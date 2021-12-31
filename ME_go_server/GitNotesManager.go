package main

import (
	"fmt"
	"github.com/go-git/go-git/v5"
	"os"
	"path/filepath"
	"regexp"
	"sort"
	"sync"
)

var gitpath string
var mdReg *regexp.Regexp
var totalStruct *TotalStruct
var RootPath = "./repo"
var GitNoteManager = _GitNoteManager{}

type _GitNoteManager struct{}

// var curMap[string]int
// var oldMap[string]int
type FileNode struct {
	Name  string `json:"name"`
	Index int    `json:"index"`
	// Path string `json:"path"`
	// FileNodes []*FileNode `json:"children"`
}
type FolderNode struct {
	Name string `json:"name"`
	// Path        string        `json:"path"`
	FileNodes   []*FileNode   `json:"file_nodes"`
	FolderNodes []*FolderNode `json:"folder_nodes"`
}

// func (*FolderNode) removeNotExist(){

// }
type TotalStruct struct {
	TotalCnt int `json:totalCnt`
	// recordedPath2IndexMap map[string]int
	Root FolderNode `json:nodeTree`
}

// func FileNodesContain(filename string) {

// }
func NewTotalStruct() *TotalStruct {
	return &TotalStruct{
		TotalCnt: 2,

		// recordedPath2IndexMap: make(map[string]int),
		Root: FolderNode{"root", []*FileNode{}, []*FolderNode{}},
	}
}

var Path2IndexMap sync.Map

func GetRootTree() *FolderNode {
	return &totalStruct.Root
}

//设置仓库路径
func (g *_GitNoteManager) setRepoPath(path string) {
	// Clone the given repository to the given directory
	// Info("git clone https://github.com/go-git/go-git")
	gitpath = path
	// CheckIfError(err)
	g.firstInit()
}
func (g *_GitNoteManager) firstInit() {
	if totalStruct == nil {
		save := FileReader.readSave()
		if save != nil {
			totalStruct = save
		} else {
			totalStruct = NewTotalStruct()
		}
	}
	g.loadMapFromTree(&totalStruct.Root, "./repo")
}
func (g *_GitNoteManager) loadMapFromTree(root *FolderNode, path string) {

	for _, value := range root.FolderNodes {
		g.loadMapFromTree(value, filepath.Join(path, value.Name))
	}
	for _, value := range root.FileNodes {
		// loadMapFromTree(value)
		fp := filepath.Join(path, value.Name)
		Path2IndexMap.Store(fp, value.Index)
		// fmt.Println("one node is loaded", fp, value.Index)
	}
}
func (g *_GitNoteManager) UpdataRepo() {
	g.pullRepo()
	g.genRepoTree()
}

func (g *_GitNoteManager) pullRepo() {
	// We instantiate a new repository targeting the given path (the .git folder)
	r, err := git.PlainOpen("./repo")
	CheckIfError(err)

	// Get the working directory for the repository
	w, err := r.Worktree()
	CheckIfError(err)

	// Pull the latest changes from the origin remote and merge into the current branch
	fmt.Println("git pull origin")
	err = w.Pull(&git.PullOptions{RemoteName: "origin"})
	CheckIfError(err)

	// Print the latest commit that was just pulled
	ref, err := r.Head()
	CheckIfError(err)
	commit, err := r.CommitObject(ref.Hash())
	CheckIfError(err)
	fmt.Println(commit)
}

//clone仓库
func (g *_GitNoteManager) cloneRepo() {

	_, _ = git.PlainClone("./repo", false, &git.CloneOptions{
		URL:      gitpath,
		Progress: os.Stdout,
	})
}

//以下皆跟遍历仓库相关/////////////////////////////////////////////////////////////
//生成仓库的文件结构
func (g *_GitNoteManager) genRepoTree() {
	reg, err := regexp.Compile(".md")

	if err != nil {
		fmt.Println(err)
		return
	}
	mdReg = reg
	// getPath("./repo", "")
	g.start()

}

func (g *_GitNoteManager) getNewFileCnt() int {
	totalStruct.TotalCnt++
	return totalStruct.TotalCnt - 1
}
func (g *_GitNoteManager) removeNotExistInFolderNode(existFiles []string, folderNode *FolderNode, prefixPath string) {
	// if folderNode.FolderNodes!=nil
	//fileNodes =[]*FileNode()
	//遍历文件节点

	{ //删除文件
		preMove := 0
		i := 0
		for ; i < len(folderNode.FileNodes); i++ {
			value := folderNode.FileNodes[i]
			if !g.filesContainFile(existFiles, value.Name) {
				// 删除path在map里的记录
				fmt.Println("not contain ", value.Name)
				fpath := filepath.Join(prefixPath, value.Name)
				//folderNode.FileNodes = append(folderNode.FileNodes[:index], folderNode.FileNodes[index+1:]...)
				Path2IndexMap.Delete(fpath)

				//删除
				preMove++
			} else {
				folderNode.FileNodes[i-preMove] = folderNode.FileNodes[i]
			}
		}
		folderNode.FileNodes = folderNode.FileNodes[:i-preMove]
	}
	//for index, value := range folderNode.FileNodes {
	//	// fmt.Println("index=", index, "value=", value)
	//
	//}
	{
		{ //删除文件夹
			preMove := 0
			i := 0
			for ; i < len(folderNode.FolderNodes); i++ {
				value := folderNode.FolderNodes[i]
				if !g.filesContainFile(existFiles, value.Name) {
					preMove++
				} else {
					folderNode.FolderNodes[i-preMove] = folderNode.FolderNodes[i]
				}
			}
			folderNode.FolderNodes = folderNode.FolderNodes[:i-preMove]
		}
	}
	//遍历文件夹节点
	//for index, value := range folderNode.FolderNodes {
	//	// fmt.Println("index=", index, "value=", value)
	//	if !g.filesContainFile(existFiles, value.Name) {
	//		folderNode.FolderNodes = append(folderNode.FolderNodes[:index], folderNode.FolderNodes[index+1:]...)
	//	}
	//}
}
func (g *_GitNoteManager) filesContainFile(files []string, file string) bool {
	for _, value := range files {
		// fmt.Println("index=", index, "value=", value)
		if value == file {
			return true
		}
	}
	return false
}
func (g *_GitNoteManager) find_delete_folder_node_in_nodes(folderNodes *[]*FolderNode, name string) {
	i := 0
	pre := 0
	println("bf size", len(*folderNodes))
	for ; i < len(*folderNodes); i++ {
		println("foldername when delete in tree", (*folderNodes)[i].Name)
		if (*folderNodes)[i].Name == name {
			pre++
			println("match one when delete in tree", name)
		} else if pre > 0 {
			(*folderNodes)[i-pre] = (*folderNodes)[i] //前移
			//println("match one when delete in tree", name)
		}
	}
	//裁剪长度
	*folderNodes = (*folderNodes)[:i-pre]
	println("aft size", len(*folderNodes))
}
func (g *_GitNoteManager) lookForFolderNode(folderNodes []*FolderNode, file string) *FolderNode {
	for _, value := range folderNodes {
		if value.Name == file {
			return value
		}
	}
	return nil
}
func (g *_GitNoteManager) lookForFileNode(fileNodes []*FileNode, file string) *FileNode {
	for _, value := range fileNodes {
		if value.Name == file {
			return value
		}
	}
	return nil
}
func (g *_GitNoteManager) start() {

	fileInfo, _ := os.Lstat(RootPath)

	g.walk(RootPath, fileInfo, &totalStruct.Root)

	FileReader.saveTotalStruct(totalStruct)
	// data, _ := json.Marshal(totalStruct.root)

	// fmt.Printf("\r\n%s\r\n", data)
	// Path2IndexMap.Range(func(key, value interface{}) bool {
	// 	keyInfo := key.(string)
	// 	valueInfo := value.(int)
	// 	fmt.Println("keyInfo", keyInfo, "valueInfo", valueInfo)
	// 	return true
	// })
	// fm
	// t.Println(Path2IndexMap)
}
func (g *_GitNoteManager) walk(path string, info os.FileInfo, node *FolderNode) bool {
	// 列出当前目录下的所有目录、文件
	files := g.listFiles(path)
	//递归移除已经不存在的节点
	g.removeNotExistInFolderNode(files, node, path)

	if len(files) == 0 {
		return false
	}
	// 遍历这些文件
	for _, filename := range files {
		// 1拼接全路径
		fpath := filepath.Join(path, filename)

		// 构造文件结构
		fio, _ := os.Lstat(fpath)

		// 如果遍历的当前文件是个目录，则进入该目录进行递归
		if fio.IsDir() {
			if filename != ".git" {
				//从已有树结构中搜索
				child := g.lookForFolderNode(node.FolderNodes, filename)
				// 将当前文件作为子节点添加到目录下
				if child == nil {
					child = &FolderNode{filename, []*FileNode{}, []*FolderNode{}}
					node.FolderNodes = append(node.FolderNodes, child)
				}
				// fmt.Println(child)

				//递归，并判断是否为空，空则删除
				hasfile := g.walk(fpath, fio, child)
				if !hasfile {
					//删除文件夹，
					os.Remove(fpath)
					println("remove", fpath)
					//log.Error("file remove err:", err)
					//删除树节点
					//println("bff size", len(folderNodes))
					g.find_delete_folder_node_in_nodes(&node.FolderNodes, filename)
					for _, data := range node.FolderNodes {
						print(data.Name)
					}
					println("")
				} else {
					//println("no remove", fpath)
				}
			}

		} else {
			if mdReg.MatchString(fio.Name()) {
				child := g.lookForFileNode(node.FileNodes, filename)
				// 将当前文件作为子节点添加到目录下
				if child == nil {
					findex := 0
					if fpath == MainPath {
						findex = 1
					} else {
						findex = g.getNewFileCnt()
					}
					child = &FileNode{filename, findex}
					node.FileNodes = append(node.FileNodes, child)
					Path2IndexMap.Store(fpath, findex)
				}
				// index := cntFileInMap(fpath)
				// // 将当前文件作为子节点添加到目录下
				// child := FileNode{filename, index}
				// node.FileNodes = append(node.FileNodes, &child)
			}
		}
	}
	files = g.listFiles(path)
	if len(files) == 0 {
		return false
	}
	return true
}
func (g *_GitNoteManager) listFiles(dirname string) []string {
	f, _ := os.Open(dirname)

	names, _ := f.Readdirnames(-1)
	f.Close()

	sort.Strings(names)

	return names
}

package main

import (
	"fmt"
	"os"
	"path/filepath"
	"regexp"
	"sort"
	"sync"

	"github.com/go-git/go-git/v5"
)

var gitpath string
var mdReg *regexp.Regexp
var totalStruct *TotalStruct
var RootPath = "./repo"

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
func setRepoPath(path string) {
	// Clone the given repository to the given directory
	// Info("git clone https://github.com/go-git/go-git")
	gitpath = path
	// CheckIfError(err)
	firstInit()
}
func firstInit() {
	if totalStruct == nil {
		save := readSave()
		if save != nil {
			totalStruct = save
		} else {
			totalStruct = NewTotalStruct()
		}
	}
	loadMapFromTree(&totalStruct.Root, "./repo")
}
func loadMapFromTree(root *FolderNode, path string) {

	for _, value := range root.FolderNodes {
		loadMapFromTree(value, filepath.Join(path, value.Name))
	}
	for _, value := range root.FileNodes {
		// loadMapFromTree(value)
		fp := filepath.Join(path, value.Name)
		Path2IndexMap.Store(fp, value.Index)
		// fmt.Println("one node is loaded", fp, value.Index)
	}
}
func UpdataRepo() {
	pullRepo()
	genRepoTree()
}

func pullRepo() {
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
func cloneRepo() {

	_, _ = git.PlainClone("./repo", false, &git.CloneOptions{
		URL:      gitpath,
		Progress: os.Stdout,
	})
}

//以下皆跟遍历仓库相关/////////////////////////////////////////////////////////////
//生成仓库的文件结构
func genRepoTree() {
	reg, err := regexp.Compile(".md")

	if err != nil {
		fmt.Println(err)
		return
	}
	mdReg = reg
	// getPath("./repo", "")
	start()

}

func getIndexInOldMap() {

}
func getNewFileCnt() int {
	totalStruct.TotalCnt++
	return totalStruct.TotalCnt - 1
}
func removeNotExistInFolderNode(existFiles []string, folderNode *FolderNode, prefixPath string) {
	// if folderNode.FolderNodes!=nil
	for index, value := range folderNode.FileNodes {
		// fmt.Println("index=", index, "value=", value)
		if !filesContainFile(existFiles, value.Name) {
			fmt.Println("not contain ", value.Name)
			fpath := filepath.Join(prefixPath, value.Name)
			folderNode.FileNodes = append(folderNode.FileNodes[:index], folderNode.FileNodes[index+1:]...)
			Path2IndexMap.Delete(fpath)
		}
	}
	for index, value := range folderNode.FolderNodes {
		// fmt.Println("index=", index, "value=", value)
		if !filesContainFile(existFiles, value.Name) {
			folderNode.FolderNodes = append(folderNode.FolderNodes[:index], folderNode.FolderNodes[index+1:]...)
		}
	}
}
func filesContainFile(files []string, file string) bool {
	for _, value := range files {
		// fmt.Println("index=", index, "value=", value)
		if value == file {
			return true
		}
	}
	return false
}
func lookForFolderNode(folderNodes []*FolderNode, file string) *FolderNode {
	for _, value := range folderNodes {
		if value.Name == file {
			return value
		}
	}
	return nil
}
func lookForFileNode(fileNodes []*FileNode, file string) *FileNode {
	for _, value := range fileNodes {
		if value.Name == file {
			return value
		}
	}
	return nil
}
func start() {

	fileInfo, _ := os.Lstat(RootPath)

	walk(RootPath, fileInfo, &totalStruct.Root)

	saveTotalStruct(totalStruct)
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
func walk(path string, info os.FileInfo, node *FolderNode) {
	// 列出当前目录下的所有目录、文件
	files := listFiles(path)
	// fmt.Println("walking:", path)
	removeNotExistInFolderNode(files, node, path)
	// filecnt := 0
	// 遍历这些文件
	// for _, filename := range files {
	// 	// 拼接全路径
	// 	fpath := filepath.Join(path, filename)

	// 	// 构造文件结构
	// 	fio, _ := os.Lstat(fpath)

	// 	// 如果遍历的当前文件是个目录，则进入该目录进行递归
	// 	if !fio.IsDir() {
	// 		if mdReg.MatchString(fio.Name()) {
	// 			// 将当前文件作为子节点添加到目录下
	// 			child := FileNode{filename}
	// 			node.FileNodes = append(node.FileNodes, &child)
	// 			filecnt++
	// 		}
	// 	}
	// }
	// if filecnt==0 {
	// 	return
	// }
	// 遍历这些文件
	for _, filename := range files {
		// 拼接全路径
		fpath := filepath.Join(path, filename)

		// 构造文件结构
		fio, _ := os.Lstat(fpath)

		// 如果遍历的当前文件是个目录，则进入该目录进行递归
		if fio.IsDir() {
			if filename != ".git" {
				// fmt.Println(node)
				child := lookForFolderNode(node.FolderNodes, filename)
				// 将当前文件作为子节点添加到目录下
				if child == nil {
					child = &FolderNode{filename, []*FileNode{}, []*FolderNode{}}
					node.FolderNodes = append(node.FolderNodes, child)
				}
				// fmt.Println(child)
				walk(fpath, fio, child)
			}

		} else {
			if mdReg.MatchString(fio.Name()) {
				child := lookForFileNode(node.FileNodes, filename)
				// 将当前文件作为子节点添加到目录下
				if child == nil {
					findex := 0
					if fpath == MainPath {
						findex = 1
					} else {
						findex = getNewFileCnt()
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
	return
}
func listFiles(dirname string) []string {
	f, _ := os.Open(dirname)

	names, _ := f.Readdirnames(-1)
	f.Close()

	sort.Strings(names)

	return names
}

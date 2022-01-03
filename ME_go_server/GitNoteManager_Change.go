package main

import (
	"github.com/go-git/go-git/v5/plumbing"
	"github.com/go-git/go-git/v5/plumbing/object"
	"github.com/go-git/go-git/v5/utils/merkletrie"
	_ "hash"
)

type ChangeDate struct {
	Date        string        `json:"date"`
	ChangeNodes []*ChangeNode `json:"change_nodes"`
}

func (cd *ChangeDate) addChangeNode(change_node *ChangeNode) {
	cd.ChangeNodes = append(cd.ChangeNodes,
		change_node)
}
func ChangeDate_new(date string) *ChangeDate {
	return &ChangeDate{
		Date:        date,
		ChangeNodes: []*ChangeNode{},
	}
}

type ChangeNode struct {
	CommitHash    string          `json:"commit_hash"`
	SingleChanges []*SingleChange `json:"single_changes"`
}

func ChangeNode_new(hash string) *ChangeNode {
	return &ChangeNode{CommitHash: hash}
}
func (cn *ChangeNode) add(single_change *SingleChange) *ChangeNode {
	cn.SingleChanges = append(cn.SingleChanges, single_change)
	return cn
}

type SingleChange struct {
	FilePath   string `json:"file_path"`
	ChangeType string `json:"change_type"` //"mod or add"
}

func SingleChange_new(file_path string, change_type string) *SingleChange {
	return &SingleChange{
		FilePath:   file_path,
		ChangeType: change_type,
	}
}

type _SingleChangeType struct {
	mod string
	ins string
}

var SingleChangeType = _SingleChangeType{
	mod: "mod",
	ins: "add",
}

//判断是否已经记录过这个commit变化
func changes_rec_check_exist(hash plumbing.Hash) bool {
	hash_str := hash.String()
	//只查找最近记录中的最近一天，因为如果添加过了，那必然在最新的一天里，没添加过，必然不在最新一天里
	arrlen := len(totalStruct.ChangeLogs)
	if arrlen > 0 {
		arr2len := len(totalStruct.ChangeLogs[arrlen-1].ChangeNodes)
		return totalStruct.ChangeLogs[arrlen-1].ChangeNodes[arr2len-1].CommitHash == hash_str
	} else {
		//一天也没有
		return false
	}
}
func (g *_GitNoteManager) delete_all_nodes_of_filepath(fp string) {
	println("delete all", fp)
	for _, v := range totalStruct.ChangeLogs {
		for _, v1 := range v.ChangeNodes {
			dcnt := 0
			for i, v2 := range v1.SingleChanges {
				if v2.FilePath == fp {
					dcnt++
				} else if dcnt > 0 {
					v1.SingleChanges[i-dcnt] = v1.SingleChanges[i] //前移
				}
			}
			v1.SingleChanges = v1.SingleChanges[:len(v1.SingleChanges)-dcnt]
		}
	}
}
func (g *_GitNoteManager) try_record_change(changes *object.Changes, when string, hash plumbing.Hash) {
	if !changes_rec_check_exist(hash) {
		println("new change is recorded")
		if len(totalStruct.ChangeLogs) == 0 ||
			last(totalStruct.ChangeLogs).Date != when {
			new_date := ChangeDate_new(when)
			totalStruct.ChangeLogs = append(totalStruct.ChangeLogs, new_date)
		}
		target_date_log := last(totalStruct.ChangeLogs)
		//生成新变化节点
		new_change_node := ChangeNode_new(hash.String())
		//插入当天数组
		target_date_log.addChangeNode(new_change_node)

		for _, v := range *changes {
			println(v.String())
			a, err := v.Action()
			CheckIfError(err)
			if a == merkletrie.Modify {
				new_change_node.add(SingleChange_new(
					v.From.Name,
					SingleChangeType.mod,
				))
				if v.From.Name != v.To.Name {
					g.delete_all_nodes_of_filepath(v.To.Name)//这个git库的api太蠢了 都是反的
				}
				//append(totalStruct.ChangeLogs, )
			} else if a == merkletrie.Delete {//这个git库的api太蠢了 都是反的
				new_change_node.add(SingleChange_new(
					v.From.Name,
					SingleChangeType.ins,
				))
			} else {
				println("some delete")
				g.delete_all_nodes_of_filepath(v.To.Name)
			}
		}
		if(len(new_change_node.SingleChanges)==0){
			target_date_log.ChangeNodes=
				target_date_log.ChangeNodes[:len(target_date_log.ChangeNodes)-1]
		}
	} else {
		println("no new change need to be recorded")
	}
}

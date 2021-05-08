package main

import (
	"fmt"

	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

type sQLStruct struct {
}
type DataBaseConfig struct {
	User     string `json:"user"`
	Name     string `json:"name"`
	Password string `json:"pw"`
	Host     string `json:"ip"`
	Port     string `json:"port"`
	Charset  string `json:"charset"`
}

var SQL sQLStruct
var db *gorm.DB
var CommentPageSize = 10

//公有函数///////////////////////////////////////////////////////////////////////////
func (sql sQLStruct) InitDB() error {
	dsn := "blog_go:Y73sT2kkMjYczxRn@tcp(127.0.0.1:3306)/blog_go?charset=utf8mb4&parseTime=True&loc=Local"
	var err error
	db, err = gorm.Open(mysql.Open(dsn), &gorm.Config{})
	if err != nil {
		panic(err)
	}
	// defer db.Close()

	// 自动迁移
	// 迁移 schema
	db.AutoMigrate(&CommentStruct{}, &FatherCommentIdStruct{})
	return nil
}

func (sql sQLStruct) AddComment(name, content, contact, email, time string, fatherID int) AddCommentReturnStruct {
	const err_findfather = 1
	// const err_findfather = 2
	const err_createComment = 2
	const err_saveFather = 3
	// const err_tooDeep
	var err = 0
	var child *CommentStruct
	// if depth>10 {
	var returnStruct AddCommentReturnStruct
	// }
	var father *CommentStruct

	if fatherID > -1 { //有爸
		// db.Find()
		result := db.First(father, fatherID)
		if result.Error != nil { //判断父组件获取成功
			err = err_findfather
			goto _AfterAnalyze
		}

		child = &CommentStruct{
			Name:      name,
			Content:   content,
			Contact:   contact,
			Email:     email,
			Time:      time,
			FatherId:  father.ID,
			HasFather: true,
		}
		result = db.Create(child) //增加评论
		if result.Error != nil {  //判断评论增加成功
			err = err_createComment
			goto _AfterAnalyze
		}
		father.Children = fmt.Sprint(father.Children, "|", child.ID)
		result = db.Save(&father)
		if result.Error != nil {
			err = err_saveFather
			goto _AfterAnalyze
		}

	} else { //没爸
		child1 := &CommentStruct{
			Name:      name,
			Content:   content,
			Contact:   contact,
			Email:     email,
			Time:      time,
			HasFather: false,
			// FatherId: -1,
		}
		result := db.Create(child1) //增加评论
		if result.Error != nil {    //判断评论增加成功
			err = err_createComment
			goto _AfterAnalyze
		}
		fcis := &FatherCommentIdStruct{
			RealID: child1.ID,
		}
		result = db.Create(fcis)
		if result.Error != nil { //判断评论增加成功
			err = err_createComment
			goto _AfterAnalyze
		}
	}

_AfterAnalyze:
	returnStruct.Successed = err == 0
	returnStruct.ErrId = err

	return returnStruct

	// rs, err := db.Exec("INSERT INTO users(name, age) VALUES (?, ?)", user.Name, user.Age)
	// if err != nil {
	// 	fmt.Println("插入数所库失败", err)
	// 	return
	// }

	// id, err := rs.LastInsertId()
}

func (sql sQLStruct) getCommentsPre(id uint) (returnData GetCommentsReturn) {
	comments := make([]CommentStruct, 0)
	result := db.Where("id > ? AND has_father=0", fmt.Sprint(id)).Limit(CommentPageSize).Find(&comments)
	if result.Error != nil {
		returnData.Errmsg = "find comments failed"
		returnData.Haserr = true
	} else if result.RowsAffected == 0 {
		// returnData.errmsg = "no more comments"
		returnData.Haserr = false
		returnData.Data = comments
	} else {
		returnData.Haserr = false
		returnData.Data = comments
	}
	return
}
func (sql sQLStruct) getCommentsPageNext(id uint) (returnData GetCommentsReturn) {
	comments := make([]CommentStruct, 0)
	result := db.Where("id < ? AND has_father = 0", fmt.Sprint(id)).Order("id desc").Limit(CommentPageSize).Find(&comments)
	if result.Error != nil {
		returnData.Errmsg = "find comments failed"
		returnData.Haserr = true
	} else if result.RowsAffected == 0 {
		// returnData.errmsg = "no more comments"
		returnData.Data = comments
		returnData.Haserr = false
	} else {
		returnData.Haserr = false
		returnData.Data = comments
	}
	return
}
func (sql sQLStruct) getCommentsPageLatest() (returnData GetCommentsReturn) {
	comments := make([]CommentStruct, 0)
	result := db.Where("has_father = 0").Order("id desc").Limit(CommentPageSize).Find(&comments)
	if result.Error != nil {
		returnData.Errmsg = "find comments failed"
		returnData.Haserr = true
	} else if result.RowsAffected == 0 {
		// returnData.errmsg = "no more comments"
		returnData.Data = comments
		returnData.Haserr = false
	} else {
		returnData.Haserr = false
		returnData.Data = comments
	}
	return
}

package main

import (
	"fmt"
	"strconv"
	"time"

	"github.com/gin-gonic/gin"
)

type CommentStruct struct {
	ID      uint
	Name    string
	Content string
	Contact string
	Email   string
	// to        int
	Children  string
	ChildCnt  int
	Depth     int //深度 0 是最外层，当深度大于10的时候就不能嵌套了
	Time      string
	FatherId  uint
	HasFather bool
}

type AddCommentReturnStruct struct {
	ErrId     int
	Successed bool
}

func (api) CreateAddCommentAPI(g *gin.Engine) {

	g.POST("/addComment", func(c *gin.Context) {
		var returnStruct AddCommentReturnStruct
		id, err := strconv.Atoi(c.PostForm("fatherID"))
		if err != nil {
			returnStruct.ErrId = 100
			returnStruct.Successed = false

		} else {
			t1 := time.Now().Year()   //年
			t2 := time.Now().Month()  //月
			t3 := time.Now().Day()    //日
			t4 := time.Now().Hour()   //小时
			t5 := time.Now().Minute() //分钟

			currentTimeData := fmt.Sprintf("%d-%d-%d %d:%d\n", t1, t2, t3, t4, t5) //获取当前时间，返回当前时间Time
			returnStruct = SQL.AddComment(
				c.PostForm("name"),
				c.PostForm("content"),
				c.PostForm("contact"),
				c.PostForm("email"),
				(currentTimeData),
				id,
			)
		}
		fmt.Println("errId ", returnStruct.ErrId, " succed ", returnStruct.Successed)
		c.JSON(200, returnStruct)
	})
}

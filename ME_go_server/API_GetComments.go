package main

import (
	"strconv"

	"github.com/gin-gonic/gin"
)

type FatherCommentIdStruct struct { //用于创造最上层父评论的键值对
	ID     uint
	RealID uint
}

type GetCommentsReturn struct {
	Errmsg string
	Haserr bool
	Data   []CommentStruct
}

func (api) CreateGetCommentsNextPageAPI(g *gin.Engine) {
	g.GET("/getNextCommentsNextPage", func(c *gin.Context) {
		id := c.Query("id") //评论id
		idint, err := strconv.Atoi(id)
		if err != nil {
			return
		}
		returnData := SQL.getCommentsPageNext(uint(idint))
		c.JSON(200, returnData)
	})
}
func (api) CreateGetCommentsPreAPI(g *gin.Engine) {
	g.GET("/getNextCommentsPre", func(c *gin.Context) {
		id := c.Query("id") //评论id
		idint, err := strconv.Atoi(id)
		if err != nil {
			return
		}
		returnData := SQL.getCommentsPre(uint(idint))
		c.JSON(200, returnData)
	})
}

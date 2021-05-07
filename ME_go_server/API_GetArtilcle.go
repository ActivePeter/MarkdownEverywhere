package main

import (
	"fmt"
	"io/ioutil"
	"path/filepath"
	"strconv"

	"github.com/gin-gonic/gin"
)

// .GET("/ping", func(c *gin.Context) {
// 	c.JSON(200, gin.H{
// 		"message": "pong",
// 	})
// })
func (api) CreateGetListApi(g *gin.Engine) {
	g.GET("/list", func(c *gin.Context) {
		// id := c.Query("id")

		c.JSON(200, gin.H{
			"tree": GetRootTree(),
			// "article": ReadArticle(id),
		})

	})
}
func (api) CreateGetArticleApi(g *gin.Engine) {
	g.GET("/article", func(c *gin.Context) {
		id := c.Query("id")
		if id == "" {
			c.JSON(300, gin.H{
				// "tree":    GetRootTree(),
				// "article": ReadArticle(id),
			})
		} else {
			art, tit := ReadArticle(id)
			c.JSON(200, gin.H{
				"title":   tit,
				"article": art,
			})
		}
	})
}
func ReadArticle(id string) (string, string) {
	id_int, err := strconv.Atoi(id)
	if err != nil {
		return "请求有误", ""
	} else {
		path := ""
		Path2IndexMap.Range(func(k, v interface{}) bool {
			if v.(int) == id_int {
				path = k.(string)
				return false
			}
			return true
		})
		if path == "" {
			return "请求的文章不存在或已删除", ""
		} else {
			byte, err := ioutil.ReadFile(path)
			if err != nil {
				fmt.Println(path, err)
				return "服务端读取文章失败", ""
			} else {
				return string(byte), filepath.Base(path)
			}
		}
	}
}

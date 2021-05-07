package main

import (
	"time"

	"github.com/gin-gonic/gin"
)

var lastCallTime int64 = 0

func CreateUpdateHookApi(g *gin.Engine) {

	g.POST("/hook", func(c *gin.Context) {
		t6 := time.Now().Unix()
		if t6-lastCallTime > 3 || lastCallTime == 0 {

			UpdataRepo()
			c.JSON(200, gin.H{})
			lastCallTime = t6
		} else {
			c.JSON(301, gin.H{
				"cur":   t6,
				"last":  lastCallTime,
				"delta": t6 - lastCallTime,
			})
		}
	})
}

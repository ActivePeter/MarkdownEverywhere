package main

import (
	"fmt"
	"os"

	"github.com/gin-gonic/gin"
	"github.com/sirupsen/logrus"
)

var log = logrus.New()

func main() {
	//1.初始化log
	log.Out = os.Stdout
	file, err := os.OpenFile("logrus.log", os.O_CREATE|os.O_WRONLY|os.O_APPEND, 0666)
	if err == nil {
		log.Out = file
		log.Info("------------------------start-------------------------------")
	} else {
		log.Info("Failed to log to file, using default stderr")
	}

	//2.read config
	conf := FileReader.readConf()
	if conf == nil {
		fmt.Println("conf read failed")
		log.Warn("conf read failed")
		return
	}

	//3.加载git
	GitNoteManager.setRepoPath((*conf)["git"].(string))
	GitNoteManager.cloneRepo()
	GitNoteManager.pullRepo()
	GitNoteManager.genRepoTree()

	r := gin.Default()
	r.GET("/ping", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "pong",
		})
	})
	SQL.InitDB()
	API.CreateGetArticleApi(r)
	API.CreateGetListApi(r)
	API.CreateUpdateHookApi(r)
	API.CreateAddCommentAPI(r)
	API.CreateGetCommentsNextPageAPI(r)
	API.CreateGetCommentsPreAPI(r)
	API.CreateGetCommentsPageLatestAPI(r)

	r.Run((*conf)["ip"].(string)) // listen and serve on 0.0.0.0:8080 (for windows "localhost:8080")
}

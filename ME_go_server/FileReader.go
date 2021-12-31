package main

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"path/filepath"
)

var savepath = ""
var MainPath = ""

var FileReader = _FileReader{}

type _FileReader struct{}

//读取配置文件
func (f *_FileReader) readConf() *map[string]interface{} {
	// curpath, _ := os.Getwd()
	// fmt.Println("path：", curpath)
	data, err := ioutil.ReadFile("./config.json")
	if err != nil {
		fmt.Println("config read fail")
		log.Warn("config read fail")
		return nil
	}
	map2 := make(map[string]interface{})
	//读取的数据为json格式，需要进行解码
	err = json.Unmarshal(data, &map2)
	if err != nil {
		fmt.Println("save json unmarshal fail")
		log.Warn("save json unmarshal fail")
		return nil
	}
	fmt.Println(map2)
	savepath = map2["savePath"].(string)
	MainPath = map2["default"].(string)
	MainPath = filepath.Join(RootPath, MainPath)

	sqlConf.pw = map2["sql_pw"].(string)
	sqlConf.name = map2["sql_name"].(string)
	sqlConf.ip_port = map2["sql_ip_port"].(string)

	return &map2
	// err = json.Unmarshal(str, &map2)
}
func (f *_FileReader) readSave() *TotalStruct {
	// curpath, _ := os.Getwd()
	// fmt.Println("path：", curpath)
	data, err := ioutil.ReadFile(savepath + "/save.json")
	if err != nil {
		fmt.Println("save read fail")
		return nil
	}
	map2 := &TotalStruct{}
	//读取的数据为json格式，需要进行解码
	err = json.Unmarshal(data, &map2)
	if err != nil {
		fmt.Println("save json unmarshal fail")
		return nil
	}
	// fmt.Println(map2)
	return map2
}
func (f *_FileReader) saveTotalStruct(totalStruct *TotalStruct) {
	data, _ := json.Marshal(totalStruct)
	// fmt.Printf("saving:\r\n%s\r\n", data)
	err := ioutil.WriteFile(savepath+"/save.json", data, 0666)
	if err != nil {
		fmt.Println("err:", err)
	}
}

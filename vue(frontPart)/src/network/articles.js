import {request,request_auth_required} from "@/network/request";
import context from "../main"
//接口层,初步处理服务器返回信息。将状态规范化后交给界面层。
export function getTags() {
  return request({
    url:'/api/tags/'
  })
}

export function getCategories() {
  return request({
    url:'/api/categorys/'
  })
}

export function GetCataServlet() {
  return request({
    method:'post',
    url:'/GetCataServlet',
  }).then(res => {
    // console.log("GetCataServlet");
    // console.log(res);
    switch(res.status){
      case 200:
        return res;
        break;
    }
    // return (result)
  })
}

export function GetArticleServlet(id,code) {
  return request({
    method:'post',
    url:'/GetArticleServlet?id='+id+"&code="+code
  }).then(res => {
    // console.log("GetArticleServlet");
    // console.log(res);
    switch(res.status){
      case 200:
        return res;
        break;
    }
    // return (result)
  })
}
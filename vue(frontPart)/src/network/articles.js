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

export function CreateArticle(title,body,tag) {
  var data={}
  data.title=title
  data.body=body
  data.tag=tag
  return request_auth_required({
    method:'post',
    url:'/api/articles/',
    data
  }).then(res => {
    // console.log("CreateArticle");
    // console.log(res);
    var result={};
    switch(res.status){
      case 201:
        result.status=201;
        result.id=res.data.id;
        break;
    }
    return (result)
  })
}

export function UpdateArticle(title,body,tag,id) {
  var data={}
  data.title=title
  data.body=body
  data.tag=tag

  return request_auth_required({
    method:'put',
    url:'/api/articles/'+id+"/",
    data
  }).then(res => {
    // console.log("UpdateArticle");
    // console.log(res);
    var result={};
    switch(res.status){
      case 200:
        result.status=200;
        //result.id=res.data.id;
        break;
      default:
        result.status=400;
        break;
    }
    return (result)
  })
}

export function ReadArticle(id) {
  
  return request({
    method:'get',
    url:'/api/articles/'+id,
  }).then(res => {
    // console.log("res");
    // console.log(res);
    var result={};
    switch(res.status){
      case 200:
        result.status=200;
        result.data=res.data;
        break;
    }
    return (result);
  })
}
export function ReadEditArticle(id) {
  return ReadArticle(id).then((res)=>{
    var result={};
    switch(res.status){
      case 200:
        // console.log("author_id:"+res.data.author_info.id);
        // console.log("user_id:"+context.$store.state.userinfo);
        if(res.data.author_info.id==context.$store.state.userinfo.id){
          result.status=200;
          result.data=res.data;
        }else{
          result.status=300;
        }
        break;
    }
    return (result);
  })
}
export function ReadComments(id,page) {
  
  return request({
    method:'get',
    url:'/api/articles/'+id+"/comments/"+"?page="+page,
   
  }).then(res => {
    console.log("ReadComments");
    console.log(res);
    var result={};
    switch(res.status){
      case 200:
        result.status=200;
        result.data=res.data;
        break;
    }
    return (result);
  })
}
export function PushComment(text,articleId,reply1,reply2) {
  var data={}
  data.text=text
  data.article=articleId
  if(reply1){
    data.reply1=reply1
  }
  if(reply2){
    data.reply2=reply2
  }
  // console.log("PushCommentData")
  // console.log(data)

  return request_auth_required({
    method:'post',
    url:'/api/comments/',
    data
  }).then(res => {
    // console.log("res");
    // console.log(res);
    var result={};
    switch(res.status){
      case 201:
        result.status=201;
        result.data=res.data;
        break;
    }
    return (result);
  })
}
export function DeletComment(id) {
  return request_auth_required({
    method:'delete',
    url:'/api/comments/'+id+"/",
  }).then(res => {
    console.log("DeletComment");
    console.log(res);
    var result={};
    switch(res.status){
      case 204:
        result.status=204;
        result.data=res.data;
        break;
      case 404:
        result.status=404;
        result.data=res.data;
        break;
    }
    return (result);
  })
}
export function GetArticlesByTags(tags,page) {
  var suffix=''
  if(page!=1){
    suffix='?page='+page
  }
  var data ={}
  data.tags=tags
  console.log('obj:', data)
  return request({
    method:'post',
    url:'/api/tags/articles/'+suffix,
    data
  }).then(res => {
    console.log("getArticlesByTags");
    console.log(res);
    var result={};
    switch(res.status){
      case 200:
        result.status=200;
        result.data=res.data;
        break;
      case 404:
        result.status=404;
        result.data=res.data;
        break;
    }
    return (result);
  })
}
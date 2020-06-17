<template>
  <div class="CommentBar">
    
        <div style="padding:0px;" class="head">
          <div style="padding:10px; border-right: 1px solid #EBEEF5;" class="head">
            <img  class="headpic" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png">
          </div>
          <div style="width:100%;">
            <div style="width:100%; display: flex;padding: 10px; justify-content:space-between;" class="">
              <div>
                <el-tag size="mini" type="info" class="hoverShadow" style="margin-right:10px;"> {{username}}</el-tag>
                <el-tag v-if="replyTo" size="mini" type="info" class="hoverShadow" style="margin-right:10px;"> 回复 {{username==replyTo.commenter_info.username?"自己":replyTo.commenter_info.username}}</el-tag>
                <el-tag size="mini" type="info" v-if="!editable"  class="hoverShadow">{{rawCreatedTime|makeTime}}</el-tag>
              </div>

              <div>
              <el-tag v-if="commentId&&editable" size="mini" :type='uploading?"info":""' class="hoverShadow" @click="cancelSelectReply()" style="margin-right:10px;">{{"取消评论"}}</el-tag>
              <el-tag v-if="editable" size="mini" :type='uploading?"info":""' class="hoverShadow" @click="pushComment()">{{uploading?"发布中":"发布评论"}}</el-tag>

              <el-tag v-if="!editable&&!replyable" size="mini" :type='uploading?"info":""' class="hoverShadow" @click="selectReply()" >回复</el-tag>
              
              <el-tag v-if="!editable&&!replyable&&this.$store.state.userinfo.name==username" size="mini" @click="deletComment()" class="hoverShadow" style="margin-left:10px;">
                <i class="el-icon-delete"></i>
              </el-tag>
              
              
              </div>
            </div>
            <hr color=#EBEEF5 SIZE=1>
            <div v-if="!editable" style="padding: 0px;" class="bottomShadowBox" >
              <textarea  ref="test" placeholder="输入评论" v-model="commentData" readonly="readonly">
                
              </textarea>

            </div>
            <div v-if="editable" style="padding: 0px;" class="bottomShadowBox" >
              <textarea class="editableTextarea" ref="test" placeholder="输入评论" v-model="editableCommentData" >
                
              </textarea>

            </div>
            
            <el-collapse-transition>
              <div v-if="replyable&&!father" style="padding: 0px;" >
                <hr color=#EBEEF5 SIZE=1>
                
                <CommentBar 
                :editable="true"
                :articleId="articleId"
                :username="this.$store.state.userinfo.name"
                @onSelectReply="onSelectReply"
                @onUpdateComments="onUpdateComments"
                :commentId="commentId"
                ></CommentBar>

              </div>
            </el-collapse-transition>
            <div 
              :key="index"
              v-for="item,index in childComments">
              <hr color=#EBEEF5 SIZE=1>
              <el-collapse-transition>
                <CommentBar 
                :replyable="selectedReply2==item.id"
                :username="item.commenter_info.username"
                :rawCreatedTime="item.created_time"
                :commentData="item.text"
                :articleId="articleId"
                :commentId="item.id"
                :editable="false"
                :father="commentId"
                :replyTo="item.replyTo"
                @onSelectReply="onSelectReply"
                @onUpdateComments="onUpdateComments"></CommentBar>
              </el-collapse-transition>
            </div>
          </div>
        </div>
        <el-collapse-transition>
              <div v-if="replyable&&father" style="padding: 0px;" >
                <hr color=#EBEEF5 SIZE=1>
                
                <CommentBar 
                :editable="true"
                :articleId="articleId"
                :username="this.$store.state.userinfo.name"
                @onSelectReply="onSelectReply"
                @onUpdateComments="onUpdateComments"
                :father="father"
                :commentId="commentId"
                ></CommentBar>

              </div>
            </el-collapse-transition>
  </div>
</template>

<script>
import {PushComment,DeletComment} from "@/network/articles";
  export default {
    name: "CommentBar",
    filters: {
      makeTime: function (value) {
        var arr=value.split(":")
        if(arr.length>1){
          return arr[0].replace("T"," ")+":"+arr[1]
        }
        return "";
      }
    },
    mounted(){
      //console.log('this.childComments');
      //console.log(this.childComments);
      this.changeHeight()
    },
    watch: {
      editableCommentData (nv, ov) {
        if (nv === ov) {
          return
        }
        this.editableCommentData = nv
        //console.log('value changed')
        this.changeHeight()
      },
      commentData (nv, ov) {
        if (nv === ov) {
          return
        }
        this.commentData = nv
        //console.log('value changed')
        this.changeHeight()
      }
    },
    data(){
      return{
        img:null,
        uploading:false,//正在发表评论，等待服务器数据
        createdTime:"",
        editableCommentData:"",
        deleteVisible:false
      }
    },
    props:{
      selectedReply2:{//作用是请求的时候提供数据，还有整明为子集评论框
        type:Number,
        default() {
          return null;
        }
      },
      childComments:{
        type:Object,
        default() {
          return null;
        }
      },
      father:{//作用是请求的时候提供数据，还有整明为子集评论框
        type:Number,
        default() {
          return null;
        }
      },
      replyTo:{//作用是请求的时候提供数据，还有整明为子集评论框
        type:Object,
        default() {
          return null;
        }
      },
      editable:{
        type:Boolean,
        default() {
          return true;
        }
      },
      replyable:{
        type:Boolean,
        default() {
          return false;
        }
      },
      commentData:{
        type:String,
        default() {
          return "";
        }
      },
      username:{
        type:String,
        default() {
          return "unknown";
        }
      },
      articleId:{
        type:String,
        default() {
          return "";
        }
      },
      commentId:{
        type:Number,
        default() {
          return null;
        }
      },
      rawCreatedTime:{
        type:String,
        default() {
          return "unknown";
        }
      }
    },
    methods:{
      cancelSelectReply(){
        this.$emit("onSelectReply", null,null);
      },
      onSelectReply(id1,id2){
        //this.commentCurrentreply2=id2;
        this.$emit("onSelectReply", id1,id2);
      },
      selectReply(){//点击回复后选择reply，
        if(this.father!=null){
          console.log('当前为二级评论')
          this.$emit("onSelectReply", this.father,this.commentId);//如果为一级
        }else{
          console.log('当前为一级评论')
          
          this.$emit("onSelectReply", this.commentId,null);//如果为一级
        }

      },
      changeHeight () {
        let _this = this
        this.$nextTick(() => {
          var textArea = _this.$refs.test
          var scrollHeight = textArea.scrollHeight // 控件所有的高度，包含滚动的那部分(不可见也会有高度)
          var height = textArea.offsetHeight // 屏幕上显示的高度
          if (height <= scrollHeight) {
            textArea.style.height = 'auto' // 恢复默认值，这个作用就是根据内容自适应textarea高度
            textArea.style.height = textArea.scrollHeight>200?'200px':(textArea.scrollHeight + 'px') // 拿到最新的高度改变textarea的高度
          }
        })
      },
      deletComment(){
        this.$confirm('确认要删除该评论吗？')
          .then(_ => {
            if(!this.uploading){
              this.uploading=true;
              DeletComment(this.commentId).then(res=>{
                switch(res.status){
                  case 204:
                    this.$message({
                      showClose: true,
                      message: '评论删除成功',
                      type: 'success'
                    });
                    this.$emit("onUpdateComments", 1);
                    break;
                  case 404:
                    this.$message({
                      showClose: true,
                      message: '评论不存在，或已被删除',
                      type: 'warning'
                    });
                    this.$emit("onUpdateComments", 1);
                    break;
                }
                this.uploading=false;
              })
            }
          })
          .catch(_ => {});
        
      },
      pushComment(){
        if(this.editableCommentData.replace(/\s*/g,"").length<3){
          this.$message({
            showClose: true,
            message: '评论字数至少为3',
            type: 'warning'
          });
          return;
        }
        if(!this.uploading){
          this.uploading=true;
          var reply1=null;
          var reply2=null;
          if(this.father){
            reply1=this.father
            reply2=this.commentId
          }else if(this.commentId){
            reply1=this.commentId
          }
          PushComment(this.editableCommentData,this.articleId,reply1,reply2).then((res)=>{
            this.editableCommentData=""
            this.uploading=false;
            switch(res.status){
              case 201:
                if(this.commentId){
                  this.$emit("onUpdateComments", 1);
                }else{
                  this.$emit("onUpdateComments", 0);
                }
                this.cancelSelectReply()
                console.log('emit("updateComments", 1);')
                this.$message({
                  showClose: true,
                  message: '发布成功！',
                  type: 'success'
                });
                break;
            }
          });
          
        }
      },
      onUpdateComments(level){
        this.$emit("onUpdateComments", level);
        console.log('this.$emit("onUpdateComments", level);')
      },
    }
  }
</script>

<style scoped>
  .headpic{
    border-radius: 100px;
    border-style: solid;
    border-width: 3px;
    border-color: white;
    width:  40px;
    height: 40px;
    box-shadow: 0px 0px 5px rgba(0,0,0,0.3)
  }
  .head{
    display: flex;
    
  }
  textarea{
    margin-bottom:-3px;
    border:0px;
    border-radius: 0px;
    background-color: white;
    width: 100%;
    resize: vertical;
    padding: 10px;
    outline: none;
  }
  .editableTextarea{
    background-color: rgb(251, 251, 251)!important; 
  }
  .bottomShadowBox{
    box-shadow: 0px 0px 0px 0px rgba(0,0,0,0.2); 
      transition: box-shadow 0.3s ease 0s;
    -o-transition: box-shadow 0.35s ease 0s;
    -moz-transition: box-shadow 0.35s ease 0s;
    -webkit-transition: box-shadow 0.3s ease 0s;
  }
  .bottomShadowBox:hover{
      box-shadow: 0px 1px 8px 0px rgba(0,0,0,0.2); 
  }
</style>

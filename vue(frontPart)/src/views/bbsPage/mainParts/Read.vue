<template>
  <div class="readPage">
    <el-card :body-style="{ padding: '0px' }"  shadow="hover">
      
      <div style="padding: 14px;" class=" bottomShadowBox">
        
        <div  class="ArticleTitle" >
          <el-button v-if="showBack" 
          @click="back()"
          style="position:relative;top: -6px;" circle size="mini" icon="el-icon-arrow-left"></el-button>
        {{title}}</div>
        <div style="margin-top: 20px;" class="">
          <el-tag type="info" size="mini" class="info_tags hoverShadow">作者 {{author.name}}</el-tag> 
          <el-tag type="info" size="mini" class="info_tags hoverShadow">编辑时间 {{edit_time}}</el-tag> 
          <el-tag  size="mini" class="info_tags hoverShadow"
          v-if="this.$store.state.userinfo&&this.$store.state.userinfo.id==author.id" @click="toEdit()">
            修改文章
          </el-tag>
        </div>
        
        <div style="margin-top: 10px;" class="selectable">
          <mavon-editor
            class="md selectable"
            :value="body"
            :subfield = "false"
            :defaultOpen = "'preview'"
            :toolbarsFlag = "false"
            :editable="false"
            :scrollStyle="false"
            :ishljs = "true"
          ></mavon-editor>
        </div>
        
      </div>
      
      <hr color=#EBEEF5 SIZE=1>
        <div style="padding: 14px;padding-bottom:4px;" class="bottomShadowBox">
          <div style="margin-top: 0px;" class=""
            :key="key.name"
            v-for="key in catToTags">
            <el-tag type="info" size="mini" class="info_tags hoverShadow">{{key.name}}</el-tag> 
             <el-tag  size="mini" class="info_tags hoverShadow"
             :key="key"
              v-for="key in key.tags">
             {{key}}</el-tag>
          </div>
        </div>
      <hr color=#EBEEF5 SIZE=1>
      <div style="padding: 14px; " class=" bottomShadowBox">
        <p>点赞-收藏-</p>
      </div>
    </el-card>
    <HorizonSpace 
    v-if="this.$store.state.userinfo"
    ></HorizonSpace>
    <el-collapse-transition>
    <el-card v-if="this.$store.state.userinfo&&commentCurrentreply1==null&&commentCurrentreply1==null" :body-style="{ padding: '0px' }"  shadow="hover">
      <CommentBar 
      :editable="true"
      :articleId="id"
      :username="this.$store.state.userinfo.name"
      @onUpdateComments="onUpdateComments"></CommentBar>
    </el-card>
    </el-collapse-transition>
    <div v-if="commentPageCount>0">
      <HorizonSpace></HorizonSpace>
        <el-pagination
          layout="prev, pager, next"
          :page-count="commentPageCount"
          :current-page="commentCurrentPage"
          @current-change="commentPageChange"
        >
        </el-pagination>
    </div>
    
    <div 
      :key="index"
      v-for="item,index in commentCurrentPageData">
      <HorizonSpace></HorizonSpace>
      <el-collapse-transition>
      <el-card :body-style="{ padding: '0px' }"  shadow="hover">
        <CommentBar 
        :replyable="item.id==commentCurrentreply1&&commentCurrentreply2==null"
        :username="item.commenter_info.username"
        :rawCreatedTime="item.created_time"
        :commentData="item.text"
        :articleId="id"
        :commentId="item.id"
        :editable="false"
        :selectedReply2="commentCurrentreply2"
        :childComments="item.child_comment"
        @onSelectReply="onSelectReply"
        @onUpdateComments="onUpdateComments"></CommentBar>
      </el-card>
      </el-collapse-transition>
    </div>
    <div v-if="commentPageCount>0">
      <HorizonSpace></HorizonSpace>
        <el-pagination
          layout="prev, pager, next"
          :page-count="commentPageCount"
          :current-page="commentCurrentPage"
          @current-change="commentPageChange"
        >
        </el-pagination>
      <HorizonSpace></HorizonSpace>
    </div>
    
  </div>
</template>

<script>
  import HorizonSpace from '@/components/common/HorizonSpace'
  import {ReadArticle,getCategories,ReadComments} from "@/network/articles";
  import { mavonEditor } from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'
  import CommentBar from './readParts/CommentBar'
  import Util from "@/assets/js/util.js";
  export default {
    name: "overview",
    components: {
      HorizonSpace,
      mavonEditor,
      CommentBar,
    },
    mounted(){
      this.loadArticle();
      //console.log("this.$store.state.back")
      //console.log(this.$store.state.back)
      if(this.$store.state.back){
        this.showBack=true;
        this.$store.commit('setNeedBack', false);
      }
    },
    methods:{
      commentPageChange(page){
        this.loadComments(page)
        this.onSelectReply(null,null)
        console.log('this.commentCurrentPage:'+page)
      },
      onUpdateComments(level){
        // console.log('loadComments(level:'+level)
        switch (level) {
          case 0:
            this.loadComments(1);
            console.log('loadComments(1)')
            break;
          case 1:
            this.loadComments(this.commentCurrentPage);
            console.log('loadComments(commentCurrentPage)')
            break;
        }
      },
      onSelectReply(id1,id2){
        if(!this.$store.state.userinfo){
          this.$message({
            showClose: true,
            message: '请先登入',
            type: 'warning'
          });
          return
        }
        this.commentCurrentreply1=id1;
        this.commentCurrentreply2=id2;

        //console.log("selectReply");
        //commentCurrentPageData[id].replyable=true;
        this.$forceUpdate();
      },
      toEdit(){
        this.$router.push({ path: "/bbs/write/"+this.id ,name: 'write',params: {id: ""+this.id,back:true}});
      },
      back(){
        this.$router.back(-1)
      },
      loadComments(page){

        ReadComments(this.id,page).then(
          res=>{
            switch(res.status){
              case 200:
                this.commentPageCount=Math.ceil(res.data.count/10);
                this.commentCurrentPage=page;
                this.commentCurrentPageData=res.data.results;
                for(var key in this.commentCurrentPageData){
                  var commentsIdToComments={};
                  for(var key1 in this.commentCurrentPageData[key].child_comment){
                    var childComment=this.commentCurrentPageData[key].child_comment[key1];
                    commentsIdToComments[childComment.id]=childComment;
                  }
                  for(var key1 in this.commentCurrentPageData[key].child_comment){
                    var childComment=this.commentCurrentPageData[key].child_comment[key1];
                    if(childComment.reply2){
                      childComment.replyTo=commentsIdToComments[childComment.reply2];
                    }
                  }
                }
                // console.log("realstuff")
                // console.log(this.commentCurrentPageData)
                this.$forceUpdate();
                break;
            }
          }
        )
      },
      loadArticle(){
        //先检查是否有文章编辑权限。没有跳转到文章阅读页
        ReadArticle(this.$route.params.id).then((res)=>{
          // console.log("ReadArticle");
          // console.log(res);
          switch(res.status){
            case 200://加载文章内容
              this.id=this.$route.params.id;
              this.author.name=res.data.author_info.name;
              this.author.id=res.data.author_info.id;
              this.edit_time=res.data.modified_time.split("T")[0];
              var tag_info=res.data.tag_info;
              this.title=res.data.title;
              this.body=res.data.body;
              this.loadComments(1);
              getCategories().then((res2)=>{
                // console.log("getCategories");
                // console.log(res2);
                  switch(res2.status){
                    case 200:
                      var tagToCat={}
                      for (var cat of res2.data){
                        for(var key in cat.tag_info){
                          tagToCat[cat.tag_info[key].id]=cat.name
                        }
                      }
                      // console.log("this.tagToCat")
                      // console.log(tagToCat)
                      for(var key in tag_info){
                        if(tagToCat.hasOwnProperty(tag_info[key].id)){
                          if(!this.catToTags.hasOwnProperty(tagToCat[tag_info[key].id])){
                            this.catToTags[tagToCat[tag_info[key].id]]={tags:[],name:tagToCat[tag_info[key].id]}
                          }
                          this.catToTags[tagToCat[tag_info[key].id]].tags.push(tag_info[key].name)
                        }
                      }
                      this.$forceUpdate();
                      console.log("this.catToTags")
                      console.log(this.catToTags)

                      break;
                  }
                }
              )
              break;
            default://未处理情况
              break;
          }
        })
      },
      
    },
    data(){
      return{
        title:"vue开发记录",
        id:null,
        author:{
          name:"",
          id:-1
        },
        title:"",
        body:"",
        html:"",
        edit_time:"",
        catToTags:{},
        commentPageCount:0,
        commentCurrentPage:1,
        commentCurrentPageData:[],
        commentCurrentreply1:null,
        commentCurrentreply2:null,
        showBack:null,
      }
    }
  }
</script>

<style scoped>
  .ArticleTitle{
    font-size:32px;
    letter-spacing:3px;
    font-weight: 600;
    padding-top: 7px;
  }
  .readPage{
    letter-spacing:1px;
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
  .el-button-group{
    margin-right: 10px;
  }
  .info_tags{
    margin-right: 10px;
    margin-bottom: 10px;
  }
  
</style>

<template>
  <div id="all">
    <NavMenu
    :showSideBar="showSideBar"
    :versionCode="versionCode"
    @onSideBarStateChange="onSideBarStateChange"
    @onUpdatePageData="onUpdatePageData"
    />
    <div id="app" ref="page">
      <transition
      @before-enter="beforeEnter"
      @enter="enter"
      @leave="leave"
      >
      <div class="sidebar" id="sidebar" ref="sidebar" v-show="showSideBar"
      >
        
        <TreeMenu
        :folded='false'
        style="width: max-content;"
        :childTreeData="treeData"
        :selectedPath='selectedPath'
        ref="tmref"
        @onDataLoad="onDataLoad"
        />
      </div></transition>
      <div class="article" id="article" ref="article"> 
        <mavon-editor
          id="meditor" 
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
  </div>
  
</template>

<script>
  import NavMenu from '@/components/content/navmenu/NavMenu'
  import { mavonEditor } from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'
  import TreeMenu from '@/views/components/TreeMenu'
  import $ from 'jquery'
  import {GetCataServlet,GetArticleServlet} from "@/network/articles";
  import elementResizeDetectorMaker from 'element-resize-detector'
  import BScroll from 'better-scroll'
  export default {
    name: "App",
    components:{
      NavMenu,
      mavonEditor,
      TreeMenu
    },
    watch:{
      clientHeight(val){
        this.$refs.page.style.height = this.clientHeight + 'px';
      }
    },
    mounted(){
      var erd = elementResizeDetectorMaker();
      var _that=this
      
      if(!this.IsPC()) {
        //alert("mobile端");
        var scroll;
        this.$nextTick(() => {
          scroll=new BScroll(this.$refs.article, {
            click:true,
            tap:true,
          })
        })
        this.$nextTick(() => {
          new BScroll(this.$refs.sidebar, {
            click:true,
            tap:true,
          })
        })
        this.$refs.article.style.overflow = "hidden";
        this.$refs.sidebar.style.overflow = "hidden";
        erd.listenTo(document.getElementById("sidebar"), function(element) {
          var width = element.offsetWidth;
          _that.$refs.article.style.marginLeft=width+"px";
          document.getElementById("meditor").style.minHeight=element.offsetHeight+"px";
          scroll.refresh()
  //         $("#article").css({
  //           marginLeft:width+"px"
  //         })
        });
        erd.listenTo(document.getElementById("meditor"), function(element) {
          scroll.refresh()
  //         $("#article").css({
  //           marginLeft:width+"px"
  //         })
        });
      }else{
        this.$refs.sidebar.style.overflowY="scroll";
        erd.listenTo(document.getElementById("sidebar"), function(element) {
          var width = element.offsetWidth;
          document.getElementById("meditor").style.minHeight=element.offsetHeight+"px";
          document.getElementById("app").style.height=element.offsetHeight+60+"px";
          _that.$refs.article.style.marginLeft=width+"px";
          _that.$refs.article.style.width="calc(100% - "+width+"px)";
  //         $("#article").css({
  //           marginLeft:width+"px"
  //         })
        });
        
      }
        
      
      
      this.clientHeight = `${window.innerHeight}`;//获取浏览器可视区域高度
      this.$refs.page.style.height = this.clientHeight + 'px';
    //   console.log(this.clientHeight)
    // let that = this;
    // window.onresize = function(){
    //    this.clientHeight =  `${window.innerHeight}`;
    //    console.log("resize")
    //     if(that.$refs.page){
    //         that.$refs.page.style.height = clientHeight + 'px';
    //     }
    // }
      this.getCataServlet()
    },
    data(){
      return{
        body:"",
        showSideBar:true,
        treeData: [
        ],
        selectedPath:"",
        clientHeight:'',
        sidebarWidth:0,
        versionCode:'',
        currentNode:null
      }
    },
    methods:{
      onUpdatePageData(){
        console.log("onUpdatePageData")
        this.getCataServlet().then(_=>{
          GetArticleServlet(this.currentNode.id,this.versionCode).then(res2=>{
            
            //this.body=res2.data;
            if(res2.data=="codeNotSame"){
              this.getCataServlet()
            }else{
              this.currentNode.body=res2.data;
              this.setBody(this.currentNode.body);
            }
          })
        }
          
        )
      },
      getCataServlet(){
        return GetCataServlet().then(res=>{
        
          this.treeData=res.data[1];
          this.versionCode=res.data[0];
        })
      },
      setBody(val){
        // console.log("setbody"+val)
        if(val==""){
          this.body="# 该文件貌似是空的哦！如果不是空的请尝试刷新 "
        }else{
          this.body=val
        }
      },
      IsPC() {
        var u = navigator.userAgent;
        var canvas = document.createElement('canvas'),
        gl = canvas.getContext('experimental-webgl'),
        debugInfo = gl.getExtension('WEBGL_debug_renderer_info');
        if(gl.getParameter(debugInfo.UNMASKED_RENDERER_WEBGL).indexOf("Apple GPU")>-1){
          
          return false
        }
        
        var Agents = ["Android", "iPhone","SymbianOS", "Windows Phone","iPad", "iPod","Mobile"];
        var flag = true;
        for (var v = 0; v < Agents.length; v++) {
          if (u.indexOf(Agents[v]) > -1) {
            flag = false;
            break;
          }
        }
        return flag;
      },
      // _isMobile() {
      //     let flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)
      //   return flag;
      // },
      onDataLoad(node){
        if(node.id>0){
          this.currentNode=node;
          GetArticleServlet(node.id,this.versionCode).then(res2=>{
            
            //this.body=res2.data;
            if(res2.data=="codeNotSame"){
              this.getCataServlet()
            }else{
              node.body=res2.data;
              this.setBody(node.body);
            }
          })
        }
        // var indexSet=node.path.split(',');
        // var nodepath=this.findNodeByIndex(indexSet);
        // //console.log(indexSet)
        // if(nodepath){
          
        //   //console.log(nodepath);
        //   if(node.body||node.body==""){
            
        //   //console.log("nodebody have")
        //     this.setBody(node.body);
        //     //this.body=node.body;
        //   }else{
        //     console.log(node.body)
        //     GetArticleServlet(nodepath).then(res2=>{
        //       node.body=res2.data;
        //       this.setBody(node.body);
        //       //this.body=res2.data;
        //     })
        //   }
          
        // }else{
        //   this.$router.push({ path: ""});
        // }
        
      },
      // onSelectChange(node,state){
      //   if(state==true){
          
      // },
      onSideBarStateChange(){
        this.showSideBar=!this.showSideBar
        //console.log("onSideBarStateChange")
      },
      beforeEnter(el){
        $(el).css({
          opacity:0
        })
      },
      enter(el,done){
        $(el).animate({
          opacity:1,
          marginLeft:'0px'
        },{
          duration:300,
          complete:done
        })
        $("#article").animate({
          marginLeft:$(el).width()+"px",
          width:"100%"
        },{
          duration:300,
          complete:done
        })
        // .css({
        //   marginLeft:$(el).width()+"px"
        // })
      },
      leave(el,done){
        $(el).animate({
          opacity:0,
          marginLeft:-$(el).width()
        },{
          duration:300,
          complete:done
        }
        )
        $("#article").animate({
          marginLeft:"0px",
          width:"100%"

        },{
          duration:300,
          complete:done
        })
      },
  
    }
  }
</script>

<style scoped>
  @import "assets/css/base.css";
  #app{
    padding-top: 60px;
    width: 100%;
    display: flex;
    height: 100vh;
  }
  .sidebar{
    position:fixed;
    width:max-content;
    left:0;
    top:60px;
    bottom: 0;
    background:white;
  }
  
  .article{
    width: 100%;
  }
</style>

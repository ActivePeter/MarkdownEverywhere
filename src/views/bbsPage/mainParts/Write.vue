<template>
  <div class="selectBar">
    <el-card  shadow="hover">
      <div class="articlehead">
        <el-input
          class="titleset"
          type="text"
          placeholder="请输入标题"
          v-model="title"
          maxlength="20"
          show-word-limit
        >
        </el-input>
        <div style="min-width:max-content;margin-left:10px;">
          <el-button round class="btn" @click="showSelectDialogue(true)">设置标签</el-button>
          <el-tooltip class="item" effect="dark" :content="id?'更新文章':'发布文章'" :placement="id?'top':'right'">
            <el-button type="primary" :loading="uploading" :disabled="!hasOneChanged" :icon='id?"el-icon-upload2":"el-icon-check"' circle
            @click="uploadArticle"></el-button>
          </el-tooltip>
            
          <el-tooltip class="item" effect="dark" v-if="id" content="查看文章" placement="right">
            <el-button type="primary" icon="el-icon-view" circle
            @click="toRead()"></el-button>
          </el-tooltip>
        </div>
      </div>
      

    </el-card>
    <HorizonSpace/>
    <el-card  :body-style="{ padding: '0px' }"  shadow="hover">
      <mavon-editor 
            v-model="markdown" 
            ref="md" 
            @change="change" 
            style="min-height: 600px"
        />
    </el-card>
    <HorizonSpace/>
    <SelectDialogue ref="SelectDialogue" @updateChosenTags="onUpdateChosenTags"/>
  </div>
</template>

<script>
  import HorizonSpace from '@/components/common/HorizonSpace'
  import SelectDialogue from '@/components/dialogues/WriteSelect'
  import {CreateArticle,UpdateArticle,ReadArticle,ReadEditArticle} from "@/network/articles";
  import { mavonEditor } from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'
  export default {
    name: "selectBar",
    components: {
      mavonEditor,
      SelectDialogue,
      HorizonSpace,
    },
    data(){
      return{
        hasOneChanged:false,//通过或操作得到总的情况
        titleChanged:false,//分别记录数据有无改变
        bodyChanged:false,
        tagChanged:false,
        lastUpdateTitle:'',
        lastUpdateTags:[],
        lastUpdateBody:'',
        chosenTags:[],
        markdown:'', // 输入的markdown
        html:'',    // 及时转的html
        value: '',
        title:'',//文章标题
        id:null,
        uploading:false,
      }
    },
    mounted:function(){
       console.log("params:");
       console.log(this.$route);
      
      if(this.$route.params.id){//当前页有文章标号
        this.loadArticle()
      }else{
        this.$refs.SelectDialogue.initCategories()
      }
      
    },
    methods:{
      toRead(){
        if(this.$route.params.back){
          this.$store.commit('setNeedBack', true);
          this.$router.back(-1)
          
        }else{
          this.$router.push({ path: "/bbs/read/"+this.id });
        }
      },
      loadArticle(){
        //先检查是否有文章编辑权限。没有跳转到文章阅读页
        ReadEditArticle(this.$route.params.id).then((res)=>{
          // console.log("ReadEditArticle");
          // console.log(res);
          switch(res.status){
            case 200://有编辑权限，加载文章内容
              this.id=this.$route.params.id
              console.log(this);
              this.lastUpdateBody=this.markdown=res.data.body;
              this.lastUpdateTitle=this.title=res.data.title;
              //content=res.data.body;
              this.$refs.SelectDialogue.initCategories().then((res2)=>{
                // console.log("initCategories().then");
                // console.log(res2);
                if(res2==1){//加载完成tag分类表后设置当前文章的分类
                  this.$refs.SelectDialogue.setTags(res.data.tag_info)
                  this.chosenTags=this.$refs.SelectDialogue.getChosenTags()
                  this.lastUpdateTags=this.chosenTags=this.sortArr(this.chosenTags)
                }
              })
              break;
            case 300://无编辑权限，跳转到文章预览页
              break;
          }
        })
      },
      updateHasOneChanged(){
        this.hasOneChanged=this.tagChanged||this.titleChanged||this.bodyChanged
      },
      onUpdateChosenTags(tags){//交给SelectDialogue的时候设置
        this.chosenTags=tags;
        if(tags.length!=this.lastUpdateTags.length){//长度不等情况
          this.tagChanged=true;
        }else if(tags.length==0){//长度相等，都为0
          this.tagChanged=false;
        }else{
          //对数组进行排序，然后在做比较
          this.chosenTags=this.sortArr(this.chosenTags);
          var same=true;
          for (var i;i<tags.length;i++) {
            if(this.chosenTags[i]!=this.lastUpdateTags[i]){
              same=false;
              break;
            }
          }
          this.tagChanged=!same;
        }
        this.updateHasOneChanged()
      },
      uploadArticle(){
        if(this.title.length==0){
          
          this.$message({
            showClose: true,
            message: '标题未写',
            type: 'warning'
          });
        }
        else if(this.markdown.length==0){
          // ok=false;
          this.$message({
            showClose: true,
            message: '文章内容未写',
            type: 'warning'
          });
        }
        else if(this.chosenTags.length==0){
          // ok=false;
          this.$message({
            showClose: true,
            message: '标签未选择',
            type: 'warning'
          });
        }
        else{
          if(!this.id){
            this.uploading=true;
            CreateArticle(this.title,this.markdown,this.chosenTags).then(
              res=>{
                //console.log("result:");
                //console.log(res);
                switch(res.status){
                  case 201:
                    this.$message({
                      showClose: true,
                      message: '文章发布成功，你可以继续编辑',
                      type: 'success'
                    });
                    this.id=res.id;
                    this.$router.push({ path: "/bbs/write/"+this.id });
                    
                    this.lastUpdateBody=this.markdown
                    this.lastUpdateTitle=this.title
                    this.lastUpdateTags=this.chosenTags
                    this.tagChanged=false;
                    this.bodyChanged=false;
                    this.titleChanged=false;
                    this.hasOneChanged=false;
                    break;
                }
                this.uploading=false;
              }
            )
          }else{
            this.uploading=true;
            UpdateArticle(this.title,this.markdown,this.chosenTags,this.id).then(
              res=>{
                switch(res.status){
                  case 200:
                    this.$message({
                      showClose: true,
                      message: '文章更新成功，你可以继续编辑',
                      type: 'success'
                    });
                    this.lastUpdateBody=this.markdown
                    this.lastUpdateTitle=this.title
                    this.lastUpdateTags=this.chosenTags
                    this.tagChanged=false;
                    this.bodyChanged=false;
                    this.titleChanged=false;
                    this.hasOneChanged=false;
                    break;

                }
                this.uploading=false;
              }
            )
          }  
        }
      },
      askLeave(msg){
        if(msg) return window.confirm(msg);
        return window.confirm('当前文章内容未上传，确定要离开？');
      },
      showSelectDialogue(visible){
        this.$refs.SelectDialogue.dialogVisible=visible;
      },
      change(value, render){
        if(value!=this.lastUpdateBody){
          this.bodyChanged=true;
          //console.log("bodyChanged")
        }else{
          this.bodyChanged=false;
        }
        this.updateHasOneChanged()
            // render 为 markdown 解析后的结果[html]
            this.html = render;
      },
      resetData(){
        this.lastUpdateTitle='';
        this.lastUpdateTags=[];
        this.lastUpdateBody='';

        this.bodyChanged=false;
        this.tagChanged=false;
        this.titleChanged=false;
        this.hasOneChanged=false;

        this.chosenTags=[];
        this.markdown=''; // 输入的markdown
        this.html='';    // 及时转的html
        this.value='';
        this.title='';//文章标题
        this.id=null;
        this.uploading=false;
        this.$refs.SelectDialogue.initCategories();
      }
    },
    watch:{
      'title'(){
        if(this.title!=this.lastUpdateTitle){
          this.titleChanged=true;
          //console.log("titleChanged")
        }else{
          this.titleChanged=false;
        }
        this.updateHasOneChanged()
      }
    },
    beforeRouteLeave(to,from,next){
      // console.log("beforeRouteLeave");
      // console.log(from);
      
      if(this.hasOneChanged){//有变动
        //has id.upload at least once{
          
           if (this.askLeave()) {
               next()
           }else{
        //       next({
        //         path: from.path,
        //       })
              next()
              this.$router.push({ path: from.path});
           }
        
      }else{
        next()
      }
    },
    beforeRouteUpdate(to,from,next){
      // console.log("beforeRouteUpdate");
      // console.log(from);

      if(to.params.id==this.id){
        next()
        return
      }
      var newArticle =true;
      if(this.hasOneChanged){//如果当前内容有变动。提问
        newArticle=this.askLeave("当前编辑文章内容未更新，确认要离开");
        // console.log(this.markdown);
        // console.log(this.lastUpdateBody);
      }
      if(newArticle){
        if(to.params.id){
        //新建文章
          next()
          this.resetData();
          this.loadArticle()
        }else{//有文章内容。加载数据
            next()
            this.resetData();
            
        }
      }
    },
  }
</script>

<style scoped>
.articlehead{
  display: flex;
  justify-content:space-between;
}
.articlehead .titleset{
  max-width: 500px;
}
</style>

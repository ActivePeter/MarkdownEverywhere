<template>
  <div class="selectBar">
    <el-card :body-style="{ padding: '0px' }"  shadow="hover">
      <div v-if="catDetail.validCatCount>0" style="padding: 14px;padding-bottom:4px;" class="bottomShadowBox">
        <div
          :key="key.id"
          v-for="key in catDetail">
          <div v-if="key.cnt>0">
            <el-tag type="info" size="mini" class="info_tags hoverShadow">{{key.name}}</el-tag> 
            <el-tag  size="mini" class="info_tags hoverShadow"
            :key="key.id"
            v-for="key in key.tag_chosen">
            {{key.name}}</el-tag>
          </div>
          
        </div>
      </div>
      <hr v-if="catDetail.validCatCount>0" color=#EBEEF5 SIZE=1>
      <div style="padding: 14px;padding-bottom:14px;" class="bottomShadowBox">
       <el-button size="mini" 
        type="primary" plain
        @click="showSelectDialogue(true)"
        >
          选择标签来对文章进行筛选 
        </el-button>
      </div>
      
      
    </el-card>
    
    <HorizonSpace/>
    <el-card :body-style="{ padding: '0px' }"  shadow="hover">
      <div style="">
        <div
          :key="key.id"
          v-for="key in articleData">
          <ArticleBar
          :authorInfo="key.author_info"
          :createTime="key.created_time"
          :title="key.title"
          :id="key.id"
          ></ArticleBar>
          <hr color=#EBEEF5 SIZE=1>
        </div>
        
        
        <el-pagination
          class="bottomShadowBox"
          background
          layout="prev, pager, next"
          :total="articleCount"
          :page-size="10"
          :current-page="articleCurPage"
          @current-change="articlePageChange">
        </el-pagination>
      </div>
      
    </el-card>
    <HorizonSpace/>
    <SelectDialogue ref="SelectDialogue" @updateChosenTags="onUpdateChosenTags"
    @onTagsLoaded="refreshTags()"/>
  </div>
</template>

<script>
  import HorizonSpace from '@/components/common/HorizonSpace'
  import ArticleBar from './selectParts/ArticleBar'
  import SelectDialogue from '@/components/dialogues/WriteSelect'
  import {GetArticlesByTags} from "@/network/articles";
  export default {
    name: "selectBar",
    components: {
      ArticleBar,
      HorizonSpace,
      SelectDialogue,
    },
    mounted:function(){
      console.log(this.$route)
      this.updatePage()
      
      this.$refs.SelectDialogue.initCategories()
      
      
    },
    data(){
      return{
        chosenTags:[],
        catDetail:[],
        img:null,
        value: [],
        articleCurPage:1,
        articleCount:0,
        articleData:[],
        
      }
    },
    watch: {
      $route(from,to){
        if(from.query.page!=to.query.page){
          this.getArticlesByTags();
        }
        if(from.query.tags!=to.query.tags){
          this.refreshTags()
        }
        
      },
    },
    methods:{
      
      updatePage(){
        if(this.$route.query.hasOwnProperty("page")&&this.$route.query.page==this.articleCurPage){
          return;
        }
        var query1=JSON.parse(JSON.stringify(this.$route.query))
        query1.page=this.articleCurPage;
        //console.log(query1)
        this.$router.push({path: this.$route.path,query:query1});
      },
      articlePageChange(toPage){
        //console.log("articlePageChange")
        this.articleCurPage=toPage
        this.updatePage()
      },
      showSelectDialogue(visible){
        this.$refs.SelectDialogue.dialogVisible=visible;
      },
      getArticlesByTags(){
        GetArticlesByTags(this.chosenTags,this.articleCurPage).then((res=>{
          switch (res.status) {
            case 200:
              this.articleData=res.data.results;
              this.articleCount=res.data.count;
              break;
          }
        }))
      },
      refreshTags(){
        
        var tags;
        if(this.$route.query.hasOwnProperty('tags')){
          tags=this.$route.query.tags.split(',')
          // console.log(this.$route.query);
          // console.log(tags);
          this.$refs.SelectDialogue.chooseTagWithoutCat(tags)
          this.onUpdateChosenTags(tags,false);
        }else{
          tags=[]
          this.$refs.SelectDialogue.chooseTagWithoutCat(tags)
          this.onUpdateChosenTags(tags,false);
        }
        this.getArticlesByTags();
      },
      onUpdateChosenTags(tags,needJump){//交给SelectDialogue的时候设置
        //console.log(tags,needJump);
          var tagChanged;
          if(tags.length!=this.chosenTags.length){//长度不等情况
            tagChanged=true;
          }else if(tags.length==0){//长度相等，都为0
            tagChanged=false;
            console.log("b");
          }else{
            //对数组进行排序，然后在做比较
            tags=this.sortArr(tags);
            var same=true;
            for (var i in tags) {
              if(tags[i]!=this.chosenTags[i]){
                same=false;
                break;
              }else{
                //console.log(tags[i],this.chosenTags[i]);
              }
            }
            tagChanged=!same;
            console.log("c",this.chosenTags);
          }
          console.log(tagChanged);
          if(tagChanged){
            if(needJump){
              if(tags.length>0){
                var query1=JSON.parse(JSON.stringify(this.$route.query))
                query1.tags=tags+"";
                //console.log(query1)
                this.$router.push({path: this.$route.path,query:query1});
                //this.$router.push({ path: "/bbs/select/?tags="+tags});
              }else{
                var query1=JSON.parse(JSON.stringify(this.$route.query))
                delete query1.tags
                //console.log(query1)
                this.$router.push({path: this.$route.path,query:query1});
              }
            }else{
              this.chosenTags=tags;
              this.catDetail=this.$refs.SelectDialogue.categories;
              //console.log(this.catDetail)
              this.catDetail.validCatCount=this.catDetail.length;
              for(var cat of this.catDetail){
                var cnt=0;
                for(var index in cat.tag_chosen){
                  cnt++;
                }
                if(cnt==0){
                  this.catDetail.validCatCount--;
                }
                cat.cnt=cnt
              }
              this.$forceUpdate();
            }
            
          }
        
        
        
        //this.updateHasOneChanged()
      },
    }
  }
</script>
  
<style scoped>
  .el-pagination{
    padding-top:20px;
    padding-bottom:20px;
    padding-left:15px;

  }
  .info_tags{
    margin-right: 10px;
    margin-bottom: 10px;
  }
</style>

<template>
  <div class="overview">
    <el-row :gutter="20">
      <el-col :span="12">
        <ArticleList
        tagName="公告"
        :tagId="8"
        />
        <HorizonSpace/>
        <ArticleList
        tagName="提问"
        :tagId="4"
        />
        <HorizonSpace/>
        <ArticleList
        tagName="知识库"
        :tagId="7"
        />
        <HorizonSpace/>
      </el-col>
      <el-col :span="12">
        <ArticleList
        tagName="学习分享"
        :tagId="6"
        />
        <HorizonSpace/>
        <ArticleList
        tagName="项目记录"
        :tagId="5"
        />
        <HorizonSpace/>
        <ArticleList
        tagName="资源分享"
        :tagId="9"
        />
        <HorizonSpace/>
      </el-col>
    </el-row>
    
  </div>
</template>

<script>
  import NotiPart from './overviewParts/Notification'
  import TutorPart from './overviewParts/Tutor'
  import SharePart from './overviewParts/Share'
  import ProjectPart from './overviewParts/Project'
  import ArticleList from './overviewParts/ArticleList'
  import HorizonSpace from '@/components/common/HorizonSpace'
  

  export default {
    name: "overview",
    components: {
      NotiPart,
      TutorPart,
      SharePart,
      ProjectPart,
      HorizonSpace,
      ArticleList
    },
    data(){
      return{
        img:null,
        
      }
    }
  }
</script>

<style scoped>
</style>

<template>
  <div class="ArticleBar">
    <div style="padding:4px;" class="head bottomShadowBox">
      <div style="padding:10px;padding-right:10px" class="">
        <img  class="headpic" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></img>
      </div>
      <div style="width:100%; padding-top:10px;">
          <a :href="'/bbs/read/'+id" class="articleTitle" @click.prevent="toRead(id)">
            {{title}}
          </a>
        <div style="width:100%; display: flex;padding: 10px; padding-bottom:10px; justify-content:space-between;" class="">
          <div>
            <el-tag size="mini" type="info" class="hoverShadow" style="margin-right:10px;"> {{authorInfo.name}}</el-tag>
            <el-tag size="mini" type="info"  class="hoverShadow">{{createTime|makeTime}}</el-tag>
          </div>
          <div v-if="!simple">
            <el-tag size="mini" class="hoverShadow left_tags">查看数</el-tag>
            <el-tag size="mini" class="hoverShadow left_tags">回复数</el-tag>
            <el-tag size="mini" class="hoverShadow left_tags">点赞数</el-tag>
          </div>
          
        </div>
        
      </div>
    </div>
    
  </div>
</template>

<script>
import Util from "@/assets/js/util.js";
  export default {
    name: "ArticleBar",
    data(){
      return{
        img:null
      }
    },
    props:{
      simple:{
        type:Boolean,
        default() {
          return false;
        }
      },
      authorInfo:{
        type:Object,
        default() {
          return null;
        }
      },
      createTime:{
        type:String,
        default() {
          return "";
        }
      },
      title:{
        type:String,
        default() {
          return "";
        }
      },
      id:{
        type:Number,
        default() {
          return "";
        }
      },
    },
    filters: {
      makeTime: function (value) {
        var arr=value.split(":")
        if(arr.length>1){
          return arr[0].replace("T"," ")+":"+arr[1]
        }
        return "";
      }
    },
    methods:{
      toRead(id){
        console.log( "toread/" ,this.$route.fullPath)
        this.$store.commit('setNeedBack', true);
        this.$router.push({ path: "/bbs/read/"+id ,name: "read",params: {id: ""+id,back:this.$route.fullPath}});
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
    box-shadow: 0px 0px 5px rgba(0,0,0,0.3);
    margin-top: 5px;
  }
  .head{
    display: flex;
  }
  
  .articleTitle{
    margin-top: 10px;
    margin-left: 10px;
    font-size: 18px;
  }
  .articleTitle a{
    margin: 0px !important;
  }
  .left_tags{
    margin-left: 10px;
  }
</style>

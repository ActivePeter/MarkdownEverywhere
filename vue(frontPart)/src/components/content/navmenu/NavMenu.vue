<template>
  <div class="header bottomShadowBox">
    
    <el-button  class="btn1" 
    :icon="showSideBar?'el-icon-arrow-left':'el-icon-arrow-right'" 
    circle
    @click="switchSideBar()"
    >
    </el-button>
    <el-tooltip class="item" effect="dark" :content="'当前commit版本:  '+versionCode" placement="bottom">
      <div class="title1">
        <div>
          hanbaoaaa.Notes
        </div>
        
      </div>
    </el-tooltip>
    
  </div>
</template>

<script>
import Util from "@/assets/js/util.js";

export default {
  
    // <el-button  class="btn2" 
    // icon="el-icon-refresh" 
    // circle
    // @click="updatePageData()"
    // >
    // </el-button>
  name: "NavMenu",
  data() {
    return {
      token: "",
      navlist: [
        { id: "1", name: "首页", link: "/lab" },
        { id: "2", name: "易控成员", link: "/users" },
        { id: "3", name: "易控课堂", link: "/class" }
      ]
    };
  },
  methods: {
    exit(){
      this.$confirm('确认要退出账号吗？')
          .then(_ => {
            this.$store.commit('setUserinfo', null);
            this.$notify.info({
              title: '消息',
              message: '您已经退出账号',
              duration: 2000
            });
            //这边做一个跳转
            if (this.$route.matched.some(record => record.meta.requireAuth)){
              this.$router.push({ path: "/bbs/overview" });
            }
          })
          .catch(_ => {});
      
    },
    switchSideBar(){
      this.$emit("onSideBarStateChange",!this.showSideBar);
    },
    updatePageData(){
      this.$emit("onUpdatePageData");
    }

  },
  props:{
    showSideBar:{//作用是请求的时候提供数据，还有整明为子集评论框
      type:Boolean,
      default() {
        return false;
      }
    },
    versionCode:{//作用是请求的时候提供数据，还有整明为子集评论框
      type:String,
      default() {
        return "";
      }
    },
  }
};
</script>

<style scoped>

.header {
  width: 100%;
  height: 60px;
  position: fixed;
  top: 0;
  margin: auto;
  z-index:2000;
  
  background: #fff;
  border-bottom: 1px solid #EBEEF5;
  font-size: 30px;
}
.title1{
  text-align: center;
  font-size: 30px;
  position: relative; /*脱离文档流*/
  top: -38px; /*偏移*/
  margin-bottom: -7px;
  width: max-content;
  margin: auto;
  z-index:20;
}
.btn1{
  position: relative;
  z-index:22;
  margin-left:10px ;
  margin-top: 10px;
}
.btn2{
  position: relative;
  z-index:22;
}
</style>

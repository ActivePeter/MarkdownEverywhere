<template>
  <div class="userBar">
    <el-card shadow="hover" >
      
      <div class='middle' >
        肝帝排行榜
      </div>
      <HorizonSpace/>
    </el-card>
  </div>
</template>

<script>
  import HorizonSpace from '@/components/common/HorizonSpace'
  export default {
    name: "UserBar",
    components: {
        HorizonSpace,
    },
    data(){
      
      return{
        img:null,
      }
    }
  }
</script>

<style scoped>
  .middle{
    margin:0 auto !important;
    text-align: center;
  }
  .btn{
    width: 80%;
  }
</style>

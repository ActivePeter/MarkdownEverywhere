<template>
  <div class="carousel">
    <el-button icon="el-icon-search" circle></el-button>
  </div>
</template>

<script>
  export default {
    name: "Carousel",
    data(){
      return{
        img:null
      }
    }
  }
</script>

<style scoped>
  .el-carousel{
    padding-top: 70px;
  }

  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }

  .el-carousel__item--card{
    border-radius: 10px
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>

<template>
  <div id="home">
    <div id="content">
      <HorizonSpace/>
      <div id="right_bar">

        <userBar/>
      </div>
      <div id="Main"></div>
    </div>
  </div>
</template>

<script>
  import Carousel from './childHome/Carousel'
  import userBar from '@/components/bars/UserBar'
  import HorizonSpace from '@/components/common/HorizonSpace'
  export default {
    name: "Home",
    components: {
      //Carousel,
      //Selector,
      userBar,
      HorizonSpace,
    }
  }
</script>

<style scoped>
#content{
    min-width: 600px;
    max-width: 1100px;
    margin: 0 auto;
    padding-top: 60px;
    
}
#right_bar{
width: 270px;
    float: right;
    margin-right: 20px;
}
#Main{
  padding: 10px;
    margin: 0 310px 0 20px;
background: blue;
}
</style>

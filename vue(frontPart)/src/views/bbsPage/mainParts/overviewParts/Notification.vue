<template>
  <div class="Notification">
    <el-card :body-style="{ padding: '0px' }"  shadow="hover">
      <el-row>
        <el-col :span="18">
          <div class="grid-content bg-purple">
            <div class="block">
              <span class="demonstration">默认 Hover 指示器触发</span>
              <el-carousel height="150px">
                <el-carousel-item v-for="item in 4" :key="item">
                  <h3 class="small">{{ item }}</h3>
                </el-carousel-item>
              </el-carousel>
            </div>
          </div>
        </el-col>
        <el-col :span="6"><div class="grid-content bg-purple-light">
          
          </div></el-col>
      </el-row>
      
      <div style="padding: 14px;">
        <span>这里放通告。比如说实验室比赛什么的</span>
      </div>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: "Notification",
    data(){
      return{
        img:null
      }
    }
  }
</script>

<style scoped>
</style>

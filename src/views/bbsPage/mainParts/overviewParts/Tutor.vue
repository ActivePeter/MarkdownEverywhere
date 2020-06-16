<template>
  <div class="Notification">
    <el-row :gutter="20">
      <el-col :span="14">
        <el-card :body-style="{ padding: '0px' }"  shadow="hover">
          <div style="padding: 14px;">
            <span>这里放官方教学</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="10">
        <el-card :body-style="{ padding: '0px' }"  shadow="hover">
          <div style="padding: 14px;">
            <span>这里放问答版块</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
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

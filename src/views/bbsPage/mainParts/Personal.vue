<template>
  <div id="home">
    <div id="content">

      <el-container>
        
        <el-main>
          <el-card :body-style="{ padding: '0px' }">
            <div class="head">
              <div class="headpic">
                <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image">
              </div>
              <div class="headinfo">
                <span id="name">好吃的汉堡</span>
                <input class="sign" ref="test" placeholder="输入签名" v-model="sign" >
                
              </div>
              
              
            </div>
            <div >
              <el-container>
                <el-aside width="200px">
                  <el-menu
                    default-active="4"
                    class="el-menu-vertical-demo"
                    @open="handleOpen"
                    @close="handleClose">
                    <el-menu-item index="4">
                      <template slot="title">
                        <i class="el-icon-location"></i>
                        <span>个人信息</span>
                      </template>
                    </el-menu-item>
                    <el-submenu index="1">
                      <template slot="title">
                        <i class="el-icon-location"></i>
                        <span>我的文章</span>
                      </template>
                      <el-menu-item index="1-1">资源分享</el-menu-item>
                      <el-menu-item index="1-2">经验记录</el-menu-item>
                      <el-menu-item index="1-3">项目记录</el-menu-item>
                      <el-menu-item index="1-4">水文记录</el-menu-item>
                    </el-submenu>
                    <el-submenu index="2">
                      <template slot="title">
                        <i class="el-icon-location"></i>
                        <span>消息</span>
                      </template>
                      <el-menu-item index="1-1">回复</el-menu-item>
                      <el-menu-item index="1-2">私信</el-menu-item>
                    </el-submenu>
                    <el-menu-item index="3">
                      <i class="el-icon-setting"></i>
                      <span slot="title">设置</span>
                    </el-menu-item>
                  </el-menu>
                </el-aside>
                <el-main>
                  
                    
                  <div class="info_body">
                    <el-form v-if="editing" ref="form" :model="form" label-width="80px">
                      <el-form-item label="用户名">
                        <el-input v-model="form.name" style="width:200px;"></el-input>
                      </el-form-item>
                      <el-form-item label="QQ">
                        <el-input v-model="form.name" style="width:200px;"></el-input>
                      </el-form-item>
                      <el-form-item label="专业">
                        <el-input v-model="form.name" style="width:200px;"></el-input>
                      </el-form-item>
                      <el-form-item label="网站">
                        <el-input v-model="form.name"></el-input>
                      </el-form-item>
                      <el-form-item label="个人介绍">
                        <el-input type="textarea" autosize v-model="form.desc"></el-input>
                      </el-form-item>
                      
                      <el-form-item label="性别">
                        <el-radio-group v-model="form.resource">
                          <el-radio label="男"></el-radio>
                          <el-radio label="女"></el-radio>
                        </el-radio-group>
                      </el-form-item>
                      <el-form-item>
                        <el-button type="primary" @click="onSubmit">立即提交</el-button>
                        <el-button @click="cancelEditInfo">取消</el-button>
                      </el-form-item>
                    </el-form>
                    <el-form v-if="!editing" ref="form" :model="form" label-width="80px">
                      <el-form-item label="用户名">
                        ssss
                      </el-form-item>
                      <el-form-item label="QQ">
                        sssss
                      </el-form-item>
                      <el-form-item label="专业">
                        sssss
                      </el-form-item>
                      <el-form-item label="网站">
                        ssss
                      </el-form-item>
                      <el-form-item label="个人介绍">
                        sssss
                      </el-form-item>
                      <el-form-item label="性别">
                        ssssss
                      </el-form-item>
                    </el-form>
                    <el-tooltip v-if="!editing" class="item" effect="dark" content="修改个人信息" placement="left">
                      <el-button @click="startEditInfo()" class="edit_btn" type="primary" icon="el-icon-edit" circle></el-button>
                    </el-tooltip>
                  </div>
                  
                  
                </el-main>
              </el-container>
            </div>
          </el-card>
        </el-main>
      </el-container>

      <!-- <div id="right_bar">

        <userBar/>
        <HorizonSpace/>
        <rankBar/>
      </div>
      <div id="Main">
        <router-view></router-view>
        
      </div> -->
    </div>
  </div>
</template>

<script>
  import HorizonSpace from '@/components/common/HorizonSpace'
  
  export default {
    name: "Home",
    components: {
      //Carousel,
      //Selector,
      HorizonSpace,
      
    },
    mounted(){

    },
    methods:{
      loadPersonInfo(){

      },
      startEditInfo(){
        this.editing=true;
      },
      cancelEditInfo(){
        this.editing=false;
      },
      finishEditInfo(){
        this.editing=fasle;
      },
    },
    data(){
      return{
        editing:false,
        sign:"",
        form: {
          name: '',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        }
      }
    }
  }
</script>

<style scoped>
.head{
  display: flex;
  border-bottom: 1px solid #DCDFE6;
}
.headpic{
  border-right: 1px solid #E4E7ED;
  width: 200px;
}
.head .headpic img{
  margin: 30px 49px 30px 50px;
  border-radius: 100px;
  border-style: solid;
  border-width: 3px;
  border-color: white;
  width:  100px;
  box-shadow: 0px 3px 10px rgba(0,0,0,0.5)
}
.head .headinfo{
  padding-top: 40px;
  margin-left: 30px;
  width: 100%;
}
.head .headinfo #name{
  font-size: 27px;
}
#content{
    min-width: 800px;
    max-width: 1100px;
    margin: 0 auto;
    
    height: 100%;
}
#right_bar{
width: 270px;
    float: right;
    margin-right: 20px;
}
#Main{
    margin: 0 310px 0 20px;
    
}
#sidebar{
  margin-right: 20px;
}
.el-menu{
  min-height: 400px;
  height: 100%;
}
.sign{
  padding: 10px;
  border: none;
  width: 90%;
  margin-left: -10px;
  margin-top: 10px;
  color:#909399;
}
.el-form{
  width: 100%;
}
.edit_btn{
  height: max-content;
}
.info_body{
  display: flex;
}
</style>

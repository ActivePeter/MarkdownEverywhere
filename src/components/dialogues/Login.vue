<template>
  <div class="login">
    <el-dialog :title="loginState?'登入':'注册'" :visible.sync="dialogVisible" width='300px'
      :close-on-click-modal='false' @close='onClose()' id="logindialog">
      <el-form ref="form" :model="form" :rules="rules" status-icon>
        <el-form-item prop="name" ref="nameRef">
          <el-input v-model="form.name" :placeholder="loginState?'用户名/邮箱':'邮箱'"  auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item prop="pw0">
          <el-input v-model="form.pw0" type="password" placeholder="密码"  auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item prop="pw1" v-if="!loginState">
          <el-input v-model="form.pw1" type="password" placeholder="重复密码"  auto-complete="off"></el-input>
        </el-form-item>
        
          
          <div id="checkCodeLine" v-if="!loginState">
            <el-form-item prop="code" v-if="!loginState">
              <el-input v-model="form.code" placeholder="验证码"  auto-complete="off"></el-input>
           </el-form-item>
            <el-button id="checkCodeBtn" @click="getCode" :loading="gettingCode">获取验证码</el-button>
          </div>
          
            
        
      </el-form>
      <div class="middle"> 
        <el-button class="btn" :loading="loading" type="primary" @click="submit()">{{loginState?'登入':'注册'}}</el-button>
        <el-button type="text" @click="setLoginState(!loginState)">{{loginState?'没有账号，前往注册': '已有账号，前往登入' }}</el-button>
      </div> 
      <div slot="footer" class="dialog-footer">
        
      </div>
    </el-dialog>
  </div>
  

</template>

<script>
  import HorizonSpace from '@/components/common/HorizonSpace'
  import {Login, GetSms,Register,GetCode} from "@/network/users";
  import {checkUsername, checkPassword,checkPassword2, checkCode} from '@/utils/validate'

  export default {
    name: "login",
    components: {
        HorizonSpace,
    },
    
    methods:{
      getCode(){//获取验证码
        if(this.$refs.nameRef.validateState=="success"){
          console.log("sasdasd");
          this.gettingCode=true;
          GetCode({
            email: this.form.name,
          }).then(res => {
            console.log("res");
            console.log(res);
            switch(res.status){
              case 200:
                switch(res.data.status){
                  case 0:
                    this.$alert("已经发送验证码到邮箱，请注意查看", '提示', {
                        confirmButtonText: '确定',
                    });
                    break;
                  case 1:
                    this.$alert('不存在的邮箱，请填写您可用的邮箱', '警告', {
                      confirmButtonText: '确定',
                    });
                    break;
                }
                break;
              case 400:
                switch(res.data.email.status){
                  case "1":
                    this.$alert("此邮箱已注册", '警告', {
                      confirmButtonText: '确定',
                    });
                    break;
                  case "3":
                    this.$alert("请在上一次获取一分钟后再进行请求", '警告', {
                      confirmButtonText: '确定',
                    });
                    break;
                }
                break;
            }
            this.gettingCode=false;
          })
        }else{
          this.$alert('邮箱无效，请填写正确格式的邮箱', '警告', {
            confirmButtonText: '确定',
          });
        }

        
        
      },
      onClose(){//关闭dialogue时清楚文本框
        this.$refs.form.resetFields();
        this.form={
          name: '',
          pw0: '',
          pw1: '',
          code: '',
        };
      },
      setLoginState(state){//设置登入/注册
        this.$refs.form.resetFields();
        this.loginState=!this.loginState;
        
      },
      isValid(){
        return 
      },
      submit(){//提交登入或注册请求
        
        this.$refs.form.validate(valid => {
          if (valid) {
            this.loading=true;
            if(this.loginState){
              Login({
                user: this.form.name,
                password: this.form.pw0,
              }).then(res => {
                console.log(res);
                switch(res.status){
                  case 400:
                    this.$alert('用户名或密码错误', '警告', {
                      confirmButtonText: '确定',
                    });
                    this.loading=false;
                    break;
                  case 200:
                    this.$store.commit('setUserinfo', {
                      token:res.data.token,
                      id:res.data.results.id,
                      name:res.data.results.username,
                      email:res.data.results.email,
                      
                    });
                    this.dialogVisible=false;
                    this.$notify.info({
                      title: '消息',
                      message: '您已经成功登录',
                      duration: 2000
                    });
                    break;
                  default:
                    this.loading=false;
                    break;
                }

              }).catch(err => {
                this.loading=false;
                console.log("ERROR");
                console.log(err);
              })
            }else{
              Register({
                email: this.form.name,
                password: this.form.pw0,
                password2: this.form.pw1,
                code:this.form.code,
              }).then(res => {
                console.log(res);
                switch(res.status){
                  case 200:
                    switch (res.data.status) {
                      case 0:
                        this.$alert('注册成功', '提示', {
                          confirmButtonText: '前往登入',
                        });
                        console.log("hhhhhh");
                        this.loginState=true;
                        this.loading=false;
                        break;
                      default:
                        this.loading=false;
                        break;
                    }
                    break;
                  case 400:
                    if(res.data.code){
                      switch (res.data.code.status) {
                        case "4":
                          this.$alert('注册失败，验证码错误', '提示', {
                            confirmButtonText: '确认',
                          });
                          break;
                        case "5":
                          this.$alert('注册失败，未向邮箱发送验证码', '提示', {
                            confirmButtonText: '确认',
                          });
                          break;
                        default:
                          
                          break;
                      }
                    }else{
                      this.$alert('注册失败，用户名已存在', '提示', {
                        confirmButtonText: '确认',
                      });
                    }
                }
                this.loading=false;
              }).catch(err => {
                this.loading=false;
                console.log(err);
              })
            }
          }
        })
        
        
      }
    },
    data(){
      return {
        loginState:true,
        gettingCode:false,
        dialogVisible: false,
        form: {
          name: '',
          pw0: '',
          pw1: '',
          code:'',
        },
        loading:false,
        rules: {
          name: [{validator: checkUsername, trigger: 'blur'}],
          pw0: [{validator: checkPassword, trigger: 'blur'}],
          pw1: [{validator: checkPassword2, trigger: 'blur'}],
          code: [{validator: checkCode, trigger: 'blur'}],
        },
        timer: null,
      };
    }
  }
</script>

<style scoped>
  .middle{
    margin:0 auto !important;
    text-align: center;
  }
  .btn{
    width: 100%;
  }
  #checkCodeBtn{
    margin-left: 15px;
    margin-bottom: 22px;
  }
  #checkCodeLine{
    display: flex;
    width: 100%;
  }
</style>

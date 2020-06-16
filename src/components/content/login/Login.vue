<template>
  <div id="login">
    <el-card class="box-card">
      <div class="menu-tab">
      <span v-for="(item, index) in menuTab" :key="index" :class="{'current':item.current}"
            @click="toggleMenu(index)">{{item.txt}}
      </span>
      </div>
      <!--表单-->
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="Form" status-icon>
        <el-form-item prop="username">
          <label for="username">邮箱</label>
          <el-input id="username" type="text" v-model="ruleForm.username" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <label for="password">密码</label>
          <el-input id="password" type="password" v-model="ruleForm.password" auto-complete="off" minlength="6"
                    maxlength="20"></el-input>
        </el-form-item>
        <el-form-item prop="code">
          <label for="code">验证码</label>
          <el-row :gutter="30">
            <el-col :span="16">
              <el-input id="code" type="text" v-model.number="ruleForm.code" auto-complete="off" minlength="6"
                        maxlength="6"></el-input>
            </el-col>
            <el-col :span="8">
              <el-button type="success" @click="getSms('ruleForm')">验证码</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">{{menuTab[0].txt}}</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
  import {checkUsername, checkPassword, checkCode} from '@/utils/validate'
  import {Login, GetSms} from "@/network/users";

  export default {
    name: "Login",
    data() {
      return {
        menuTab: [
          {txt: '登录', current: true},
          {txt: '注册', current: false},
        ],
        ruleForm: {
          username: "",
          password: "",
          code: "",
        },
        rules: {
          username: [{validator: checkUsername, trigger: 'blur'}],
          password: [{validator: checkPassword, trigger: 'blur'}],
          code: [{validator: checkCode, trigger: 'blur'}],
        },
      }
    },
    methods: {
      //切换为注册
      toggleMenu(index) {
        if (index === 1) {
          this.menuTab[0].current = false;
          this.menuTab[1].current = true;
          this.$router.push('/register')
        }
      },
      //获取验证码
      getSms(formName) {
        this.$refs[formName].validate(valid => {
          if (valid) {
            GetSms({username: this.ruleForm.username}).then(response =>{

            }).catch(error=>{

            })
          } else {
            console.log("表单校验失败");
            return false;
          }
        })
      },
      //提交表单
      submitForm(formName) {
        this.$refs[formName].validate(valid => {
          if (valid) {
            Login({
              username: this.ruleForm.username,
              password: this.ruleForm.password,
            }).then(res => {
              console.log(res);
            }).catch(err => {
              console.log(err);
            })
          } else {
            console.log("表单校验失败");
            return false;
          }
        })
      },
    }
  }
</script>

<style scoped>
  .box-card {
    width: 400px;
    margin: auto;
    position: absolute;
    top: 15%;
    left: 0;
    bottom: 15%;
    right: 0;
    border-radius: 10px;
  }

  .Form label {
    display: block;
  }

  .el-button {
    width: 100%;
  }


</style>

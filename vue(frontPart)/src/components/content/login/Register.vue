<template>
  <div id="register">
    <el-card class="box-card">
      <div class="menu-tab">
      <span v-for="(item, index) in menuTab" :key="index" :class="{'current':item.current}"
            @click="toggleMenu(index)">{{item.txt}}
      </span>
      </div>
      <!--表单-->
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="Form" status-icon>
        <el-form-item prop="username">
          <label>邮箱</label>
          <el-input type="text" v-model="ruleForm.username" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <label>密码</label>
          <el-input type="password" v-model="ruleForm.password" auto-complete="off" minlength="6"
                    maxlength="20"></el-input>
        </el-form-item>
        <el-form-item prop="password2">
          <label>重复密码</label>
          <el-input type="password" v-model="ruleForm.password2" auto-complete="off" minlength="6"
                    maxlength="20"></el-input>
        </el-form-item>

        <el-form-item prop="code">
          <label>验证码</label>
          <el-row :gutter="30">
            <el-col :span="16">
              <el-input type="text" v-model.number="ruleForm.code" auto-complete="off" minlength="6"
                        maxlength="6"></el-input>
            </el-col>
            <el-col :span="8">
              <el-button type="success" @click="getSms">验证码</el-button>
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

  export default {

    name: "Register",
    data() {
      // <!--验证重复密码-->
      let checkPassword2 = (rule, value, callback) => {
        if (value === "") {
          callback(new Error("请再次输入密码"));
        } else if (value !== this.ruleForm.password) {
          callback(new Error("两次输入密码不一致!"));
        } else {
          callback();
        }
      };

      return {
        menuTab: [
          {txt: '登录', current: false},
          {txt: '注册', current: true}
        ],
        ruleForm: {
          username: "",
          password: "",
          password2: "",
          code: "",
        },
        rules: {
          username: [{validator: checkUsername, trigger: 'blur'}],
          password: [{validator: checkPassword, trigger: 'blur'}],
          password2: [{validator: checkPassword2, trigger: 'blur'}],
          code: [{validator: checkCode, trigger: 'blur'}],
        },
      }
    },
    methods: {
      toggleMenu(index) {
        if (index === 0) {
          this.menuTab[0].current = true;
          this.menuTab[1].current = false;
          this.$router.push('/login')
        }
      },
      //获取验证码
      getSms(){
        //  验证表单是否为空

        //  请求验证码
      },
      submitForm(formName) {
        this.$refs[formName].validate(valid => {
          if (valid) {
            setTimeout(() => {
              alert('注册成功')
            }, 400);
          } else {
            console.log("error submit!!");
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

<template>
  <div id="card">
    <HorizonSpace />
    <HorizonSpace />
    <el-col class="left-col">
      <el-card class="box-card1"></el-card>
    </el-col>
    <el-col class="right-col">
      <el-card class="box-card2" v-for="item in PersonImformationList" :key="item">
        <div>
          <el-row>
            <span class="avatar">
              <el-avatar :size="60" icon="el-icon-user-solid"></el-avatar>
            </span>
            <span class="username">{{item.username}}</span>
          </el-row>
          <div style="line-height:20px" class="text1">
            <div>昵称:{{item.nickname}}</div>
            <div>性别:{{item.gender}}</div>
            <div>邮箱:{{item.email}}</div>
            <div>专业:{{item.major}}</div>
            <div>简介:{{item.introduction}}</div>
          </div>
        </div>
      </el-card>
    </el-col>
  </div>
</template>

<script>
import { getUsersMultiData } from "@/network/users";
import HorizonSpace from "@/components/common/HorizonSpace";

export default {
  name: "Card",
  data() {
    return {
      PersonImformationList: [
        {
          username: "我配吗",
          nickname: "小灰",
          gender: "男",
          email: "aaaaa@abc.com",
          major: "xx工程"
        },
        {
          nickname: "小灰",
          gender: "男",
          email: "aaaaa@abc.com",
          major: "xx工程"
        }
      ]
    };
  },
  created() {
    getUsersMultiData().then(res => {
      this.res = res.data.results;
    });
  },
  components: {
    HorizonSpace
  }
};
</script>

<style scoped>
.left-col {
  float: left;
  width: 30%;
}

.right-col {
  float: right;
  width: 70%;
}

.box-card1 {
  height: 550px;
  width: 220px;
  border-radius: 10px; /*圆角*/
  margin: 40px 220px;
}

.box-card2 {
  height: 300px;
  width: 260px;
  border-radius: 10px; /*圆角*/
  padding: 10px 10px 10px 12px;
  margin: 40px 40px;
}

.username {
  position: absolute;
  top: 10px;
  left: 45%;
  font-size: 30px;
}

.text1 {
  margin: 20px 10px 10px 10px;
  font-size: 14px;
}
</style>

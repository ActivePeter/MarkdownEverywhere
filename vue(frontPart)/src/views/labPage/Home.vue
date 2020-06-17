<template>
  <div id="home">
    <div id="content">
      <el-carousel trigger="click" height="400px">
        <!-- 图片浏览框 -->
        <el-carousel-item v-for="item in img_list" :key="item.id">
          <img style="width:100%;	height:auto,z-index:10" v-bind:src="item.idView" alt="none" />
          <!-- 图片的格式还需要进行调整 -->
        </el-carousel-item>
      </el-carousel>
      <HorizonSpace />
      <el-card shadow="hover">
        <div class="listshow1">
          <div class="title">{{LabName}}</div>
        </div>
        <!-- 实验室名称和简介card -->
        <HorizonSpace />
        <p style="text-indent:20px;line-height:40px">{{LabDiscribtion}}</p>
      </el-card>
      <HorizonSpace />
      <el-card shadow="hover">
        <div class="listshow2">
          <div class="title">培养方案</div>
        </div>
        <HorizonSpace />
        <div>
          <!-- 培养方案介绍 -->
          <el-steps :active="1" align-center>
            <el-step
              :title="TrainDiscriptionList[0].step"
              :description="TrainDiscriptionList[0].Discription"
            ></el-step>
            <el-step
              :title="TrainDiscriptionList[1].step"
              :description="TrainDiscriptionList[1].Discription"
            ></el-step>
            <el-step
              :title="TrainDiscriptionList[2].step"
              :description="TrainDiscriptionList[2].Discription"
            >
              <el-card shadow="hover"></el-card>
            </el-step>
          </el-steps>
        </div>
      </el-card>
      <HorizonSpace />
      <el-card shadow="hover">
        <div class="listshow3">
          <div class="title">活动记录</div>
        </div>
        <HorizonSpace />
        <div class="block">
          <el-timeline>
            <!-- 活动记录的时间 -->
            <el-timeline-item :timestamp="ActivtyList[2].Time" placement="top">
              <el-card shadow="hover">
                <h4>{{ActivtyList[2].activty}}</h4>
              </el-card>
            </el-timeline-item>
            <el-timeline-item :timestamp="ActivtyList[1].Time" placement="top">
              <el-card shadow="hover">
                <h4>{{ActivtyList[1].activty}}</h4>
              </el-card>
            </el-timeline-item>
            <el-timeline-item :timestamp="ActivtyList[0].Time" placement="top">
              <el-card shadow="hover">
                <h4>{{ActivtyList[0].activty}}</h4>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </div>
      </el-card>
      <HorizonSpace />
      <el-card shadow="hover">
        <div class="listshow4">
          <div class="title">项目记录</div>
        </div>
        <HorizonSpace />
        <div class="block">
          <el-timeline>
            <el-timeline-item placement="top">
              <el-card shadow="hover">
                <h4>易控OJ</h4>
                <p>实验室为了招新而开发的OJ,极大地简化了收发作业的流程并能够统计作业完成情况,便于了解学习中的难点。</p>
              </el-card>
            </el-timeline-item>
            <el-timeline-item placement="top">
              <el-card shadow="hover">
                <h4>易表格</h4>
                <p>可以快速收集齐数据，并一键导出,加入了表格的分析功能，用漂亮的图表展示汇集的数据和数据分类。</p>
              </el-card>
            </el-timeline-item>
            <el-timeline-item placement="top">
              <el-card shadow="hover">
                <h4>签到小助手</h4>
                <p>一款开发中的微信/QQ双平台的小程序,能够帮助学生会/班委完成各类签到任务,并提供月/学期统计,一键查询,一键导出等功能.</p>
              </el-card>
            </el-timeline-item>
            <el-timeline-item placement="top">
              <el-card shadow="hover">
                <h4>实验室NAS</h4>
                <p>通过树莓派搭建属于实验室的校园内高速传输云盘</p>
              </el-card>
            </el-timeline-item>
            <el-timeline-item placement="top">
              <el-card shadow="hover">
                <h4>指纹锁</h4>
                <p>通过指纹模块即可完成寝室/实验室门的开关,让你免受忘带钥匙的烦恼</p>
              </el-card>
            </el-timeline-item>
            <el-timeline-item placement="top">
              <el-card shadow="hover">
                <h4>悦生活小程序</h4>
                <p>一款集手账,账本,待办事件簿为一体的生活类的小程序,帮助你有条理的安排你的生活</p>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </div>
      </el-card>
      <HorizonSpace />
    </div>
  </div>
</template>

<script>
import userBar from "@/components/bars/UserBar";
import HorizonSpace from "@/components/common/HorizonSpace";
import Util from "@/assets/js/util.js";
export default {
  name: "Home",
  data() {
    return {
      PPTNum: 4,
      showIndex: null,
      LabName: "易控简介",
      LabDiscribtion:
        "易控实验室是一个由对技术充满热爱的小伙伴们组成的技术团体,并一直受到网络空间安全学院的大力支持，学院给予场地支持。易控实验室面向成都信息工程大学全体学生招新，积极参与校内外各项知名赛事斩获颇丰。",
      // 图片地址数组
      img_list: [
        { id: 0, idView: require("../../assets/img/1.jpg") }, //恶心人恶心人
        { id: 1, idView: require("../../assets/img/2.jpg") },
        { id: 2, idView: require("../../assets/img/3.jpg") },
        { id: 3, idView: require("../../assets/img/4.jpg") }
      ],
      TrainDiscriptionList: [
        {
          title: "初步培训",
          Discription:
            "通过线上直播与课后作业帮助新伙伴快速入门C语言,初步了解编程."
        },
        {
          title: "分组培训",
          Discription: "在硬件组与开发组中选择自己感兴趣的方向加入相应组的培训"
        },
        {
          title: "进阶培训",
          Discription:
            "硬件组通过单片机的培训与参加校内的赛事带领小伙伴们开始深入学习电路设计与单片机设计，C进一步学习与HTML+CSS的培训,让小伙伴们培养自己的兴趣并对未来的前端方向和后台方向都有所了解"
        }
      ],
      ActivtyList: [
        { Time: "2018/4/12", activty: "活动0" },
        { Time: "2018/5/12", activty: "活动0" },
        { Time: "2018/6/12", activty: "活动0" }
      ],
      backgroundDiv: {
        backgroundImage:
          "url(" + require("../../assets/img/官网background.jpg") + ")",
        backgroundRepeat: "no-repeat",
        backgroundSize: "100% 100%",
        backgroundattachment: "fixed",
        zindex: "10"
      }
    };
  },
  methods: {
    PPTChangeNum(num) {
      this.PPTNum = num;
    },
    PPTChangePhoto(num) {
      for (i = 0; i < num; i++) {
        return img_list[i];
      }
    },
    ShowDetails(num) {
      var el = document.getElementsByClassName(`listshow${num}`)[0];
      console.log(el.offsetTop);
      if (this.showIndex == num) {
        this.showIndex = null;
      } else {
        this.showIndex = num;
      }
      this.$nextTick(function() {
        document.getElementById('app').scrollTo({ behavior: "smooth", top: el.offsetTop - 100 });
      });
    }
  },
  mounted() {
    var that = this;
    Util.$on("showDetails", num => {
      console.log(num);
      that.ShowDetails(num);
    });
  },
  components: {
    //Carousel,
    //Selector,
    userBar,
    HorizonSpace
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.handleFun);
  }
};
</script>

<style scoped>
#content {
  min-width: 600px;
  max-width: 1100px;
  margin: 0 auto;
}
#right_bar {
  width: 270px;
  float: right;
  margin-right: 20px;
}
#Main {
  margin: 0 310px 0 20px;
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 10px 10px;
}

.el-carousel__item:nth-child(2n) {
  background-color: #d3dce6;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>
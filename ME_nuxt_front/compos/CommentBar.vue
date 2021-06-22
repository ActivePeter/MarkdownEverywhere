<template>
  <div class="body">
    <div id="title">
      <div id="name">{{ name }}</div>
      <div id="contactBtn">联系方式 {{ contact }}</div>
    </div>

    <div class="little-bar" id="time">{{ time }}</div>
    <div id="content" v-html="content"></div>
    <div id="replyCnt">
      回复数 ({{ childCnt }})
      <span>点击展开</span>
    </div>
    <!-- <TreeNode /> -->
    <!-- <TreeNode :childTreeData="treeData" />
     -->
    <!-- <div :key="index" v-for="(item, index) in treeData.file_nodes">
      {{ item.name }}
    </div> -->
    <!-- <slot></slot> -->
  </div>
</template>

<script>
// import func from 'vue-editor-bridge'
export default {
  name: 'CommentBar',
  components: {},

  watch: {
    // treeData(val) {
    //   if (this.$isServer) {
    //     console.log("data in", val);
    //   }
    // },
  },
  // data() {
  //   return {
  //     treeData: {},
  //   }
  // },

  mounted() {
    // console.log(this.treeRoot)
    // res = $.ajax({
    //   type: "get",
    //   url: "http://localhost:8080/",
    //   // cache: false,
    //   async: false,
    //   // dataType: $.browser.msie ? "text" : "xml",
    //   // success: function (xmlobj) {},
    // });
    // console.log(res);
  },
  props: {
    childCnt: {
      type: Number,
      default: 0,
    },
    children: {
      type: Array,
      default: () => {
        return []
      },
    },
    contact: {
      type: String,
      default: '',
    },
    content: {
      type: String,
      default: '',
    },
    depth: {
      type: Number,
      default: 0,
    },
    email: {
      type: String,
      default: '',
    },
    fatherId: {
      type: Number,
      default: 0,
    },
    hasFather: {
      type: Boolean,
      default: false,
    },
    id: {
      type: Number,
      default: 0,
    },
    name: {
      type: String,
      default: '',
    },
    time: {
      type: String,
      default: '',
    },
  },
  methods: {
    // sleep(delay) {
    // },
    // setTreeData(data) {
    //   this.treeData = data;
    // },
  },
}
</script>

<style scoped>
.body {
  padding: 10px;
  border-left: 3px solid rgb(0, 122, 204);
}
#name {
  font-size: 30px;
  padding-bottom: 5px;
}
#contactBtn {
  margin-left: 10px;
  font-size: 14px;
  color: rgba(255, 255, 255, 0.4);
}
#title {
  display: -webkit-flex; /* Safari */
  display: flex;
  align-items: flex-end;
  padding-bottom: 10px;
}
#time {
  margin-bottom: 10px;
  font-size: 14px;
  color: rgba(255, 255, 255, 0.4);
}
#replyCnt {
  margin-top: 10px;
  font-size: 14px;
  color: rgba(255, 255, 255, 0.4);
}
#content {
  background: rgb(51, 51, 51);
  margin: 0 -10px;
  padding: 10px;
}
</style>

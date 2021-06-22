<template>
  <div class="card">
    <!-- <TreeNode /> -->
    <!-- <TreeNode :childTreeData="treeData" />
     -->
    <!-- <div :key="index" v-for="(item, index) in treeData.file_nodes">
      {{ item.name }}
    </div> -->
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: 'Card',
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
    treeRoot: {
      // 作用是请求的时候提供数据，还有整明为子集评论框
      type: Object,
      default() {
        return {}
      },
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
.card {
  width: calc(100% - 20px);
  margin: 10px;
  min-height: 30px;
  background: rgb(37, 37, 38);
  padding: 0;
}
</style>

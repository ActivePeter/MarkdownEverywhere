<template>
  <div class="sidebar1">
    <!-- <TreeNode /> -->
    <!-- <TreeNode :childTreeData="treeData" />
     -->
    <!-- <div :key="index" v-for="(item, index) in treeData.file_nodes">
      {{ item.name }}
    </div> -->
    <TreeNode id="tm" :childTreeData="treeRoot" :isMD="false" :isRoot="true" />
  </div>
</template>

<script>
import TreeNode from './TreeNode.vue'

export default {
  name: 'TreeMenu',
  components: {
    TreeNode,
  },

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
#tm {
  white-space: nowrap;
}
</style>

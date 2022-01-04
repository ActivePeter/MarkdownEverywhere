<template>
  <div class="sidebar1">
    <!-- <TreeNode /> -->
    <!-- <TreeNode :childTreeData="treeData" />
     -->
    <!-- <div :key="index" v-for="(item, index) in treeData.file_nodes">
      {{ item.name }}
    </div> -->
    <div id="log" @click="switch_history_show_state">
      <span>
        <i class="el-icon-time"></i>
      </span>
      <span v-if="show_history">hide update log</span>
      <span v-else>show update log</span>
    </div>
    <TreeNode id="tm" :childTreeData="treeRoot" :isMD="false" :isRoot="true" />
  </div>
</template>

<script>
import TreeNode from "./TreeNode.vue";

export default {
  name: "TreeMenu",
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
        return {};
      },
    },
    show_history: {
      type: Boolean,
      default() {
        return true;
      },
    },
  },
  methods: {
    find_node_in_arr(arr, target) {
      // var arr = arr;
      for (var i = 0; i < arr.length; i++) {
        if (arr[i].name == target) {
          return arr[i];
        }
      }
      console.log("err: node not found ", arr, target);
      return null;
    },
    switch2node(node) {
      var nodes = node.split("/");
      // console.log("msg swicth2node ", nodes);
      var found_node = this.treeRoot;
      for (var i = 0; i < nodes.length; i++) {
        // console.log("found ", found_node);
        if (i != nodes.length - 1) {
          // console.log("look in folders ", i, nodes.length - 1);
          found_node = this.find_node_in_arr(found_node.folder_nodes, nodes[i]);
          //不是最后一个节点。
        } else {
          // console.log("look in files ");
          found_node = this.find_node_in_arr(found_node.file_nodes, nodes[i]);
          // console.log("final found ", found_node);
          if (found_node) {
            this.$router.push({ path: "/id/" + found_node.index });
          }
        }
        // console.log(nodes[i]);
      }
    },
    switch_history_show_state() {
      console.log("switch_history_show");
      this.$emit("switch_history_show");
    },
    // sleep(delay) {
    // },
    // setTreeData(data) {
    //   this.treeData = data;
    // },
  },
};
</script>
<style scoped>
#tm {
  white-space: nowrap;
}
#log {
  padding: 12px;
  cursor: pointer;
}
#log:hover {
  color: #eee;
}
</style>

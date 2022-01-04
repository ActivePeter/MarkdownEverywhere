<template>
  <div v-show="show" id="history_layer">
    <div id="date_bar" v-for="(item, index) in history_log" :key="'d_' + index">
      <div id="date">{{ item.date }}</div>

      <div
        id="commits"
        v-for="(item1, index) in item.change_nodes"
        :key="'c_' + index"
      >
        <div class="hashbar">{{ item1.commit_hash }}</div>
        <div
          id="single_file"
          v-for="(item2, index) in item1.single_changes"
          :key="'f_' + index"
          @click="switch_2_node(item2.file_path)"
        >
          <div>
            <i v-if="item2.change_type == 'mod'" class="el-icon-edit"></i>
            <i v-else class="el-icon-circle-plus-outline"></i>
            {{ item2.file_path }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "History",
  components: {
    // TreeNode,
  },

  watch: {
    history_log(val) {
      //   if (this.$isServer) {
      console.log("history data in", val);
      //   }
    },
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
    history_log: {
      // 作用是请求的时候提供数据，还有整明为子集评论框
      type: Array,
      default() {
        return [];
      },
    },
    show: {
      type: Boolean,
      default: true,
    },
  },
  methods: {
    switch_2_node(node) {
      //   console.log("switch 2 ", node);
      this.$emit("swicth2node", node);
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
#date {
  font-size: 1.7em;
  padding-bottom: 10px;
}
#commits {
  padding: 12px;
}
#date_bar {
  padding: 10px;
}
#history_layer {
  padding-top: 20px;
}
#single_file {
  padding-left: 10px;
  padding-top: 3px;

  cursor: pointer;
}
#single_file :hover {
  color: #eee;
}
.hashbar {
  background-color: #333;
  padding: 2px;
  margin: 3px;
  width: intrinsic;
  width: -moz-max-content;
  width: -webkit-max-content;
  font-size: 0.5em;
  border-radius: 5px;
  color: #666;
  cursor: pointer;
}
</style>

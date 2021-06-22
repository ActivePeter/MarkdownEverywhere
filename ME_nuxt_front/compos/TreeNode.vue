<template>
  <div :class="isRoot ? '' : 'treenode'">
    <!-- <div :key="index" v-for="(item, index) in childTreeData.file_nodes">
      {item.name}
      
    </div> -->
    <!-- <TreeNode /> -->
    <div v-if="!isRoot" @click="nodeClick()">
      <div :class="selected ? 'selected' : ''" id="nodetitle" v-if="!isMD">
        <span>
          <i class="el-icon-folder"></i>
        </span>
        {{ childTreeData.name }}
      </div>
      <div :class="selected ? 'selected' : ''" id="nodetitle2" v-if="isMD">
        <nuxt-link :to="'/id/' + childTreeData.index">
          <span>
            <i class="el-icon-document"></i>
          </span>
          {{ childTreeData.name }}</nuxt-link
        >
      </div>
    </div>
    <div :id="!isRoot ? 'nodechildren' : ''" v-if="!isMD" v-show="visible">
      <div v-for="(item, index) in childTreeData.file_nodes" :key="'a' + index">
        <!-- {{ item.name }} -->
        <TreeNode
          :childTreeData="item"
          :isMD="true"
          v-on:passChild="onPassChild"
        />
      </div>
      <div
        v-for="(item, index) in childTreeData.folder_nodes"
        :key="'b' + index"
      >
        <!-- {{ item.name }} -->
        <TreeNode
          :childTreeData="item"
          :isMD="false"
          v-on:passChild="onPassChild"
        />
      </div>
    </div>
  </div>
</template>

<script>
import TreeNode from './TreeNode.vue'
// import { GetArticleServlet } from 'src/network/articles'
export default {
  name: 'TreeNode',
  components: {
    TreeNode,
  },
  beforeCreate() {},
  created() {
    if (this.isMD) {
      this.$emit('passChild', '' + this.childTreeData.index)
      if ('' + this.childTreeData.index === this.$route.params.id1) {
        this.selected = true
      }
    }
  },

  watch: {
    $route(to, from) {
      if (this.isMD && '' + this.childTreeData.index === to.params.id1) {
        this.selected = true
        // this.$emit('selectChanged')
      } else if (this.isMD) {
        this.selected = false
      } else if (!this.isMD) {
        // console.log(this.children)
        if (this.children.includes(to.params.id1)) {
          this.selected = true
        } else {
          this.selected = false
        }
      }
    },
    children(to, from) {
      if (!this.selected) {
        if (this.children.includes(this.$route.params.id1)) {
          this.selected = true
        } else {
          this.visible = false
        }
      }
    },
  },
  data() {
    return {
      treeData: {},
      visible: true,
      selected: false,
      children: [],
    }
  },
  props: {
    isMD: {
      // 作用是请求的时候提供数据，还有整明为子集评论框
      type: Boolean,
      default() {
        return true
      },
    },
    childTreeData: {
      // 作用是请求的时候提供数据，还有整明为子集评论框
      type: Object,
      default() {
        return {}
      },
    },
    isRoot: {
      type: Boolean,
      default() {
        return false
      },
    },
  },
  mounted() {
    // this.visible = this.isRoot
  },
  methods: {
    onPassChild(childindex) {
      this.children.push(childindex)
      // this.selected = true
      this.$emit('passChild', childindex)
      // console.log(this.childTreeData.name)
      // console.log('selectChanged')
    },
    nodeClick(index) {
      this.switchVisibility()
      // print('click node ', index)
    },
    switchVisibility() {
      this.visible = !this.visible
    },
    // setTreeData(data) {
    //   this.treeData = data;
    // },
  },
}
</script>

<style scoped>
#nodetitle {
  padding: 10px;
  border-left: rgba(0, 122, 204, 0) solid 3px;
}
#nodetitle2 {
  border-left: rgba(0, 122, 204, 0) solid 3px;
}
#nodetitle2 a {
  padding: 10px;
  display: block;
  height: 100%;
}
#nodetitle2:hover {
  background: rgba(0, 122, 204, 0.4);
  border-left: rgb(0, 122, 204) solid 3px;
  /* color: rgb(149, 213, 255); */
}
#nodetitle:hover {
  background: rgba(0, 122, 204, 0.5);
  border-left: rgb(0, 122, 204) solid 3px;
}
#nodechildren {
  padding-left: 10px;
}
.selected#nodetitle2 {
  border-left: rgb(0, 122, 204) solid 3px;
}
.selected a {
  color: rgb(0, 122, 204);
}
.selected {
  color: rgb(0, 122, 204);
}
</style>

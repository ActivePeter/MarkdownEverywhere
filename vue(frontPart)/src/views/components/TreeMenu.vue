<template>
  <div id="sidebar1">
    <div
      class="labelBarFather"
      :key="index"
      v-for="item,index in treeData"
    >
      <div class="labelBar">
        <i v-if="!selectable" class="el-icon-arrow-up"></i> {{item.label}}
      </div>
      <TreeMenu
        class="childMenu"
        v-if="item.children&&item.children.length>0"
        :treeData=item.children
      />
      
    </div>
  </div>
  
</template>

<script>
  import NavMenu from '@/components/content/navmenu/NavMenu'
  import TreeMenu from '@/views/components/TreeMenu'
  export default {
    name: "TreeMenu",
    components:{
      TreeMenu
    },
    data(){
      return{
      }
    },
    methods:{
    },
    props:{
      treeData:{//作用是请求的时候提供数据，还有整明为子集评论框
        type:Array,
        default() {
          return [];
        }
      },
      folded:{//作用是请求的时候提供数据，还有整明为子集评论框
        type:Boolean,
        default() {
          return true;
        }
      },
      selectable:{//作用是请求的时候提供数据，还有整明为子集评论框
        type:Boolean,
        default() {
          return false;
        }
      },
    }
  }
</script>

<style scoped>
.labelBar{
  padding: 6px 10px;
  
}
.labelBar:hover{
  background: #EBEEF5;
}
.labelBarFather{
  width: max-content;
} 
.childMenu{
  padding-left: 10px;
}
</style>

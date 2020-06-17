<template>
  <div id="all">
    <div
      :key="index"
      v-for="item,index in treeData"
    >
      {{item.label}}
    </div>
  </div>
  
</template>

<script>
  import NavMenu from '@/components/content/navmenu/NavMenu'
  export default {
    name: "treeMenu",
    components:{
      
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
    }
  }
</script>

<style scoped>
.all{
  width: 200px;
}
</style>

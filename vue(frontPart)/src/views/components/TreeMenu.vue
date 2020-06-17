<template>
  <div class="sidebar1">
    <div v-if="label" class="labelBar" @click="onClick()">
        <span :class='selected?"selected":""'>
          <i v-if="!isMD" :class='folded?"el-icon-folder":"el-icon-folder-opened"'></i> 
          <i v-if="isMD" class='el-icon-document'></i>
        </span>
        
        {{label}}
    </div>
    <div
      v-show="!folded"
      class="labelBarFather"
      :key="index"
      v-for="item,index in childTreeData"
    >
      
      <TreeMenu
        :class='label?"childMenu":""'
        :label=item.label
        :childTreeData=item.children
        @onSelectChange="onSelectChange"
        @onCancelSelectPrevious="onCancelSelectPrevious"
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
        isMD:false,
        selected:false,
        previousNode:null,
        folded:false
      }
    },
    mounted(){
      if(this.label){
        this.folded=true
      }
      if(this.childTreeData.length==0){
        this.isMD=true;
      }else{
        this.isMD=false;
      }
    },
    methods:{
      select(state){
        this.selected=state;
        this.$emit("onSelectChange",this,state);
      },
      onCancelSelectPrevious(){
        if(!this.label){//根节点
          if(this.previousNode){
            this.previousNode.select(false)
          }
        }
        this.$emit("onCancelSelectPrevious");
      },
      onSelectChange(node,state){
        if(!this.label){//根节点
          this.previousNode=node
        }else{
         this.selected=state;
        }
        this.$emit("onSelectChange",node,state);
      },
      onClick(){
        if(this.isMD){
          this.onCancelSelectPrevious()
          this.select(true)
        }else{
          this.folded=!this.folded
        }
      }
    },
    props:{
      childTreeData:{//作用是请求的时候提供数据，还有整明为子集评论框
        type:Array,
        default() {
          return [];
        }
      },
      
      label:{//作用是请求的时候提供数据，还有整明为子集评论框
        type:String,
        default() {
          return null;
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
  
} 
.childMenu{
  padding-left: 10px;
}
.sidebar1{
  
}
.selected{
  color: #409EFF;
}
</style>

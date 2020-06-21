<template>
  <div class="sidebar1">
    <div v-if="label" class="labelBar" @click="onClick()">
        <span :class='selected?"selected":""'>
          <i v-if="!isMD" :class='(label&&folded)?"el-icon-folder":"el-icon-folder-opened"'></i> 
          <i v-if="isMD" class='el-icon-document'></i>
        </span>
        
        {{label}}
    </div>
    <div
      v-show="!(label&&folded)"
      class="labelBarFather"
      :key="index"
      v-for="item,index in childTreeData"
    >
      
      <TreeMenu
        :ref="'tmref'+index"
        :class='label?"childMenu":""'
        :label='item.filename'
        :index='index'
        :id='item.id?item.id:-1'
        :isMD='!item.children'
        :selectedPath='selectedPath'
        :childTreeData='item.children'
        :level='level+1'
        @onSelectChange="onSelectChange"
        @onDataLoad="onDataLoad"
        @onExpand="onExpand"
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
        selected:false,
        previousNode:null,
        folded:true,
        body:null,
      }
    },
    watch: {
      id(val) {
        if(this.$route.params.id==this.id){
          console.log("route has id",this.$route.params.id)
          this.select(true)
          this.onDataLoad(this)
          this.onExpand();
        }
      }
    },
    mounted(){
      if(this.$route.params.id==this.id){
          console.log("route has id",this.$route.params.id)
          this.select(true)
          this.onDataLoad(this)
          this.onExpand();
        }
    },
    methods:{
      setSelectFromRoot(id){
        // if(this.id==id){
        //   this.select(true)
        //   this.onExpand();
        //   this.body=body1;
        // }else{

        // }
        // if(this.level==path1.length){
        //   console.log(this.path,path1)
        //   this.select(true)
        //   this.onExpand();
        //   this.body=body1;
        //   return
        // }
        if(path1){
          console.log("tmref"+path1[this.level],this.$refs["tmref"+path1[this.level]]);
          this.$refs["tmref"+path1[this.level]][0].setSelectFromRoot(path1,body1);
        }
      },
      select(state){
        this.selected=state;
        this.$emit("onSelectChange",this,state);
      },
      onExpand(){
        this.$emit("onExpand")
        this.folded=false;
      },
      onDataLoad(node){
        this.$emit("onDataLoad",node);
      },
      onCancelSelectPrevious(){
        if(!this.label){//根节点
          if(this.previousNode){//从底开始
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
          if(!this.selected){
            this.onCancelSelectPrevious()
            this.select(true)
            this.onDataLoad(this)
            this.$router.push({ path: ""+this.id});
          }
          
        }else{
          this.folded=!this.folded
        }
      }
    },
    props:{
      childTreeData:{//作用是请求的时候提供数据，还有整明为子集评论框
        type:Array,
        default() {
          return null;
        }
      },
      
      label:{//作用是请求的时候提供数据，还有整明为子集评论框
        type:String,
        default() {
          return null;
        }
      },
      path:{//作用是请求的时候提供数据，还有整明为子集评论框
        type:String,
        default() {
          return "";
        }
      },
      index:{//作用是请求的时候提供数据，还有整明为子集评论框
        type:Number,
        default() {
          return 0;
        }
      },
      isMD:{//作用是请求的时候提供数据，还有整明为子集评论框
        type:Boolean,
        default() {
          return false;
        }
      },
      selectedPath:{//作用是请求的时候提供数据，还有整明为子集评论框
        type:String,
        default() {
          return "";
        }
      },
      level:{//作用是请求的时候提供数据，还有整明为子集评论框
        type:Number,
        default() {
          return 0;
        }
      },
      id:{//作用是请求的时候提供数据，还有整明为子集评论框
        type:Number,
        default() {
          return -1;
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

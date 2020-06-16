<template>
  <div id="all">
    <NavMenu
    :showSideBar="showSideBar"
    @onSideBarStateChange="onSideBarStateChange"
    />
    <div id="app">
      <transition name="fade">
      <div class="sidebar" v-show="showSideBar"
       
      >
      
      </div></transition>
      <div class="article">
        <mavon-editor
          class="md selectable"
          :value="body"
          :subfield = "false"
          :defaultOpen = "'preview'"
          :toolbarsFlag = "false"
          :editable="false"
          :scrollStyle="false"
          :ishljs = "true"
        ></mavon-editor>
      </div>
      
      
    </div>
  </div>
  
</template>

<script>
  import NavMenu from '@/components/content/navmenu/NavMenu'
  import { mavonEditor } from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'
  export default {
    name: "App",
    components:{
      NavMenu,
      mavonEditor
    },
    data(){
      return{
        showSideBar:true,
        data: [{
          label: '一级 1',
          children: [{
            label: '二级 1-1',
            children: [{
              label: '三级 1-1-1'
            }]
          }]
        }, {
          label: '一级 2',
          children: [{
            label: '二级 2-1',
            children: [{
              label: '三级 2-1-1'
            }]
          }, {
            label: '二级 2-2',
            children: [{
              label: '三级 2-2-1'
            }]
          }]
        }, {
          label: '一级 3',
          children: [{
            label: '二级 3-1',
            children: [{
              label: '三级 3-1-1'
            }]
          }, {
            label: '二级 3-2',
            children: [{
              label: '三级 3-2-1'
            }]
          }]
        }],
      }
    },
    methods:{
      onSideBarStateChange(){
        this.showSideBar=!this.showSideBar
        console.log("onSideBarStateChange")
      }
    }
  }
</script>

<style scoped>
  @import "assets/css/base.css";
  #app{
    
    width: 100%;
    height: 100%;
    display: flex;
  }
  .sidebar{
    
    background:green;
    width: 100px;
  }
  .article{
    padding-top: 60px;
    min-height:100vh;
    width: 100%;
  }
  .fade-enter-active, .fade-leave-active {
      transition: width .3s
  }
  .fade-enter, .fade-leave-active {
        width: 0
  }
</style>

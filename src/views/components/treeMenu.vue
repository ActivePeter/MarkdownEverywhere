<template>
  <div id="all">
    <NavMenu
    :showSideBar="showSideBar"
    @onSideBarStateChange="onSideBarStateChange"
    />
    <div id="app">
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
    margin-top: 60px;
    width: 100%;
    height: 100%;
  }
</style>

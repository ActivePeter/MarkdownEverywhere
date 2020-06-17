<template>
  <div class="login">
    <el-dialog title="添加标签" :visible.sync="dialogVisible" width='500px'
      :close-on-click-modal='false' @close='onClose()' id="logindialog">
        
          
          <el-tabs tab-position="left" style="height: 200px;">
            <el-tab-pane 
              :key="cat.id"
              v-for="cat in categories"
              :label="cat.name">
              <el-tag
                :key="tag.id"
                v-for="tag in cat.tag_chosen"
                size="mini" 
                closable
                :disable-transitions="false"
                @close="unchooseTag(cat,tag)">
                {{tag.name}}
              </el-tag>
              <el-divider content-position="left">选择合适的标签</el-divider>
              <div>
                <el-tag
                  class="hoverShadow"
                  :key="tag.id"
                  v-for="tag in cat.tag_unchosen"
                  type="info"
                  size="mini" 
                  :disable-transitions="false"
                  @click="chooseTag(cat,tag)">
                  {{tag.name}}
                </el-tag>
              </div>
            </el-tab-pane>
          </el-tabs>

    </el-dialog>
  </div>
  

</template>

<script>
  import HorizonSpace from '@/components/common/HorizonSpace'
  import {CreateArticle,getCategories} from "@/network/articles";
  //import {checkUsername, checkPassword, checkCode} from '@/utils/validate'

  export default {
    name: "login",
    components: {
        HorizonSpace,
    },
    created: function () {
      
    },
    methods:{
      setTags(tags_info){
        for(var key in tags_info){//遍历tag
          for(var cat of this.categories){//遍历json对象的每个key/value对,p为key
            if(cat.tag_unchosen.hasOwnProperty(tags_info[key].id)){//当前tagid已经找到分类
              cat.tag_chosen[tags_info[key].id]=tags_info[key];//选中当前tag
              delete cat.tag_unchosen[tags_info[key].id]
              break;
            }
          }
        }
        this.$forceUpdate();
      },
      initCategories(){
        return getCategories().then(res => {
          // console.log("getCats:");
          // console.log(res);
          switch(res.status){
            case 200://成功读取分类后的tag列表
              this.categories=res.data;
              for(var cat of this.categories) {
                  // //do something
                  
                  cat.tag_chosen={};
                  cat.tag_unchosen={};
                  for(var tag in cat.tag_info) {
                    cat.tag_unchosen[cat.tag_info[tag].id]=cat.tag_info[tag];
                  };
                  
              };
              this.$emit('onTagsLoaded')
                return 1;
              break;
            default:
                return null;
              break;
          }
          
        })
      },
      getChosenTags(){//上传文章时用于获取已选中tags的id
        var tags=[]
        for(var cat of this.categories) {
            // //do something
            // console.log("cat.tag_info:");
            // console.log(cat.tag_info);
            for(var tag in cat.tag_chosen) {
              // console.log("tag:");
              // console.log(cat.tag_chosen[tag]);
              tags.push(cat.tag_chosen[tag].id)
            };
        };
        return tags;
      },
      chooseTagWithoutCat(tags){
        // console.log("yag:");
        // console.log(tag.id);
        // console.log("cat:");
        // console.log(cat);
        for(var cat of this.categories){
          for(var key in cat.tag_info) {
              // console.log("tag:");
              // console.log(cat.tag_chosen[tag]);
            var id=cat.tag_info[key].id
            // console.log(tags+"tag:indexOf"+id);
            //   console.log(tags.indexOf(""+id));
            if(tags.indexOf(""+id)>=0){
              cat.tag_chosen[id]=cat.tag_info[key];
              delete cat.tag_unchosen[id]
            }else{
              cat.tag_unchosen[id]=cat.tag_info[key];
              delete cat.tag_chosen[id]
            }
          };
        }
       
      },
      chooseTag(cat,tag){
        // console.log("yag:");
        // console.log(tag.id);
        // console.log("cat:");
        // console.log(cat);

        cat.tag_chosen[tag.id]=tag;
        delete cat.tag_unchosen[tag.id]
        this.$forceUpdate()
      },
      unchooseTag(cat,tag){
        cat.tag_unchosen[tag.id]=tag;
        delete cat.tag_chosen[tag.id]
        this.$forceUpdate()
      },
      onClose(){//关闭dialogue时清楚文本框
        this.$emit('updateChosenTags',this.getChosenTags(),true)//传递tags 给父组件write
      },
    },
    data(){
      return {
        categories: [],
        dialogVisible:false,
        value: ''
      };
    }
  }
</script>

<style scoped>
  .middle{
    margin:0 auto !important;
    text-align: center;
  }
  .btn{
    width: 100%;
  }
  .discribe{
    padding: 6px;
  }

  .el-tag {
    margin: 5px;
  }
  .el-tag:hover {
    
  }
</style>

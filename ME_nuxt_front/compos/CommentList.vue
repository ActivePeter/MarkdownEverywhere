<template>
  <div id="list">
    <div v-show="showAdd">
      <Card v-for="(value, index) in comments" :key="index">
        <!-- <div>{{ value.Name }}</div>
        <div>{{ value.Time }}</div> -->
        <CommentBar
          :name="value.Name"
          :time="value.Time"
          :content="value.Content"
          :contact="value.Contact"
        />
      </Card>
      <div id="loadmore" @click="loadComment">加载更多</div>
      <!-- <TreeNode /> -->
      <!-- <TreeNode :childTreeData="treeData" />
     -->
      <!-- <div :key="index" v-for="(item, index) in treeData.file_nodes">
      {{ item.name }}
    </div> -->
      <div id="showcomment" class="grad_trans" @click="openComment">
        <i class="el-icon-plus"> </i>
      </div>
    </div>
    <div v-show="!showAdd">
      <div id="bigtitle"><h3>新增评论</h3></div>

      <Card>
        <div class="formcard">
          <!-- <div class="formbar"> -->
          <div class="formtitle">称呼</div>
          <input
            class="input"
            type="text"
            v-model="form.name"
            placeholder="edit me"
          />
        </div>
      </Card>
      <Card>
        <div class="formcard">
          <!-- <div class="formbar"> -->
          <div class="formtitle">联系方式</div>
          <input
            class="input"
            type="text"
            v-model="form.contact"
            placeholder="edit me"
          />
        </div>
      </Card>
      <Card>
        <div class="formcard">
          <!-- <div class="formbar"> -->
          <div class="formtitle">邮箱（选填）</div>
          <input
            class="input"
            type="text"
            v-model="form.email"
            placeholder="edit me"
          />
        </div>
      </Card>
      <Card>
        <div class="formcard">
          <!-- <div class="formbar"> -->
          <div class="formtitle">内容</div>
          <DivEditable @input="AreaChange" ref="divEdit" />
          <!-- <div
            class="textarea"
            contenteditable="true"
            v-html="form.content"
            @input="form.content = $event.target.innerHTML"
            @focus="isChange = false"
            @blur="isChange = true"
            ref="textArea"
          ></div> -->
        </div>
      </Card>
      <h3 class="button grad_trans" @click="addComment">提交</h3>
      <h5 class="cancelbutton grad_trans" @click="cancelAdd">取消</h5>
      <!-- </div> -->
    </div>
  </div>
</template>

<script>
import calcTextareaHeight from '@/assets/js/calcTextareaHeight'
import $ from 'jquery'
import Card from './Card'
import CommentBar from './CommentBar'
import DivEditable from './DivEditable'
export default {
  name: 'CommentList',
  components: {
    Card,
    CommentBar,
    DivEditable,
  },
  data() {
    return {
      showAdd: true,
      comments: [
        {
          ChildCnt: 0,
          Children: '',
          Contact: 'asdasd',
          Content: 'sss',
          Depth: 0,
          Email: 'asdasd',
          FatherId: 0,
          HasFather: false,
          ID: 1,
          Name: 'asdad',
          Time: '2021-5-8 4:19\n',
        },
      ],
      msg: '',
      form: {
        // body: 'sssssss',

        name: '',
        content: '',
        contact: '',
        email: '',
      },
      formBodyHeight: 30,
      loadingmore: false,
    }
  },
  watch: {
    'form.body'() {
      //   $('.formcard .textarea').style.height = $(
      //     '.formcard .textarea'
      //   ).scrollHeight
      //   $('.formcard .textarea').css('height':'')
      console.log($('.formcard textarea'))
      this.formBodyHeight = calcTextareaHeight($('.formcard textarea')[0])
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
    this.updateLatest()
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
        return {}
      },
    },
  },
  methods: {
    AreaChange(val) {
      console.log(val)
      this.form.content = val
    },
    updateLatest() {
      this.$api.getCommentsPageLatest().then((res) => {
        console.log(res)
        if (!res.data.Haserr) {
          this.comments = res.data.Data
        } else {
          this.$message.error('获取最新评论失败')
        }
      })
    },
    addComment() {
      if (this.form.name === '') {
        this.$message.error('未填写称呼')
        return
      }
      if (this.form.contact === '') {
        this.$message.error('未填写联系方式')
        return
      }
      if (this.form.content === '') {
        this.$message.error('未填写内容')
        return
      }
      Promise.all([
        this.$api.addCommentRoot(
          this.form.name,
          this.form.content,
          this.form.contact,
          this.form.email
        ),
      ]).then((res) => {
        console.log(res)
        if (res[0].data.succed) {
          this.showAdd = true
          this.closeAddUI()
          this.updateLatest()
        } else {
          this.$message.error('新增评论请求失败')
        }
        // this.showAdd = true
        // this.$api.getCommentsPageLatest().then((res1) => {
        //   console.log(res1)
        // })
      })
      // const rec = await Promise.all([app.$api.getList()])
    },
    closeAddUI() {
      this.showAdd = true
      this.form = {
        // body: 'sssssss',

        name: '',
        content: '',
        contact: '',
        email: '',
      }
      this.$refs.divEdit.clear()
    },
    cancelAdd() {
      this.closeAddUI()
    },
    openComment() {
      this.showAdd = false
    },
    loadComment() {
      if (!this.loadingmore) {
        this.loadingmore = true
        let id = 0
        if (this.comments.length > 0) {
          id = this.comments[this.comments.length - 1].ID
        }
        this.$api.getCommentsPageNext(id).then((res) => {
          console.log('res', res)
          if (res.data.Data && !res.data.Haserr) {
            if (res.data.Data.length === 0) {
              this.$message('没有更多评论了')
            } else {
              this.comments.push(...res.data.Data)
            }
          } else {
            this.$message.error('加载失败')
          }
          this.loadingmore = false
        })
      }
      // this.comments.push({
      //   author: 'aaa',
      //   contact: 'qq 123',
      //   content: 'asadasdad',
      // })
      // //   this.$nextTick(function () {
      // // const div = document.getElementById('list')
      // this.$nextTick(() => {
      //   const div = document.getElementById('list')
      //   $('#list').animate({ scrollTop: div.scrollHeight }, 300)
      //   // console.log($('#list').height())
      // })
      // const dis=scrollHeight-scrollTop
      // let scrollTopc=scrollTop
      // if(distance>0){
      //     scrollTopc++
      //     if(scrollTopc>){
      //         div
      //     }
      // }
      // div.scrollIntoView({
      //   behavior: 'smooth',
      //   block: 'end',
      //   inline: 'nearest',
      // })
      // div.scrollTop = div.scrollHeight
      //   })
    },
    // sleep(delay) {
    // },
    // setTreeData(data) {
    //   this.treeData = data;
    // },
  },
}
</script>
<style scoped>
#list {
  position: absolute;
  top: 0;
  right: 0;
  width: 100%;
  height: 100%;

  overflow-y: scroll;
}
#loadmore {
  width: calc(100% - 20px);
  margin: 10px;
  height: 60px;
  line-height: 60px;
  text-align: center;
  cursor: pointer;
}
#loadmore:hover {
  color: rgb(0, 122, 204);
}
#showcomment {
  width: 70px;
  height: 70px;

  color: rgb(0, 122, 204);
  position: fixed;
  bottom: 80px;
  right: 80px;
  border-radius: 50%;
  font-size: 30px;
  display: flex;
  align-items: center;
  text-align: center;
  cursor: pointer;
  z-index: 0;
  background-color: rgb(37, 37, 38);
  border: 1px solid rgb(0, 122, 204);
}
#showcomment:hover {
  color: rgb(37, 37, 38);
  background-color: rgb(0, 122, 204);
  border: 1px solid rgb(0, 122, 204);
}
#showcomment i {
  width: 100%;
  /* position: absolute;
  margin: auto;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0; */
}
.formtitle {
  width: 100%;
  margin-bottom: 10px;
}
.formcard {
  border-left: 3px solid rgba(0, 122, 204, 255);
  padding: 10px;
}
.formcard:hover {
  /* border-left: 3px solid rgb(0, 122, 204); */
  padding: 10px;
}
input {
  width: calc(100% - 6px);
  /* margin-right: 10px; */
  height: 40px;
  border: 1px solid rgb(37, 37, 38);
  outline: none;
  font-size: 18px;
  text-indent: 14px;
  color: rgba(255, 255, 255, 0.623);
  background: rgb(37, 37, 38);
  margin: -5px;
}

.textarea div {
  margin-top: 10px;
}
#bigtitle {
  /* border-left: 3px solid rgb(0, 122, 204); */
  padding: 15px;
  padding-bottom: 0;
}
.button {
  margin: 20px;
  cursor: pointer;
}
.button:hover {
  /* margin: 15px; */
  color: rgb(0, 122, 204);
}
.cancelbutton {
  margin: 20px;
  cursor: pointer;
  color: rgba(255, 255, 255, 0.623);
}
.cancelbutton:hover {
  /* margin: 15px; */
  color: rgb(0, 122, 204);
}
</style>

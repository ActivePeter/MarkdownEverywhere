<template>
  <div>
    <!-- ssss
    {{ root.file_nodes[0].name }}
    <div v-for="(item, index) in root.file_nodes" :key="index">
      ssss
      {{ item.name }}
      {{ hhh }}
    </div> -->

    <drawer
      id="mydrawer"
      :show="drawer"
      pos="right"
      tran="overlay"
      :drstyle="{ background: 'rgb(30, 30, 30)' }"
      @change-show="changeDrawerShow"
      @on-hide="onHide"
      @on-show="onShow"
    >
      <div id="box" slot="body">
        <div id="right" :style="{ width: sideWidth + 'px' }">
          <TreeMenu :treeRoot="root" />
        </div>
        <div id="resize"></div>
        <div id="left">
          <!-- {{ articleBody }} -->
          <div class="background">
            <img :src="imgSrc" width="100%" height="100%" alt="" />
          </div>
          <nuxt-child></nuxt-child>
          <!-- <markdown-it-vue class="md-body" :content="content" /> -->
          <!-- <vue-markdown>{{ articleBody }}</vue-markdown> -->
          <!-- <mavonEditor
            :subfield="false"
            :autofocus="false"
            v-model="content"
            ref="md"
            style="width: 99%"
          /> -->
        </div>
        <div id="showcomment" class="grad_trans" @click="switchCommentShow">
          <i class="el-icon-chat-dot-round"> </i>
        </div>
      </div>
      <CommentList slot="side" />
    </drawer>
  </div>
</template>

<script>
import TreeMenu from '@/compos/TreeMenu'
import drawer from '../compos/drawer.vue'
import CommentList from '../compos/CommentList'
// import VueMarkdown from 'vue-markdown'
// import member from '@/components/index/member/index'
// import domain from '@/components/index/domain'
// import carousel from '~/components/index/carousel/index'
// import message from '~/components/index/message'
// import md from 'markdown-it'
// import MarkdownItVue from 'markdown-it-vue'
// import 'markdown-it-vue/dist/markdown-it-vue.css'
// import { mavonEditor } from 'mavon-editor'
// import 'mavon-editor/dist/css/index.css'
export default {
  name: 'Index',
  components: {
    TreeMenu,
    drawer,
    CommentList,
    // mavonEditor,
    // MarkdownItVue,
    // 'vue-markdown': VueMarkdown,
    // carousel,
    // introduction,
    // member,
    // domain,
    // message,
  },
  data() {
    return {
      sideWidth: 200,
      root: { file_nodes: [{ name: 'ss' }] },
      articleBody: '',
      content: '# your markdown content',
      imgSrc: require('@/assets/img/bg.png'),
      drawer: false,
    }
  },
  methods: {
    onHide() {
      console.log('hide')
    },
    changeDrawerShow(state) {
      this.drawer = state
      console.log('drawer was changed from components')
    },
    onShow() {
      console.log('show')
    },
    switchCommentShow() {
      this.drawer = !this.drawer
    },
    dragControllerDiv() {
      // 保留this引用
      const that = this
      const resize = document.getElementById('resize')
      resize.onmousedown = function (e) {
        // 颜色改变提醒
        resize.style.background = '#818181'
        let startX = e.clientX
        resize.left = resize.offsetLeft
        document.onmousemove = function (e) {
          // 计算并应用位移量
          const endX = e.clientX
          const moveLen = endX - startX
          startX = endX
          that.sideWidth += moveLen
        }
        document.onmouseup = function () {
          // 颜色恢复
          resize.style.background = ''
          document.onmousemove = null
          document.onmouseup = null
        }
        return false
      }
    },
  },
  mounted() {
    this.dragControllerDiv()
  },
  async asyncData({ app, redirect, route }) {
    if (route.params.id1 == null) {
      // console.log(route)
      redirect('/id/1')
    } else {
      try {
        const rec = await Promise.all([app.$api.getList()])
        // console.log(rec[0].data)
        // console.log(md)
        // const result1 = $md.render('# your markdown content')
        // console.log(result1)
        // const result1 = '' // md.render(rec[0].data.article)
        return {
          root: rec[0].data.tree,
          // articleBody: rec[0].data.article,
        }
      } catch (e) {
        return {}
      }
    }
  },
}
</script>

<style scoped>
#box {
  width: 100%;
  height: calc(100vh);
  position: relative;
  overflow: hidden;
  display: flex;
}
#left {
  height: 100%;
  /* background: #f3f3f3; */
  flex: 1;
  overflow-y: scroll;
  /* padding: 30px; */
}

#resize {
  width: 5px;
  height: 100%;
  cursor: w-resize;
}

#right {
  height: 100%;
  /* background: #ffffff; */
  overflow: scroll;
}
#article {
  /* margin-bottom: 80px; */
}
.background {
  width: 100%;
  height: 100%; /**宽高100%是为了图片铺满屏幕 */
  z-index: -1;
  position: absolute;
}
.background img {
  object-fit: cover;
  opacity: 0.3;
}
#showcomment {
  width: 70px;
  height: 70px;
  background-color: rgb(30, 30, 30);
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
</style>

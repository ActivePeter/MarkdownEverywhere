<template>
  <div>
    <!-- ssss
    {{ root.file_nodes[0].name }}
    <div v-for="(item, index) in root.file_nodes" :key="index">
      ssss
      {{ item.name }}
      {{ hhh }}
    </div> -->
    <!-- <div id="box"> -->
    <!-- <div id="right" :style="{ width: sideWidth + 'px' }">
        <TreeMenu :treeRoot="root" />
      </div>
      <div id="resize"></div>
      <div id="left">主界面</div>
      <p></p>
    </div> -->
    <div id="article">
      <h1>{{ title }}</h1>
      <div class="markdown-body" v-html="$md.render(articleBody)"></div>
    </div>
    <div id="bottom">
      © 2021
      <a href="https://hanbaoaaa.xyz/">hanbaoaaa record</a>.浙ICP备20005263号
    </div>
  </div>
</template>

<script>
// import TreeMenu from '@/compos/TreeMenu'
// import member from '@/components/index/member/index'
// import domain from '@/components/index/domain'
// import carousel from '~/components/index/carousel/index'
// import message from '~/components/index/message'
export default {
  name: "Index",
  components: {
    // TreeMenu,
    // carousel,
    // introduction,
    // member,
    // domain,
    // message,
  },
  head() {
    return {
      title: "hanbaoaaa 的知识库--" + this.title,
      meta: [
        {
          hid: "description",
          name: "description",
          content:
            "hanbaoaaa, 是程序员，喜欢捣鼓各方面的东西。" +
            this.articleBody.substring(
              0,
              this.articleBody.length > 50 ? 50 : this.articleBody.length
            ),
        },
        {
          hid: "viewport",
          name: "viewport",
          content: "width=device-width, initial-scale=1.0",
        },
      ],
    };
  },
  data() {
    return {
      sideWidth: 200,
      root: { file_nodes: [{ name: "ss" }] },
    };
  },
  methods: {
    dragControllerDiv() {
      // 保留this引用
      const that = this;
      const resize = document.getElementById("resize");
      resize.onmousedown = function (e) {
        // 颜色改变提醒
        resize.style.background = "#818181";
        let startX = e.clientX;
        resize.left = resize.offsetLeft;
        document.onmousemove = function (e) {
          // 计算并应用位移量
          const endX = e.clientX;
          const moveLen = endX - startX;
          startX = endX;
          that.sideWidth += moveLen;
        };
        document.onmouseup = function () {
          // 颜色恢复
          resize.style.background = "";
          document.onmousemove = null;
          document.onmouseup = null;
        };
        return false;
      };
    },
  },
  mounted() {},
  // async asyncData({ app }) {
  //   try {
  //     const rec = await Promise.all([app.$api.getArticle()])
  //     console.log(rec[0].data)
  //     return {
  //       root: rec[0].data.tree,
  //       hhh: 'ssss',
  //     }
  //   } catch (e) {
  //     return {}
  //   }
  // },
  async asyncData({ app, route }) {
    try {
      const rec = await Promise.all([app.$api.getArticle(route.params.id1)]);
      // console.log(rec[0].data)
      // console.log(md)
      // const result1 = $md.render('# your markdown content')
      // console.log(result1)
      // const result1 = '' // md.render(rec[0].data.article)
      return {
        // root: rec[0].data.tree,
        articleBody: rec[0].data.article,
        title: rec[0].data.title.slice(0, rec[0].data.title.length - 3),
      };
    } catch (e) {
      return {};
    }
  },
};
</script>

<style scoped>
#article {
  padding: 30px;
}
#bottom {
  margin-left: 30px;
  margin-bottom: 50px;
}
</style>

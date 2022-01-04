export default {
  // Global page headers (https://go.nuxtjs.dev/config-head)
  // env: {
  //   baseUrl: '/api',
  // },
  server: {
    port: 3000,
    host: '0.0.0.0',
  },

  head: {
    title: 'hanbaoaaa 的知识库',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: '' },
    ],
    link: [{ rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }],
  },

  // Global CSS (https://go.nuxtjs.dev/config-css)
  css: [
    '~/assets/css/main.css',
    '~/assets/css/normalize.css',
    '~/assets/css/darkdown.css',
    'element-ui/lib/theme-chalk/index.css',
    '~/node_modules/highlight.js/styles/hopscotch.css',
  ],

  // Plugins to run before rendering page (https://go.nuxtjs.dev/config-plugins)
  plugins: [
    {
      src: '@/plugins/element-ui',
      ssr: true,
    },
    {
      src: '~/plugins/axios',
      ssr: true,
    },
    '~/plugins/request',
    '~/plugins/api',
    '~/plugins/filter',
    {
      src: '~/plugins/fast-amap',
      ssr: false,
    },
  ],

  // Auto import components (https://go.nuxtjs.dev/config-components)
  components: true,

  // Modules for dev and build (recommended) (https://go.nuxtjs.dev/config-modules)
  buildModules: [
    // https://go.nuxtjs.dev/eslint
    // '@nuxtjs/eslint-module',
  ],

  // Modules (https://go.nuxtjs.dev/config-modules)
  modules: [
    // https://go.nuxtjs.dev/axios
    '@nuxtjs/axios',
    '@nuxtjs/markdownit',
  ],

  axios: {
    proxy: true, // 表示开启代理
    prefix: '/api', // 表示给请求url加个前缀 /api
    credentials: true, // 表示跨域请求时是否需要使用凭证
  },
  // [optional] markdownit options
  // See https://github.com/markdown-it/markdown-it
  markdownit: {
    preset: 'default',
    linkify: true,
    breaks: true,
    use: [
      'markdown-it-div',
      'markdown-it-attrs',
      'markdown-it-highlightjs',
      'markdown-it-task-lists',
    ],
    runtime: true,
    injected: true,
  },
  proxy: {
    '/api': {
      // target: 'http://localhost:659', // 本地测试目标接口域名
      target: 'http://hanbaoaaa.xyz/backend', // 部署时目标接口域名
      changeOrigin: true, // 表示是否跨域
      pathRewrite: {
        '^/api': '', // 把 /api 替换成 /
      },
    },
  },

  // Build Configuration (https://go.nuxtjs.dev/config-build)
  build: {
    transpile: [/^element-ui/],
  },
}

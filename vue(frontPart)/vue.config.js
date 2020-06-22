module.exports = {
  publicPath: "/markdownEverywhere",
  configureWebpack: {
    resolve: {
      alias: {
        'assets': '@/assets',
        'components': '@/components',
        'network': '@/network',
        'views': '@/views',
        'utils':'@/utils',
      }
    }
    
  }
    
}

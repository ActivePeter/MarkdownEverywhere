export default function ({ app: { $axios } }) {
  // 基本配置
  // $axios.defaults.baseURL = process.env.baseUrl
  $axios.defaults.timeout = 10000

  // 请求拦截
  $axios.onRequest((config) => {
    return config
  })

  // 响应拦截
  $axios.onResponse((res) => {
    if (res.data.code === 500) {
      this.$message({
        message: res.data.data,
        type: 'warning',
      })
    }
    return res
  })

  // 错误处理
  $axios.onError((error) => {
    return Promise.reject(error)
  })
}

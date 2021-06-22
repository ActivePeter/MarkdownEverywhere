export default ({ app: { $axios } }, inject) => {
  const requestList = {}
  const methods = ['get', 'post', 'put', 'delete']
  methods.forEach((method) => {
    const dataKey = method === 'get' ? 'params' : 'data'
    requestList[method] = function (url, data) {
      return $axios({
        method,
        url,
        [dataKey]: data,
      }).catch((err) => {
        return {
          err_no: 1,
          data: {},
          errors: [err],
        }
      })
    }
  })
  inject('request', requestList)
}

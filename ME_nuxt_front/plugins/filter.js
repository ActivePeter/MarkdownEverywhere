import Vue from 'vue'
// 现在列表内容字数
export function limitContentNumber(content) {
  return content.substring(0, 500)
}
export function formatDate(timestamp = +new Date()) {
  if (timestamp) {
    const time = new Date(timestamp * 1000)
    const y = time.getFullYear() // getFullYear方法以四位数字返回年份
    const M = time.getMonth() + 1 // getMonth方法从 Date 对象返回月份 (0 ~ 11)，返回结果需要手动加一
    const d = time.getDate() // getDate方法从 Date 对象返回一个月中的某一天 (1 ~ 31)
    // const h = time.getHours() // getHours方法返回 Date 对象的小时 (0 ~ 23)
    // const m = time.getMinutes() // getMinutes方法返回 Date 对象的分钟 (0 ~ 59)
    // const s = time.getSeconds() // getSeconds方法返回 Date 对象的秒数 (0 ~ 59)
    // return y + '年' + M + '月' + d + '日 ' + h + ':' + m + ':' + s
    return y + '年' + M + '月' + d + '日 '
  } else {
    return ''
  }
}
const filters = {
  limitContentNumber,
  formatDate,
}

Object.keys(filters).forEach((key) => {
  Vue.filter(key, filters[key])
})
export default filters

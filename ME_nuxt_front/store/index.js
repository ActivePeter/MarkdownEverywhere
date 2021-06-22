// 主模块
// state
export const state = () => ({
  activeName: '',
})

// mutations
export const mutations = {
  // M_UPDATA_NAV(state, payload) {
  //   state.bNav = payload
  // },
  // M_UPDATE_LOADING(state, payload) {
  //   state.bLoading = payload
  // },
  M_UPDATE_ACTIVENAME(state, payload) {
    state.activeName = payload
  },
}

// actions
export const actions = {
  // nuxtServerInit(store, { app: { $cookies } }) {
  // console.log('nuxtServerInit')
  // // console.log($cookies.get('user'));
  // const user = $cookies.get('user')
  //   ? $cookies.get('user')
  //   : { err: 2, msg: '未登录', token: '' }
  // // console.log(user);
  // store.commit('user/M_UPDATE_USER', user)
  // console.log(store.state.user.token)
  // console.log('1111')
  // },
}

// getters
export const getters = {
  // getNav(state) {
  //   return state.bNav ? '显示' : '隐藏'
  // },
}

import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userinfo:localStorage.getItem('userInfo') ? JSON.parse(localStorage.getItem('userInfo')):null,
    back:false
  },
  mutations: {
    setUserinfo(state, value) {
      state.userinfo = value;
      localStorage.setItem('userInfo', JSON.stringify(value));
      console.log("setUserinfo");
      console.log(state.userinfo);
    },
    setNeedBack(state, value) {
      state.back=value;
    }
  },
  actions: {
  },
  modules: {
  }
})

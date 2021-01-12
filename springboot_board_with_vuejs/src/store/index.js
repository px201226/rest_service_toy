import Vue from 'vue'
import Vuex from 'vuex'
import post from "./post"
import user from "./user"
import like from "./like"
import comment from "./comment"
import matching from "./matching"
import common from "./common"

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
	  post, user, common, like, comment,matching
  }
})

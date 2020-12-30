import {getSessionUser, getTestUser,logout } from "../api/user_api";
import router from "../router";

// state
const state = {
  user:"",
  login:false
};

// getters
const getters = {
 
 GET_USER(state){
    return state.user;
  },

  GET_USER_NAME(state){
    return state.user.name;
  },
  GET_IS_LOGIN(state){
    return state.user !== ""
  }
};

// mutations
const mutations = {
  SET_USER(state,user){
    state.user = user;
  },
  SET_LOGOUT(state){
    state.user=""
  }
};

// actions
const actions = {
  async QUERY_LOGOUT(context) {
    try {
      const response = await logout();
      context.commit("SET_LOGOUT");
      alert("로그아웃 되었습니다.");
      return response.data;
    } catch (e) {
      return Promise.reject(e);
    }
  },

  async QUERY_TEST_USER(context) {
    try {
      const response = await getTestUser();
      context.commit("SET_USER", response.data);
      alert("로그인 되었습니다.");
      return response.data;
    } catch (e) {
      return Promise.reject(e);
    }
  },
  async QUERY_GET_USER(context) {
    try {
      const response = await getSessionUser();
      context.commit("SET_USER", response.data);
      console.log(response.data);
      //alert(response.data);
      return response.data;
    } catch (e) {
      return Promise.reject(e);
    }
  },

};

export default {
  state,
  mutations,
  actions,
  getters,
};

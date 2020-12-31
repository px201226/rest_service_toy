import {getSessionUser, getTestUser,logout, join } from "../api/user_api";
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
  async QUERY_JOIN(context, req) {
    try {
      const response = await join(req);
      context.commit("SET_USER", response.data);
      context.commit('OPEN_SNACKBAR', setSnackBarInfo('제목과 종료일자를 작성해주세요.', 'error', 'top'))
      return response.data;
    } catch (e) {
      context.commit('OPEN_MODAL', {title: '회원가입에 실패', content: e.response.data.data.message, option1: '닫기',});
      return Promise.reject(e);
    }
  },
};

function setSnackBarInfo(text, color, location) {
  return {
      text: text,
      color: color,
      location: location,
  }
}

export default {
  state,
  mutations,
  actions,
  getters,
};

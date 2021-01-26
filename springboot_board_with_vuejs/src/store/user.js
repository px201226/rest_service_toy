import store from '.';
import {logout, getOAuthToken, join, getUser, getPostList, getCommentList } from "../api/user_api";
import {setTokenInLocalStorage,deleteToken} from "./token"
import {setSnackBarInfo} from "../api/common_api"
import router from "../router";

// state
const state = {
  token: localStorage.getItem('access_token'),
  user : "",
  login:false
};

// getters
const getters = {
 
  GET_IS_LOGIN(state) {
    return !!state.token;
  },
  GET_TOKEN(state){
    return state.token;
  },

  GET_USER(state){
    return state.user
  }

  
};

// mutations
const mutations = {
  SET_USER(state,user){
    state.user = user;
  },
  SET_LOGOUT(state){
    state.user=""
  },
  SET_TOKEN(state,token){
    state.token = token;
  },
  LOGOUT(state) {
    deleteToken();
    state.user = "";
    state.token = "";
    this.commit('OPEN_SNACKBAR', setSnackBarInfo('로그아웃 완료', 'success', 'top'));
    router.push("/");
  },
};

// actions
const actions = {
  
  // 회원가입
  async QUERY_JOIN(context, req) {
    try {
      context.commit('START_LOADING')
      const response = await join(req);
      router.push("/login")
      context.commit('OPEN_SNACKBAR', setSnackBarInfo('회원가입이 완료되었습니다.', 'success', 'top'))
    } catch (e) {
      context.commit('OPEN_MODAL', {title: '에러', content: e.response.data.message, option1: '닫기',});
    } 
  },

  // 로그인
  async QUERY_LOGIN(context, req) {
    try {
      context.commit('START_LOADING')
      const response = await getOAuthToken(req);

      //JWT 토큰 설정
      setTokenInLocalStorage(response.data);
      context.commit('SET_TOKEN',response.data );
  
      // USER PROFILE 설정
      const response1 = await getUser();
      context.commit('SET_USER',response1.data)
      
      // Matching 참가 여부 확인
      if(response1.data.lastMatchingDate)
      context.commit("SET_IS_APPLIED", true);

      return response.jwtToken;
    } catch (e) {
      console.log(e);
      context.commit('OPEN_MODAL', {title: '에러', content: e.response.data.message, option1: '닫기',});
    }
  },
  // 회원가입
  async QUERY_GET_USER_POST_LIST(context) {
    try {
      context.commit('START_LOADING')
      const response = await getPostList();
      return response.data.content;
    } catch (e) {
      context.commit('OPEN_MODAL', {title: '에러', content: e.response.data.message, option1: '닫기',});
    } 
  },

  // 회원가입
  async QUERY_GET_USER_COMMENT_LIST(context, req) {
    try {
      context.commit('START_LOADING')
      const response = await getCommentList();
      return response.data.content;;
    } catch (e) {
      context.commit('OPEN_MODAL', {title: '에러', content: e.response.data.message, option1: '닫기',});
    } 
  },
};


export default {
  state,
  mutations,
  actions,
  getters,
};

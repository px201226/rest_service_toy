import store from '.';
import {logout, login, join, getUser } from "../api/user_api";
import {setTokenInLocalStorage,setSnackBarInfo} from "./token"
import router from "../router";

// state
const state = {
  jwtToken:"",
  user : "",
  login:false
};

// getters
const getters = {
 
  GET_IS_LOGIN(state) {
    return !!state.jwtToken;
  },
  GET_JWT_TOKEN(state){
    return state.jwtToken;
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
  SET_JWTTOKEN(state,token){
    state.jwtToken = token;
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
      context.commit('OPEN_MODAL', {title: '에러', content: e.response.message, option1: '닫기',});
    } 
  },

  // 로그인
  async QUERY_LOGIN(context, req) {
    try {
      context.commit('START_LOADING')
      const response = await login(req);
      router.push("/")

      setTokenInLocalStorage(response.data.jwtToken);
      context.commit('SET_JWTTOKEN',response.data.jwtToken );
  
      const response1 = await getUser();
      context.commit('SET_USER',response1.data)

      return response.jwtToken;
    } catch (e) {
      console.log(e);
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

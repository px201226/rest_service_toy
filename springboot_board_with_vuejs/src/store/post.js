import { getPostsList, writePost, deletePost,updatePost, getPost } from "../api/post_api";
import {setTokenInLocalStorage} from "./token"
import {setSnackBarInfo} from "../api/common_api"
import router from "../router";

// state
const state = {
  // 페이지 정보  
  page:{
   
  },

  // 포스트 리스트
  posts: [],

  post:{},
};

// getters
const getters = {

  GET_PAGE(state){
    return state.page;
  },

  GET_POST_LIST(state) {
    return state.posts;
  },



};

// mutations
const mutations = {
  ADD_POST_LIST(state, posts) {
    state.posts = state.posts.concat(posts);
  },

  SET_PAGE(state, page){
    state.page = page;
  },
  
  CLEAR_POST(state){
    state.page = {};
    state.posts = [];
  }

};

// actions
const actions = {

  async QUERY_POST_LIST(context, page) {
    try {
      context.commit('START_LOADING')
      const response = await getPostsList(page);
      console.log(response)
      context.commit("ADD_POST_LIST", response.data.content);
      context.commit("SET_PAGE", response.data.page);
      return  response.data.content;
    } catch (e) {
      return Promise.reject(e);
    }
  },

  async QUERY_GET_POST(context, postId) {
    try {
      context.commit('START_LOADING')
      const response = await getPost(postId);
      return  response.data;
    } catch (e) {
      return Promise.reject(e);
    }
  },

  async QUERY_DELETE_POST(context,id) {
    try {
      context.commit('START_LOADING')
      const response = await deletePost(id);
      context.commit('OPEN_SNACKBAR', setSnackBarInfo('게시물이 삭제되었습니다.', 'success', 'top'))
      return response.data;
    } catch (e) {
      context.commit('OPEN_MODAL', {title: '에러', content: e.response.message, option1: '닫기',});
      return Promise.reject(e);
    }
  },

  

  async QUERY_WRITE_POST(context, req) {
    try {
      context.commit('START_LOADING')
      const response = await writePost(req);
      context.commit('OPEN_SNACKBAR', setSnackBarInfo('게시물이 작성되었습니다.', 'success', 'top'))
      return response.data;
    } catch (e) {
      context.commit('OPEN_MODAL', {title: '에러', content: e.response.data.message, option1: '닫기',});
      return Promise.reject(e);
    } finally {
    }
  },

  async QUERY_UPDATE_POST(context, req) {
    try {
      context.commit('START_LOADING')
      const response = await updatePost(req.id, req);
      context.commit('OPEN_SNACKBAR', setSnackBarInfo('게시물이 수정되었습니다.', 'success', 'top'))
      router.push("/");
      return response.data;
    } catch (e) {
      context.commit('OPEN_MODAL', {title: '에러', content: e.response.message, option1: '닫기',});
      return Promise.reject(e);
      } finally {
    }
  },
};

export default {
  state,
  mutations,
  actions,
  getters,
};

import { getCommentList, writeComment, deleteComment,updateComment } from "../api/commnet_api";
import {setTokenInLocalStorage} from "./token"
import {setSnackBarInfo} from "../api/common_api"
import router from "../router";

// state
const state = {

  
  comments: [],                    // 포스트 리스트
};

// getters
const getters = {

  GET_COMMENT_LIST(state) {
    return state.comments;
  },

};

// mutations
const mutations = {

  SET_COMMENS(state, comments){
    state.comments = comments;
  },
  
};

// actions
const actions = {

  async QUERY_COMMENT_LIST(context, page) {
    try {
      context.commit('START_LOADING')
      const response = await getCommentList(page);
      context.commit("SET_COMMENS", response.data._embedded.commentModels);
      return  response.data;
    } catch (e) {
      return Promise.reject(e);
    }
  },

  async QUERY_DELETE_COMMENT(context,req) {
    try {
      context.commit('START_LOADING')
      const response = await deleteComment(req.postId, req.commentId);
      context.commit('OPEN_SNACKBAR', setSnackBarInfo('댓글 삭제되었습니다.', 'success', 'top'))
      return response.data;
    } catch (e) {
      context.commit('OPEN_MODAL', {title: '에러', content: e.response.message, option1: '닫기',});
      return Promise.reject(e);
    }
  },

  

  async QUERY_WRITE_COMMENT(context, req) {
    try {
      console.log(req);
      context.commit('START_LOADING')
      const response = await writeComment(req.postId,req);
      context.commit('OPEN_SNACKBAR', setSnackBarInfo('댓글이 작성되었습니다.', 'success', 'top'))
      return response.data;
    } catch (e) {
      //context.commit('OPEN_MODAL', {title: '에러', content: e.response.data.message, option1: '닫기',});
      return Promise.reject(e);
    } finally {
    }
  },

  async QUERY_UPDATE_COMMENT(context, req) {
    try {
      context.commit('START_LOADING')
      const response = await updateComment(req.postId, req.commentId, {content:req.content});
      context.commit('OPEN_SNACKBAR', setSnackBarInfo('게시물이 수정되었습니다.', 'success', 'top'))
      return response.data;
    } catch (e) {
      //context.commit('OPEN_MODAL', {title: '에러', content: e.response.message, option1: '닫기',});
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

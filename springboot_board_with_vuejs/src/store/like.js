import store from '.';
import {like,unlike } from "../api/like_api";
import {setTokenInLocalStorage} from "./token"
import {setSnackBarInfo} from "../api/common_api"

// state
const state = {
 
};

// getters
const getters = {
 
};

// mutations
const mutations = {
  
};

// actions
const actions = {
  
  // 좋아요
  async QUERY_LIKE(context, req) {
    try {
      return await like(req);
    } catch (e) {
      return Promise.reject(e);
    } 
  },

 // 좋아요 취소
 async QUERY_UNLIKE(context, req) {
  try {
    const response = await unlike(req);
    return response;
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

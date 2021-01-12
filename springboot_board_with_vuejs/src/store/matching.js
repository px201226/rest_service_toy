import store from '.';
import {getNextMatchingDay } from "../api/matching_api";
import {setTokenInLocalStorage,setSnackBarInfo} from "./token"

// state
const state = {
  nextMatchingDay :"",

};

// getters
const getters = {

  GET_MATCHING_DAY(state){
    return state.nextMatchingDay;
  },
};

// mutations
const mutations = {
  SET_MATCHING_DAY(state, day){
    state.nextMatchingDay = day;
  },
};

// actions
const actions = {
  
  // 좋아요
  async QUERY_NEXT_MATCHING_DAY(context) {
    try {
      const response = await getNextMatchingDay();
      context.commit("SET_MATCHING_DAY", response.data);
      console.log(context.getters.GET_MATCHING_DAY);
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

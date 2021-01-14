import store from '.';
import {getNextMatchingDay, getApplyHistory, applyMatching, getMatchingResult } from "../api/matching_api";
import router from "../router";

// state
const state = {
  nextMatchingDay :"",
  isMatching : "",
  isApplied : false,
  matchingResult :""
};

// getters
const getters = {

  GET_MATCHING_DAY(state){
    return state.nextMatchingDay;
  },

  GET_IS_MATCHING(state){
    return state.isMatching;
  },

  GET_IS_APPLIED(state){
    return state.isApplied;
  },

  GET_MATCHING_RESULT(state){
    return state.matchingResult;
  }
};

// mutations
const mutations = {
  SET_MATCHING_DAY(state, day){
    state.nextMatchingDay = day;
  },
  
  SET_IS_MATCHING(state, bool){
    state.isMatching = bool;
  },
  
  SET_IS_APPLIED(state, bool){
    state.isApplied = bool;
  },

  SET_MATCHING_RESULT(state, result){
    state.matchingResult = result;
  }
};

// actions
const actions = {
  
  async QUERY_NEXT_MATCHING_DAY(context) {
    try {
      const response = await getNextMatchingDay();
      context.commit("SET_MATCHING_DAY", response.data);
      return response.data;
    } catch (e) {
      return Promise.reject(e);
    } 
  },

  async QUERY_MATCHING_RESULT(context) {
    try {
      const response = await getMatchingResult();
      context.commit("SET_MATCHING_RESULT", response.data);
      context.commit("SET_IS_MATCHING", response.data.isMatching);
      return response.data;
    } catch (e) {
      return Promise.reject(e);
    } 
  },

  
  async QUERY_APPLT_MATCHING(context) {
    try {
      const response = await applyMatching();
  
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

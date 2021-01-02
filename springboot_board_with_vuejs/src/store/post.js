import { getPostsList, readPostByPostId, writePost, deletePost,updatePost } from "../api/post_api";
import router from "../router";

// state
const state = {
  post: {

  },

  posts: [],
};

// getters
const getters = {
  GET_POST_DETAIL(state) {
    return state.post;
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

  SET_POST_DETAIL(state, post) {
    state.post = post;
  },
};

// actions
const actions = {
  async QUERY_POST_DETAIL(context, id) {
    try {
      const response = await readPostByPostId(id);
      context.commit("SET_POST_DETAIL", response.data);
      console.log(response.data);

      return response.data;
    } catch (e) {

      return Promise.reject(e);
    }
  },

  async QUERY_DELETE_POST(context,id) {
    try {
      const response = await deletePost(id);
      alert('게시물을 삭제하였습니다');
      router.push("/");
      return response.data;
    } catch (e) {
      alert("권한이 없습니다");
      return Promise.reject(e);
    }
  },

  async QUERY_POST_LIST(context, page) {
    try {
      const response = await getPostsList(page);
      context.commit("ADD_POST_LIST", response.data._embedded.postModels);
      console.log(response.data._embedded.postModels);
      return response.data._embedded.postModels;
    } catch (e) {
      return Promise.reject(e);
    }
  },

  async QUERY_WRITE_POST(context, requestPostSaveDto) {
    try {
      const response = await writePost(requestPostSaveDto);
      router.push("/");
      return response.data;
    } catch (e) {
      alert("권한이 없습니다");
      return Promise.reject(e);
    } finally {
    }
  },

  async QUERY_UPDATE_POST(context, requestPostUpdateDto) {
    try {
      const response = await updatePost(requestPostUpdateDto.id, requestPostUpdateDto);
      alert('게시물을 수정하였습니다.');
      router.push("/");
      return response.data;
    } catch (e) {
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

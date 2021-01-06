/* ## BoardList.vue 내용 */
<template>
  <div class="pa-3">
    <v-row row>
      <v-col class="flex-grow-0 flex-shrink-0">
        <v-icon lef large color="grey">mdi-message-reply-text</v-icon>
      </v-col>
      <v-col>
        <v-btn
          block
          depressed
          outlined
          plain
          rounded
          text
          @click="openPostWriter"
        >
          <span> 자신의 생각을 말해보세요! </span>
        </v-btn>
      </v-col>
      <v-col class="flex-grow-0 flex-shrink-0">
        <v-btn icon color="primary" @click="refresh">
          <v-icon>mdi-cached</v-icon>
        </v-btn>
      </v-col>
    </v-row>

    <div id="posts">
      <post-item
        v-for="(post, index) in getPostList"
        :key="index"
        :id="post.id"
        :nickName="post.userNickName"
        :content="post.content"
        :date="post.modifyDate"
        :likes="post.likes"
        :isLike="post.isLike"
        :comments="post.comments"
        :isWriter="post.isWriter"
        class="my-3"
      >
      </post-item>
    </div>

    <post-write @commit="refresh" />
    <infinite-loading @infinite="infiniteHandler" spinner="waveDots">
      <div
        slot="no-more"
        style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;"
      ></div>
    </infinite-loading>
    <v-col class="text-right">
      <v-btn color="primary" @click="routePostArticle">게시글 작성</v-btn>
    </v-col>
    <modal @pass="deleteEmit" />
  </div>
</template>

<script>
import InfiniteLoading from "vue-infinite-loading";
import PostItem from "./PostItem.vue";
import PostWrite from "./PostWrite.vue";
import Modal from "../components/Modal.vue";

export default {
  name: "Home",
  components: { PostItem, InfiniteLoading, PostWrite, Modal },

  data() {
    return {
      pageAble: { totalPages: 0 },
      pageNum: 0,
      posts: "",
      state: "",
    };
  },

  created() {
    console.log("create");
    this.$store.commit("CLEAR_POST");
    console.log(this.$store.getters.GET_POST_LIST);
  },

  computed: {
    getPostList() {
      return this.$store.getters.GET_POST_LIST;
    },

    loading() {
      return this.$store.getters.GET_LOADING_STATE;
    },
  },
  methods: {
    routePostArticle() {
      this.$store.commit("OPEN_MODAL", {
        title: "에러",
        content: "dd",
        option1: "닫기",
      });
      // this.$router.push("/save");
    },

    infiniteHandler($state) {
      this.state = $state;
      this.$store.dispatch("QUERY_POST_LIST", this.pageNum++).then(() => {
        if (this.$store.getters.GET_PAGE.totalPages > this.pageNum) {
          $state.loaded();
        } else {
          $state.complete();
        }
      });
    },

    openPostWriter() {
      this.$store.commit("OPEN_POST_WRITER");
    },

    refresh() {
      this.$store.commit("CLEAR_POST");
      this.pageNum = 0;
      this.state.reset();
    },

    deleteEmit(id) {
      this.$store.dispatch("QUERY_DELETE_POST", id).then(() => {
        this.refresh();
      });
    },
  },
};
</script>

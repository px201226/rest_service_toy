/* ## BoardList.vue 내용 */
<template>
  <v-container>
    <v-layout class="mx-2 my-3" row>
      <v-flex xs1>
        <v-icon lef large color="grey">mdi-message-reply-text</v-icon>
      </v-flex>
      <v-flex xs11>
        <v-btn block depressed outlined plain rounded text>
          <span> 자신의 생각을 말해보세요! </span>
        </v-btn>
      </v-flex>
    </v-layout>
    <div id="posts">
      <post-view
        v-for="(post, index) in getPostList"
        :key="index"
        :nickName="post.userNickName"
        :content="post.content"
        :date="post.modifyDate"
        :likes="post.likes"
        :comments="post.comments"
        class="my-3"
      >
      </post-view>
    </div>
    <infinite-loading @infinite="infiniteHandler" spinner="waveDots">
      <div
        slot="no-more"
        style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;"
      ></div>
    </infinite-loading>
    <v-col class="text-right">
      <v-btn color="primary" @click="routePostArticle">게시글 작성</v-btn>
    </v-col>
  </v-container>
</template>

<script>
import InfiniteLoading from "vue-infinite-loading";
import PostView from "./PostView.vue";
import axios from "axios";
export default {
  name: "Home",
  components: { PostView, InfiniteLoading },

  data() {
    return {
      pageAble: { totalPages: 0 },
      pageNum: 0,
      posts: "",
    };
  },
  created() {},
  computed: {
    getPostList() {
      return this.$store.getters.GET_POST_LIST;
    },

    loading() {
      return this.$store.getters.GET_LOADING_STATE;
    },
  },
  methods: {
    clickItem(item) {
      console.log(item.id);
      this.$router.push("/posts/" + item.id);
    },

    routePostArticle() {
      this.$router.push("/save");
    },
    infiniteHandler($state) {
      this.$store.dispatch("QUERY_POST_LIST", this.pageNum++).then(() => {
        console.log(
          "total=" +
            this.$store.getters.GET_PAGE.totalPages +
            " cur=" +
            this.pageNum
        );
        if (this.$store.getters.GET_PAGE.totalPages > this.pageNum) {
          $state.loaded();
        } else {
          $state.complete();
        }
      });
    },
  },
};
</script>

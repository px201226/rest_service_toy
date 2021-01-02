/* ## BoardList.vue 내용 */
<template>
  <v-container>
    <v-layout class="mx-2 my-3" row>
      <v-flex xs1>
        <v-icon lef large color="grey">mdi-message-reply-text</v-icon>
      </v-flex>
      <v-flex xs11>
        <v-btn block depressed outlined plain rounded text>
          <span> 자신의 생각을 말해보세요! </span> {{ getPostList.length }}
        </v-btn>
      </v-flex>
    </v-layout>
    <div id="posts">
      <post-view
        v-for="post in getPostList"
        :key="post.id"
        :nickName="post.nickName"
        :content="post.content"
      >
      </post-view>
    </div>
    <infinite-loading @infinite="infiniteHandler" spinner="waveDots">
      <div
        slot="no-more"
        style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;"
      >
        목록의 끝입니다 :)
      </div>
    </infinite-loading>
    <v-col class="text-right">
      <v-btn color="primary" @click="routePostArticle">게시글 작성</v-btn>
    </v-col>
  </v-container>
</template>

<script>
import InfiniteLoading from "vue-infinite-loading";
import PostView from "./PostView.vue";

export default {
  name: "BoardList",
  components: { PostView, InfiniteLoading },

  data() {
    return {
      page: 0,
      posts: this.$store.getters.GET_POST_LIST,
    };
  },
  created() {
    this.queryGetPostList(this.page);
  },
  computed: {
    getPostList() {
      return this.$store.getters.GET_POST_LIST;
    },
  },
  methods: {
    clickItem(item) {
      console.log(item.id);
      this.$router.push("/posts/" + item.id);
    },
    queryGetPostList(page) {
      this.$store.dispatch("QUERY_POST_LIST", page);
    },
    routePostArticle() {
      this.$router.push("/save");
    },
    infiniteHandler($state) {
      this.queryGetPostList(++this.page);
      console.log("page=" + this.page);
    },
  },
};
</script>

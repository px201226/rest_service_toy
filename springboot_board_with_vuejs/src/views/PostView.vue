<template>
  <v-card id="view">
    <v-toolbar dark color="primary">
      <v-btn icon dark @click="onBackButtonClick">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-toolbar-title>뒤로가기</v-toolbar-title>
      <v-spacer></v-spacer>
    </v-toolbar>

    <div class="pa-2">
      <v-row row>
        <v-col class="flex-grow-0 flex-shrink-0">
          <v-avatar size="36px">
            <img src="@/assets/no_profile.gif" alt="John" />
          </v-avatar>
        </v-col>

        <v-col class="">
          <v-row>
            <div class="text--primary font-weight-bold">
              {{ post.userNickName }}
            </div>
          </v-row>
          <v-row>
            <div class="font-weight-light">{{ post.modifyDate }}</div>
          </v-row>
        </v-col>
        <div class="mr-2" v-if="post.isWriter">
          <v-btn class="ml-auto" icon>
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
          <v-btn class="ml-auto" icon @click="onDeleteButtonClick">
            <v-icon>mdi-delete</v-icon>
          </v-btn>
        </div>
      </v-row>

      <div class="font-weight-regular ml-4 mt-3 mb-6">
        {{ post.content }}
      </div>

      <v-card-actions>
        <v-btn icon :color="isLikeColor" @click="onLikeButtonClick">
          <v-icon>mdi-heart</v-icon>
        </v-btn>
        <v-btn text color="" class="ml-auto">
          좋아요 {{ post.likes }}개 | 댓글 {{ getCommentList.length }}개
        </v-btn>
      </v-card-actions>
    </div>

    <v-divider />
    <v-row class="pa-5 pb-0 ">
      <v-textarea
        v-model="commentReq"
        label="댓글을 입력하세요"
        auto-grow
        outlined
        rows="1"
        row-height="5"
      ></v-textarea>
      <v-btn
        class="mx-1 "
        height="55"
        depressed
        color="primary"
        @click="onCommentWriteClick"
        >작성</v-btn
      >
    </v-row>
    <v-divider />

    <comment
      v-for="(comment, index) in getCommentList"
      :key="index"
      :postId="postId"
      :commentId="comment.id"
      :nickName="comment.userNickName"
      :content="comment.content"
      :date="comment.modifyDate"
      :isLike="comment.isLike"
      :isWriter="comment.isWriter"
      class="my-1"
    />

    <modal @postDelete="deletePost" @commentDelete="deleteComment" />
  </v-card>
</template>

<script>
import Modal from "../components/Modal.vue";
import BottomNavigator from "./BottomNavigator.vue";
import Comment from "./Comment.vue";

export default {
  components: { BottomNavigator, Comment, Modal },
  name: "PostView",
  data() {
    return {
      commentReq: "",
      postId: this.$route.params.id,
      post: "",
    };
  },
  mounted() {
    // 툴바, 네비게이션바 숨기기
    this.$store.commit("APPBAR_DISPLAY", false);
    this.$store.commit("NAVIBAR_DISPLAY", false);
  },

  created() {
    // POST READ
    this.$store
      .dispatch("QUERY_GET_POST", this.$route.params.id)
      .then((res) => {
        console.log(res);
        this.post = res;
      });

    //COMMENT READ
    this.$store.dispatch("QUERY_COMMENT_LIST", this.$route.params.id);
  },

  destroyed() {
    this.$store.commit("APPBAR_DISPLAY", true);
    this.$store.commit("NAVIBAR_DISPLAY", true);
  },

  computed: {
    isLikeColor() {
      if (this.post.isLike === true) return "pink";
      else return "grey";
    },

    getCommentList() {
      return this.$store.getters.GET_COMMENT_LIST;
    },
  },

  methods: {
    deletePost(id) {
      this.$store.dispatch("QUERY_DELETE_POST", id).then(() => {
        this.$router.back();
      });
    },
    deleteComment(req) {
      this.$store.dispatch("QUERY_DELETE_COMMENT", req).then(() => {
        this.$store.dispatch("QUERY_COMMENT_LIST", this.$route.params.id);
      });
    },
    onBackButtonClick() {
      this.$router.back();
    },

    onDeleteButtonClick() {
      this.$store.commit("OPEN_MODAL", {
        title: "확인",
        content: "게시물을 정말로 삭제하시겠습니까?",
        option1: "닫기",
        option2: "삭제",
        event: "postDelete",
        data: this.post.id,
      });
    },

    onLikeButtonClick() {
      if (this.post.isLike === false) {
        this.$store
          .dispatch("QUERY_LIKE", this.post.id)
          .then((req) => {
            this.post.isLike = true;
            this.post.likes++;
          })
          .catch(() => "");
      } else {
        this.$store
          .dispatch("QUERY_UNLIKE", this.post.id)
          .then(() => {
            this.post.isLike = false;
            this.post.likes--;
          })
          .catch(() => "");
      }
    },

    onCommentWriteClick() {
      this.$store
        .dispatch("QUERY_WRITE_COMMENT", {
          postId: this.post.id,
          content: this.commentReq,
        })
        .then((req) => {
          this.commentReq = "";
          this.$store
            .dispatch("QUERY_COMMENT_LIST", this.$route.params.id)
            .then(() => {
              var objDiv = document.getElementById("view");
              window.scrollTo({ top: objDiv.scrollHeight, behavior: "smooth" });
            });
        })
        .catch(() => "");
    },
  },
};
</script>

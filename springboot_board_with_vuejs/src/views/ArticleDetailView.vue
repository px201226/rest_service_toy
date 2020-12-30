<template>
  <div>
    <h1 class="d-flex justify-center mt-3">{{ getPostDetail.title }}</h1>
    <p class="d-flex justify-center grey--text text--lighten-1">
      {{ getPostDetail.author }} / {{ getPostDetail.modifiedDate }}
    </p>
    <v-divider />
    <div class="my-5">
      <MarkdownView :markdownstring="getPostDetail.content"> </MarkdownView>
    </div>
    <v-divider />

    <div class="d-flex mt-6">
      <v-spacer></v-spacer>
      <v-btn class="d-flex justify-end " @click="routeModifyPage">
        <v-icon left>
          mdi-pencil
        </v-icon>
        글 수정
      </v-btn>

      <v-btn class="d-flex justify-end" @click="deleteAriticle">
        <v-icon left>
          mdi-delete
        </v-icon>
        삭제
      </v-btn>
    </div>
  </div>
</template>

<script>
import MarkdownView from "../components/MarkdownView";
import { eventBus } from "../main.js";

export default {
  components: {
    MarkdownView,
  },
  data() {
    return {
      id: this.$route.params.id,
    };
  },
  created() {
    this.$store.dispatch("QUERY_POST_DETAIL", this.id);
  },

  computed: {
    getPostDetail() {
      eventBus.$emit("FETCH", this.$store.getters.GET_POST_DETAIL);
      return this.$store.getters.GET_POST_DETAIL;
    },
  },
  methods: {
    isAuthenticated() {
      var post = this.$store.getters.GET_POST_DETAIL;
      var sessionUser = this.$store.getters.GET_USER;
      return post.writerEmail === sessionUser.email;
    },
    routeModifyPage() {
      if (!this.isAuthenticated()) {
        alert("권한이 없습니다");
        return;
      }
      this.$router.push("/update");
    },
    deleteAriticle() {
      if (!this.isAuthenticated()) {
        alert("권한이 없습니다");
        return;
      }
      this.$store.dispatch("QUERY_DELETE_POST", this.getPostDetail.id);
    },
  },
};
</script>

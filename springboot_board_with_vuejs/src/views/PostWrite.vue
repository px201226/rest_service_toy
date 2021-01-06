<template>
  <v-dialog
    v-model="modal"
    fullscreen
    hide-overlay
    transition="dialog-bottom-transition"
  >
    <v-card>
      <v-toolbar dark color="primary">
        <v-btn icon dark @click="close">
          <v-icon>mdi-close</v-icon>
        </v-btn>
        <v-toolbar-title>게시물 작성</v-toolbar-title>
        <v-spacer></v-spacer>
      </v-toolbar>

      <div class="px-2 pt-4 d-inline-flex align-center">
        <v-avatar size="36px">
          <img src="@/assets/no_profile.gif" alt="John" />
        </v-avatar>
        <div class="mx-2 text--primary font-weight-bold">
          {{ user.nickName }}
        </div>
      </div>

      <v-row class="pa-5 py-3 pb-0 ">
        <v-textarea
          label="게시물 내용을 입력하세요"
          auto-grow
          outlined
          rows="10"
          row-height="32"
          v-model="content"
        ></v-textarea>
      </v-row>
      <v-btn
        block
        class="mx-1 "
        height="55"
        depressed
        color="primary"
        :loading="loading"
        @click="post"
        >작성</v-btn
      >
      <v-divider />
    </v-card>
  </v-dialog>
</template>

<script>
import BottomNavigator from "./BottomNavigator.vue";
import Comment from "./Comment.vue";

export default {
  name: "PostWirter",

  data() {
    return {
      content: "",
    };
  },

  computed: {
    loading() {
      return this.$store.state.common.loading;
    },
    modal() {
      return this.$store.state.common.postModal;
    },

    user() {
      return this.$store.getters.GET_USER;
    },
  },

  methods: {
    close() {
      this.$store.commit("CLOSE_POST_WRITER");
    },

    post() {
      this.$store
        .dispatch("QUERY_WRITE_POST", { content: this.content })
        .then(() => {
          this.content = "";
          this.$emit("commit");
          this.close();
        });
    },
  },
};
</script>

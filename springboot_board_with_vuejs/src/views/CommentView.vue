<template>
  <v-dialog
    v-model="modal.open"
    fullscreen
    hide-overlay
    transition="dialog-bottom-transition"
  >
    <v-card>
      <v-toolbar dark color="primary">
        <v-btn icon dark @click="dialog = false">
          <v-icon>mdi-close</v-icon>
        </v-btn>
        <v-toolbar-title>댓글</v-toolbar-title>
        <v-spacer></v-spacer>
      </v-toolbar>
      <v-row class="pa-5 pb-0 ">
        <v-textarea
          label="댓글을 입력하세요"
          auto-grow
          outlined
          rows="1"
          row-height="5"
        ></v-textarea>
        <v-btn class="mx-1 " height="55" depressed color="primary">작성</v-btn>
      </v-row>
      <v-divider />
      <comment /> <comment /> <comment />
      <comment />
    </v-card>
  </v-dialog>
</template>

<script>
import BottomNavigator from "./BottomNavigator.vue";
import Comment from "./Comment.vue";

export default {
  components: { BottomNavigator, Comment },
  name: "CommentView",
  computed: {
    modal() {
      return this.$store.state.common.modal;
    },
  },
};
</script>

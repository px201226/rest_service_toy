<template>
  <v-row no-gutters>
    <v-avatar size="25px" class="ml-1 mt-2">
      <img
        src="https://lh5.googleusercontent.com/-2QXdr8wesbM/AAAAAAAAAAI/AAAAAAAAAAA/AMZuucnb024lRD57ypepPkcphVu_7loiBQ/s96-c/photo.jpg"
        alt="John"
      />
    </v-avatar>

    <v-col class="pa-2">
      <v-card color="px-6 py-3 grey lighten-4" elevation="1">
        <v-row>
          <p class="font-weight-regular ">
            {{ nickName }}
          </p>
          <v-btn
            class="ml-2"
            x-small
            icon
            @click="onEditButton"
            v-if="isWriter"
          >
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
          <v-btn
            class=""
            x-small
            icon
            @click="onDeleteButtonClick"
            v-if="isWriter"
          >
            <v-icon>mdi-delete</v-icon>
          </v-btn>

          <div class="ml-auto">
            <p class="text-right caption">
              {{ date }}
            </p>
          </div>
        </v-row>
        <v-row>
          <div v-if="!isEdit" v-html="content" class="body-2"></div>
          <v-row class="mx-1" v-if="isEdit">
            <v-textarea
              class="caption"
              v-model="editContent"
              auto-grow
              outlined
            ></v-textarea>
          </v-row>
        </v-row>
        <v-row>
          <v-btn
            block
            v-if="isEdit"
            color="primary"
            dark
            @click="onUpdateButtonClick"
          >
            <v-icon> mdi-check </v-icon>
            수정{{ comment.content }}
          </v-btn>
        </v-row>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: "Comment",
  props: ["postId", "commentId", "nickName", "date", "content", "isWriter"],

  data() {
    return {
      comment: {
        id: this.commentId,
        postId: this.postId,
        nickName: this.nickName,
        date: this.date,
        content: this.content,
      },
      isEdit: false,
      editContent: this.content,
    };
  },

  methods: {
    onEditButton() {
      this.isEdit = !this.isEdit;
    },
    onUpdateButtonClick() {
      this.isEdit = !this.isEdit;
      this.$store.dispatch("QUERY_UPDATE_COMMENT", {
        postId: this.postId,
        commentId: this.commentId,
        content: this.editContent,
      });
    },

    onDeleteButtonClick() {
      this.$store.commit("OPEN_MODAL", {
        title: "확인",
        content: "댓글을 정말로 삭제하시겠습니까?",
        option1: "닫기",
        option2: "삭제",
        event: "commentDelete",
        data: { postId: this.postId, commentId: this.commentId },
      });
    },
  },
};
</script>

<template>
  <v-card class="pa-2" contextmenu="" flat outlined>
    <v-row row>
      <v-col class="flex-grow-0 flex-shrink-0">
        <v-avatar size="36px">
          <img src="@/assets/no_profile.gif" alt="John" />
        </v-avatar>
      </v-col>

      <v-col class="">
        <v-row>
          <div class="text--primary font-weight-bold">
            {{ nickName }}
          </div>
        </v-row>
        <v-row>
          <div class="font-weight-light">{{ date }}</div>
        </v-row>
      </v-col>
      <div class="mr-2" v-if="isWriter">
        <v-btn class="ml-auto" icon>
          <v-icon>mdi-pencil</v-icon>
        </v-btn>
        <v-btn class="ml-auto" icon @click="onDeleteButtonClick">
          <v-icon>mdi-delete</v-icon>
        </v-btn>
      </div>
    </v-row>

    <div class="font-weight-regular ml-4 mt-3 mb-6">
      {{ content }}
    </div>

    <v-card-actions>
      <v-btn icon :color="isLikeColor" @click="onLikeButtonClick">
        <v-icon>mdi-heart</v-icon>
      </v-btn>
      <v-btn text color="" class="ml-auto" @click="openPostView">
        좋아요 {{ mlikes }}개 | 댓글 {{ comments }}개
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  name: "PostItem",
  props: [
    "id",
    "nickName",
    "date",
    "likes",
    "comments",
    "content",
    "isLike",
    "isWriter",
  ],
  data() {
    return {
      like: this.isLike,
      mlikes: this.likes,
      expand: false,
      expand2: false,
    };
  },
  components: {},

  computed: {
    isLikeColor() {
      if (this.like === true) return "pink";
      else return "grey";
    },
  },

  methods: {
    onDeleteButtonClick() {
      this.$store.commit("OPEN_MODAL", {
        title: "확인",
        content: "게시물을 정말로 삭제하시겠습니까?",
        option1: "닫기",
        option2: "삭제",
        event: "deletePost",
        data: this.id,
      });
    },

    onLikeButtonClick() {
      if (this.like === false) {
        this.$store
          .dispatch("QUERY_LIKE", this.id)
          .then((req) => {
            this.like = true;
            this.mlikes++;
          })
          .catch(() => "");
      } else {
        this.$store
          .dispatch("QUERY_UNLIKE", this.id)
          .then(() => {
            this.like = false;
            this.mlikes--;
          })
          .catch(() => "");
      }
    },

    openPostView() {
      this.$router.push("/posts/" + this.id);
    },
  },
};
</script>

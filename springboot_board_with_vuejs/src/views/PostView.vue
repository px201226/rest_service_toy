<template>
  <v-card flat outlined>
    <v-layout row>
      <div class="pa-5 d-inline-flex">
        <v-avatar size="36px">
          <img
            src="https://lh5.googleusercontent.com/-2QXdr8wesbM/AAAAAAAAAAI/AAAAAAAAAAA/AMZuucnb024lRD57ypepPkcphVu_7loiBQ/s96-c/photo.jpg"
            alt="John"
          />
        </v-avatar>
      </div>
      <div class="pa-5 pl-2 py-4">
        <v-row>
          <div class="text--primary font-weight-bold">
            {{ nickName }}
          </div>
        </v-row>
        <v-row>
          <div class="font-weight-light">{{ date }}</div>
        </v-row>
      </div>
    </v-layout>

    <div class="font-weight-regular ml-4 mt-3 mb-6">
      {{ content }}
    </div>

    <v-card-actions>
      <v-btn icon color="pink">
        <v-icon>mdi-heart</v-icon>
      </v-btn>
      <v-btn text color="" class="ml-auto" @click="expand = !expand">
        좋아요 {{ likes }}개 | 댓글 {{ comments }}개
      </v-btn>
    </v-card-actions>
    <v-expand-transition>
      <comment v-show="expand">d </comment>
    </v-expand-transition>
  </v-card>
</template>

<script>
import Comment from "./Comment.vue";
export default {
  name: "PostView",
  props: ["nickName", "date", "likes", "comments", "content"],
  data() {
    return {
      expand: false,
      expand2: false,
    };
  },
  components: { Comment },
  created() {
    //this.$store.dispatch("QUERY_GET_USER");
  },
  computed: {
    getUser() {
      return this.$store.getters.GET_USER;
    },
  },
};
</script>

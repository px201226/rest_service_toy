<template>
  <v-card class="mx-auto" max-width="344">
    <div>
      <v-row>
        <v-col cols="auto">
          <v-avatar size="36px" class="ml-2 mt-2">
            <img
              src="https://lh5.googleusercontent.com/-2QXdr8wesbM/AAAAAAAAAAI/AAAAAAAAAAA/AMZuucnb024lRD57ypepPkcphVu_7loiBQ/s96-c/photo.jpg"
              alt="John"
            />
          </v-avatar>
        </v-col>
        <v-col>
          <v-row>
            <div class="text--primary">
              닉네임
            </div>
          </v-row>
          <v-row>
            <div class="font-weight-light">
              2014-12-12
            </div>
          </v-row>
        </v-col>
      </v-row>
    </div>
    <div class="font-weight-regular ml-4 my-2">
      Normal weight text.<br />Normal weight text.
    </div>

    <v-card-actions>
      <v-btn icon color="pink">
        <v-icon>mdi-heart</v-icon>
      </v-btn>
      <v-btn text color="" class="ml-auto" @click="expand = !expand">
        좋아요 0개 | 댓글 0개
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
  data: () => ({
    expand: false,
    expand2: false,
  }),
  components: { Comment },
  created() {
    this.$store.dispatch("QUERY_GET_USER");
  },
  computed: {
    getUser() {
      return this.$store.getters.GET_USER;
    },
  },
};
</script>

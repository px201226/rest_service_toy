<template>
  <v-card>
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
              {{ nickName }}
            </div>
          </v-row>
          <v-row>
            <div class="font-weight-light">
              {{ date }}
            </div>
          </v-row>
        </v-col>
      </v-row>
    </div>
    <div class="font-weight-regular ml-4 mt-3 mb-6">
      {{ content }}sadddddddsadsa
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
    this.$store.dispatch("QUERY_GET_USER");
  },
  computed: {
    getUser() {
      return this.$store.getters.GET_USER;
    },
  },
};
</script>

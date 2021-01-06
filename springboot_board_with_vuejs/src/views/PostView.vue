<template>
  <v-card>
    <v-toolbar dark color="primary">
      <v-btn icon dark @click="onBackButtonClick">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-toolbar-title>뒤로가기</v-toolbar-title>
      {{ post }}
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
</template>

<script>
import BottomNavigator from "./BottomNavigator.vue";
import Comment from "./Comment.vue";

export default {
  components: { BottomNavigator, Comment },
  name: "PostView",

  mounted() {
    console.log("mounted");
    this.$store.commit("APPBAR_DISPLAY", false);
    this.$store.commit("NAVIBAR_DISPLAY", false);
  },
  created() {
    this.$store.dispatch("QUERY_GET_POST", this.$route.params.id);
  },

  destroyed() {
    this.$store.commit("APPBAR_DISPLAY", true);
    this.$store.commit("NAVIBAR_DISPLAY", true);
  },

  computed: {
    post() {
      return this.$store.getters.GET_POST;
    },
  },

  methods: {
    onBackButtonClick() {
      this.$router.back();
    },
  },
};
</script>

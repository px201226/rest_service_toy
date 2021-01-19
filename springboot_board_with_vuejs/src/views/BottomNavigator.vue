<template>
  <v-bottom-navigation
    app
    horizontal
    fixed
    :input-value="navibarShow"
    :value="value"
    color="primary"
  >
    <v-btn @click="onClickBoard">
      <span>게시판</span>
      <v-icon>mdi-view-list</v-icon>
    </v-btn>

    <v-btn @click="onClickMatching">
      <span>짝짝쿵</span>
      <v-icon>mdi-heart</v-icon>
    </v-btn>

    <v-btn @click="onClickMyPage">
      <span>마이페이지</span>

      <v-icon>mdi-account</v-icon>
    </v-btn>
  </v-bottom-navigation>
</template>

<script>
export default {
  data: () => ({ value: 0 }),
  computed: {
    navibarShow() {
      return this.$store.getters.IS_NAVIBAR_SHOW;
    },
  },
  methods: {
    onClickBoard() {
      if (this.$route.path !== "/") this.$router.push("/");
    },
    onClickMatching() {
      if (this.$route.path !== "/matching") this.$router.push("/matching");
    },
    onClickMyPage() {
      if (this.$route.path !== "/mypage") this.$router.push("/mypage");
    },
  },
};
</script>

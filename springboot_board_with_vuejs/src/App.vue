<template>
  <v-app>
    <AppBar />
    <v-content class="">
      <!-- <MatchingResult /> -->
      <!-- <matching /> -->
      <!-- <register /> -->
      <!-- <login /> -->
      <!-- <PostView />
      <PostView />
      <PostView /> <PostView class=" " /> -->
      <router-view />
    </v-content>
    <SnackBar />
    <BottomNavigator />
  </v-app>
</template>

<script>
import Home from "./views/Home.vue";
import AppBar from "./views/AppBar.vue";
import About from "./views/About.vue";
import Modal from "././components/Modal.vue";
import SnackBar from "././components/SnackBar.vue";

import BottomNavigator from "./views/BottomNavigator.vue";
import PostView from "./views/PostView.vue";
import Login from "./views/Login.vue";
import Register from "./views/Register.vue";
import Matching from "./views/matching.vue";
import MatchingResult from "./views/MatchingResult.vue";

export default {
  name: "App",

  components: {
    Home,
    AppBar,
    About,
    BottomNavigator,
    PostView,
    Login,
    Register,
    Matching,
    MatchingResult,
    Modal,
    SnackBar,
  },

  data() {
    return {};
  },
};
</script>
<style scoped>
#app {
  width: 100%;
  height: 100%;
}
.v-application--wrap {
  min-height: 100% !important;
}
</style>

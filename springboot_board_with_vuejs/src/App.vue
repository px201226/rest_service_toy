<template>
  <v-app>
    <AppBar v-if="appbarShow" />

    <v-content class="">
      <!-- <MatchingResult /> -->
      <!-- <matching /> -->
      <!-- <register /> -->
      <!-- <login /> -->

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
  computed: {
    appbarShow() {
      return this.$store.getters.IS_APPBAR_SHOW;
    },
    navibarShow() {
      return this.$store.getters.IS_NAVIBAR_SHOW;
    },
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

<template>
  <v-app-bar app color="primary" dark>
    <div class="d-flex align-center">
      <v-list-item> 게시판 </v-list-item>
    </div>

    <v-spacer></v-spacer>
    <div align="center" justify="center" v-if="isLogin">
      <v-menu left bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn icon v-bind="attrs" v-on="on">
            <v-list-item-avatar>
              <v-img :src="getUser.picture"></v-img>
            </v-list-item-avatar>
          </v-btn>
        </template>

        <v-list>
          <v-list-item @click="logout">
            <v-list-item-title>로그아웃</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </div>
    <div v-if="!isLogin">
      <v-btn target="_blank" text @click="onLoginClick">
        로그인
        <v-icon>mdi-login</v-icon>
      </v-btn>
      <!-- <v-btn @click="login"> 테스트 </v-btn> -->
    </div>
  </v-app-bar>
</template>

<script>
export default {
  name: "AppBar",
  components: {},

  created() {
    this.$store.dispatch("QUERY_GET_USER");
  },
  computed: {
    getUser() {
      return this.$store.getters.GET_USER;
    },
    getUserName() {
      return this.$store.getters.GET_USER_NAME;
    },
    isLogin() {
      return this.$store.getters.GET_IS_LOGIN;
    },
  },
  methods: {
    login() {
      this.$store.dispatch("QUERY_TEST_USER");
    },
    logout() {
      this.$store.dispatch("QUERY_LOGOUT");
    },

    onLoginClick() {
      this.$router.push("/login");
    },
  },
};
</script>

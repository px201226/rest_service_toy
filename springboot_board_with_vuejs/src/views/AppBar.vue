<template>
  <v-app-bar app color="primary" dark>
    <div class="d-flex align-center">
      <v-list-item> 게시판 </v-list-item>
    </div>

    <v-spacer></v-spacer>

    <div align="center" justify="center" v-if="isLogin">
      <v-menu offset-y>
        <template v-slot:activator="{ on, attrs }">
          <v-btn class="pa-3" icon v-bind="attrs" v-on="on">
            <v-list-item-avatar>
              <img
                src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
              ></img>
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

  created() {},
  computed: {
    isLogin() {
      return this.$store.getters.GET_IS_LOGIN;
    },
  },
  methods: {
    login() {
      this.$store.dispatch("QUERY_TEST_USER");
    },
    logout() {
      this.$store.commit("LOGOUT");
    },

    onLoginClick() {
      this.$router.push("/login");
    },
  },
};
</script>

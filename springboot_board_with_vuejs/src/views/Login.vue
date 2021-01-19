<template>
  <v-container class="fill-height fill-width jusity-center" fluid="fluid">
    <v-layout
      row="row"
      justify-center="justify-center"
      align-center="align-center"
    >
      <v-flex xs12="xs12">
        <h1 class="text-center">로그인</h1>
        <v-form class="pa-3" ref="form">
          <v-text-field
            :rules="emailRules"
            outlined="outlined"
            label="이메일"
            prepend-icon="mdi-email"
            required="required"
            type="email"
            v-model="member.email"
          ></v-text-field>

          <v-text-field
            :rules="passwordRules"
            outlined="outlined"
            prepend-icon="mdi-lock"
            label="패스워드"
            @keyup.enter="loginRequest"
            required="required"
            type="password"
            v-model="member.password"
          ></v-text-field>

          <v-btn
            @click="loginRequest"
            block="block"
            color="primary"
            class="my-3 pa-7"
            outlined="outlined"
            :loading="loading"
          >
            <v-icon left="left">mdi-login</v-icon>
            로그인
          </v-btn>

          <v-btn
            depressed="depressed"
            block="block"
            class="mt-3 text--right pa-7"
            color="primary"
            @click="onRegisterClick"
          >
            회원가입
          </v-btn>
        </v-form>
      </v-flex>
    </v-layout>
    <modal />
  </v-container>
</template>

<script>
import Modal from "../components/Modal";

export default {
  name: "Login",
  data() {
    return {
      member: {
        email: "",
        password: "",
      },
      nextPath: this.$route.query.next ? this.$route.query.next : "/",
    };
  },
  mounted() {
    console.log(process.env);
    this.$store.commit("APPBAR_DISPLAY", false);
    this.$store.commit("NAVIBAR_DISPLAY", false);
  },
  destroyed() {
    this.$store.commit("APPBAR_DISPLAY", true);
    this.$store.commit("NAVIBAR_DISPLAY", true);
  },

  computed: {
    emailRules() {
      return this.$store.state.common.emailRules;
    },
    loading() {
      return this.$store.state.common.loading;
    },
    passwordRules() {
      return this.$store.state.common.passwordRules;
    },
  },
  components: {
    Modal,
  },
  methods: {
    loginRequest() {
      if (this.$refs.form.validate()) {
        this.$store
          .dispatch("QUERY_LOGIN", this.member)
          .then(() => this.$router.push(this.nextPath));
      }
    },

    onRegisterClick() {
      this.$router.push("/register");
    },
  },
};
</script>

<style scoped="scoped">
@media (min-width: 700px) {
  .login {
    width: 360px !important;
  }
}

.login {
  width: 80%;
  background-color: white;
  border-radius: 8px;
  border: white 1px solid;
  padding: 40px;
}
</style>

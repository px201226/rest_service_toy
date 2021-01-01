<template>
  <v-container class="fill-height fill-width jusity-center" fluid>
    <v-layout row justify-center align-center>
      <v-flex xs12>
        <h1 class="text-center">로그인</h1>
        <v-form class="pa-3" ref="form">
          <v-text-field
            :rules="emailRules"
            outlined
            label="이메일"
            prepend-icon="mdi-email"
            required
            type="email"
            v-model="member.email"
          ></v-text-field>

          <v-text-field
            :rules="passwordRules"
            outlined
            prepend-icon="mdi-lock"
            label="패스워드"
            @keyup.enter="loginRequest"
            required
            type="password"
            v-model="member.password"
          ></v-text-field>

          <v-btn
            @click="loginRequest"
            block
            color="primary"
            class="my-3 pa-7"
            outlined
          >
            <v-icon left>mdi-login</v-icon>
            로그인
          </v-btn>

          <v-btn
            depressed
            block
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
    };
  },
  computed: {
    emailRules() {
      return this.$store.state.common.emailRules;
    },
    loadingState() {
      return this.$store.state.common.loadingState;
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
        this.$store.dispatch("QUERY_LOGIN", this.member);
      }
    },

    onRegisterClick() {
      this.$router.push("/register");
    },
  },
};
</script>

<style scoped>
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

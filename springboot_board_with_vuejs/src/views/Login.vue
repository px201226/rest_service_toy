<template>
  <div class="fill-height">
    <v-container class="fill-height">
      <v-row align="center" class="fill-height" justify="center">
        <div class="login text-center">
          <h1>로그인</h1>
          <div>
            <v-form class="pa-3" ref="form">
              <v-text-field
                :rules="emailRules"
                label="이메일"
                prepend-icon="mdi-email"
                required
                type="email"
                v-model="member.email"
              ></v-text-field>

              <v-text-field
                :rules="passwordRules"
                prepend-icon="mdi-lock"
                label="패스워드"
                @keyup.enter="loginRequest"
                required
                type="password"
                v-model="member.password"
              ></v-text-field>

              <v-btn
                @click="loginRequest"
                class="d-flex"
                block
                color="primary"
                outlined
              >
                로그인
              </v-btn>

              <v-btn
                depressed
                block
                class="mt-3"
                color="primary"
                @click="onRegisterClick"
              >
                회원가입
              </v-btn>
            </v-form>
          </div>
        </div>
      </v-row>
    </v-container>
  </div>
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
        this.$store.dispatch("REQUEST_LOGIN", this.member);
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

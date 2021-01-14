<template>
  <div
    class="align-center d-flex flex-column fill-weidh fill-height pink lighten-1"
  >
    <v-col class="align-center justify-center pa-0 ">
      <v-row class=" justify-center align-center pt-7 purple lighten-2">
        <v-icon class="mr-5 mb-3" dark x-large> mdi-alarm </v-icon>
        <p class="white--text text-h4">
          다음 소개팅까지
        </p>
      </v-row>

      <v-row class="justify-center purple lighten-2">
        <timer :deadline="matchingDay" />
      </v-row>

      <v-row class="mt-10 px-10 justify-center ">
        <p class="text-center white--text text-h6">
          소개팅에 참여하시겠어요?
        </p>
      </v-row>
      <v-row class=" justify-center">
        <v-btn
          outlined
          color="pink accent-3"
          class="white--text white align-center"
          @click="onMatchingApply"
        >
          소개팅 신청하기
          <v-icon right> mdi-arrow-right </v-icon>
        </v-btn>
      </v-row>
      <v-row class="justify-center mt-7 px-10">
        <p class="text-center white--text text-h6">
          소개팅 결과를 확인해보세요!
        </p>
      </v-row>
      <v-row class=" justify-center">
        <v-btn
          outlined
          color="pink accent-3"
          class="white--text white align-center"
          @click="onMatchingResult"
        >
          결과확인
          <v-icon right> mdi-arrow-right </v-icon>
        </v-btn>
      </v-row>
    </v-col>
    <modal />
  </div>
</template>

<script>
import Modal from "../components/Modal.vue";
import Timer from "../components/Timer.vue";
import { validateLogin } from "../api/common_api";

export default {
  components: {
    Timer,
    Modal,
  },

  created() {
    this.$store.dispatch("QUERY_NEXT_MATCHING_DAY");
  },

  data() {
    return {};
  },

  computed: {
    matchingDay() {
      return this.$store.getters.GET_MATCHING_DAY;
    },

    isApplied() {
      return this.$store.getters.GET_IS_APPLIED;
    },
  },

  methods: {
    onMatchingApply() {
      if (!validateLogin()) {
        return;
      }
      this.$store.dispatch("QUERY_APPLT_MATCHING");
      this.$store.commit("OPEN_SNACKBAR", { text: "test" });
      // this.$router.push("/matching/apply");
    },

    onMatchingResult() {
      if (!validateLogin()) {
        return;
      }

      this.$router.push("/matching/result");
    },
  },
};
</script>

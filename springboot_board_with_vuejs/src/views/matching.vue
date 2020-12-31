<template>
  <div
    class="align-center d-flex flex-column fill-weidh fill-height pink lighten-1"
  >
    <v-col class="align-center justify-center ">
      <v-row class=" justify-center mt-5">
        <p class="text-center white--text text-h7">
          다음 소개팅까지
        </p>
      </v-row>

      <v-row class="mt-0 justify-center">
        <timer deadline="2021-01-11 00:00:00" />
      </v-row>

      <v-row class=" justify-center">
        <p class="text-center white--text text-h7">
          소개팅 매칭에 참여하실려면 다음을 클릭해주세요!
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
        </v-btn>
      </v-row>
      <v-row class="justify-center mt-7">
        <p class="text-center white--text text-h6">
          소개팅에 참여하셨다면 결과를 확인해보세요!
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
        </v-btn>
      </v-row>
    </v-col>
    <modal />
  </div>
</template>

<script>
import Modal from "../components/Modal.vue";
import Timer from "../components/Timer.vue";

export default {
  components: {
    Timer,
    Modal,
  },

  methods: {
    onMatchingApply() {
      this.$store.commit("OPEN_SNACKBAR", { text: "dd" });
      // this.$router.push("/matching/apply");
    },

    onMatchingResult() {
      this.$router.push("/matching/result");
    },
  },
};
</script>

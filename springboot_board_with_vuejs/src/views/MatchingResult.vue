<template>
  <div class="fill-height pink lighten-1">
    <v-container fill-height fluid class="align-center justify-center">
      <div v-if="isMatching === false" class="login text-center">
        <div>
          <p class="text-center white--text text-h6">
            아쉽네요!<br />
            매칭된 상대가 없습니다. <br />
            다음 소개팅을 신청해보세요!
          </p>
        </div>
        <v-btn
          outlined
          color="pink accent-3 "
          class="white mt-5"
          @click="onHomeClick"
        >
          <v-icon left> mdi-home </v-icon>
          홈으로
        </v-btn>
      </div>

      <div v-if="isMatching === true" class="login text-center">
        <div>
          <v-icon dark x-large class="mb-3"> mdi-heart </v-icon>
          <br />
          <p class="text-center white--text text-h6">
            축하드립니다!<br />
            매칭된 상대의 카카오톡 ID를 알려드릴께요 <br />
          </p>

          <div class="mt-10">
            <p class="text-center white--text text-h4">
              카카오톡 ID : {{ matchingResult.otherKakaoId }}
            </p>
          </div>
        </div>
        <v-btn
          outlined
          color="pink accent-3"
          class="white--text white align-center mt-5"
          @click="onHomeClick"
        >
          돌아가기
          <v-icon right> mdi-arrow-right </v-icon>
        </v-btn>
      </div>
    </v-container>
    <modal @pass="pass" />
  </div>
</template>

<script>
import Modal from "../components/Modal.vue";
export default {
  data() {
    return {
      isSuccessed: false,
      isFailed: true,
    };
  },
  components: { Modal },

  computed: {
    isMatching() {
      return this.$store.getters.GET_IS_MATCHING;
    },

    matchingResult() {
      return this.$store.getters.GET_MATCHING_RESULT;
    },
  },

  created() {
    this.$store.dispatch("QUERY_MATCHING_RESULT");
  },

  methods: {
    onHomeClick() {
      this.$router.back();
    },
    pass() {
      console.log("pass");
      this.$router.back();
    },
  },
};
</script>

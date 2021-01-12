<template>
  <div class="fill-height pink lighten-1">
    <v-container fill-height fluid class="align-center justify-center">
      <div v-if="isFailed === true" class="login text-center">
        <div>
          <p class="text-center white--text text-h6">
            아쉽네요!<br />
            매칭된 상대가 없습니다. <br />
            다음 소개팅을 신청해보세요!
          </p>
        </div>
        <v-btn color="white" class="mt-5" @click="onHomeClick">홈으로</v-btn>
      </div>

      <div v-if="isSuccessed" class="login text-center">
        <div>
          <p class="text-center white--text text-h6">
            축하드립니다!<br />
            매칭된 상대의 카카오톡 ID를 알려드릴께요 <br />
            다음 소개팅을 신청해보세요!
          </p>
        </div>
        <v-btn color="white" class="mt-5" @click="onHomeClick">홈으로</v-btn>
      </div>
    </v-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isSuccessed: false,
      isFailed: true,
    };
  },
  components: {},

  methods: {
    onHomeClick() {
      this.$router.push("/");
    },
  },
};
</script>

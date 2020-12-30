<template>
  <div class="fill-width fill-height">
    <v-form class="fill-width fill-height" ref="form">
      <v-tabs background-color="primary" color="white" grow>
        <v-tab v-for="item in items" :key="item">
          {{ item }}
        </v-tab>

        <v-tab-item v-for="item in items" :key="item.tab">
          <!-- ----------------------------------------------- -->
          <v-container fluid v-if="item === items[0]">
            <v-text-field
              :rules="emailRules"
              class="pl-3 pr-3"
              label="이메일"
              prepend-icon="mdi-email"
              required
              type="email"
              v-model="member.email"
            >
            </v-text-field>

            <v-text-field
              :rules="passwordRules"
              class="pl-3 pr-3"
              label="비밀번호"
              prepend-icon="mdi-lock"
              required
              type="password"
              v-model="member.password"
            >
            </v-text-field>

            <v-text-field
              :rules="validatePasswordRules"
              class="pl-3 pr-3"
              label="비밀번호 확인"
              prepend-icon="mdi-lock"
              required
              type="password"
              v-model="validatePassword"
            >
            </v-text-field>

            <v-text-field
              :rules="nameRules"
              class="pl-3 pr-3"
              label="닉네임"
              prepend-icon="mdi-account"
              required
              type="text"
              v-model="member.nickName"
            >
            </v-text-field>

            <v-text-field
              :rules="nameRules"
              class="pl-3 pr-3"
              label="카카오톡 ID"
              prepend-icon="mdi-account"
              required
              type="text"
              v-model="member.kakaoId"
            >
            </v-text-field>
          </v-container>
          <!-- ----------------------------------------------- -->
          <v-container fluid class="py-5" v-if="item === items[1]">
            <v-combobox
              :rules="typeRules"
              required
              class="pl-3 pr-3"
              outlined
              persistent-hint
              hint="본인의 거주지역을 선택하세요."
              label="지역"
              :items="locationCategory"
              @change="setDetailLocationCategory"
            ></v-combobox>
            <v-combobox
              :rules="typeRules"
              required
              class="pl-3 pr-3"
              outlined
              persistent-hint
              hint="본인의 체형을 선택하세요."
              label="체형"
              :items="bodyType"
              @change="setDetailBodyType"
            ></v-combobox>
            <v-combobox
              :rules="typeRules"
              required
              class="pl-3 pr-3"
              outlined
              persistent-hint
              hint="본인의 신장을 선택해주세요."
              label="신장"
              :items="tallType"
              @change="setDetailTallType"
            ></v-combobox>
            {{ member.detailProfiles.locationCategory }}
            {{ member.detailProfiles.bodyType }}
            {{ member.detailProfiles.tallType }}
          </v-container>

          <!-- ----------------------------------------------- -->
          <v-container fluid class="py-5" v-if="item === items[2]">
            <v-combobox
              :rules="typeRules"
              required
              class="pl-3 pr-3"
              outlined
              persistent-hint
              hint="희망하는 이성의 체형을 선택하세요."
              label="체형"
              :items="bodyType"
              @change="setDreamBodyType"
            ></v-combobox>
            <v-combobox
              :rules="typeRules"
              required
              class="pl-3 pr-3"
              outlined
              persistent-hint
              hint="흐망하는 이성의 신장을 선택하세요."
              label="신장"
              :items="tallType"
              @change="setDreamTallType"
            ></v-combobox>
            {{ member.dreamProfiles.locationCategory }}
            {{ member.dreamProfiles.bodyType }}
            {{ member.dreamProfiles.tallType }}
            <v-btn
              depressed
              block
              class="my-auto"
              color="primary"
              @click="requestJoin"
            >
              회원가입
            </v-btn>
          </v-container>
        </v-tab-item>
      </v-tabs>
    </v-form>
  </div>
</template>

<script>
export default {
  name: "Resister",
  data() {
    return {
      currentId: null,
      items: ["1. 기본정보 입력", "2. 프로필 입력", "3. 이상형 입력"],
      locationCategory: [
        { value: "SEOUL", text: "서울" },
        { value: "BUSAN", text: "부산" },
        { value: "DAEGU", text: "대구" },
        { value: "INCHUN", text: "인천" },
        { value: "GWANGJU", text: "광주" },
        { value: "DAEJUN", text: "대구" },
        { value: "ULSAN", text: "울산" },
        { value: "SEJONG", text: "세종" },
        { value: "GYEONGGI", text: "경기" },
        { value: "GANGWAON", text: "강원" },
        { value: "CHUNGBUK", text: "충북" },
        { value: "CHUNGNAM", text: "충남" },
        { value: "JUNBUK", text: "전북" },
        { value: "JUNNAM", text: "전남" },
        { value: "GYEONGNAM", text: "경남" },
        { value: "GYEONGBUK", text: "경북" },
        { value: "JEJU", text: "제주" },
      ],

      tallType: [
        { value: "SMALL", text: "작음" },
        { value: "LITTEL_SMLL", text: "살짝 작음" },
        { value: "NORMAL", text: "평균" },
        { value: "LITTLE_TALL", text: "살짝 큼" },
        { value: "TALL", text: "큼" },
      ],

      bodyType: [
        { value: "SKINNY", text: "날씬" },
        { value: "SLIM", text: "슬림" },
        { value: "NORMAL", text: "보통" },
        { value: "LITTLE_CHUBBY", text: "약간 통통" },
        { value: "CHUBBY", text: "통통" },
      ],

      dialog: false,
      member: {
        email: "",
        password: "",
        nickName: "",
        kakaoId: "",
        detailProfiles: { tallType: "", bodyType: "", locationCategory: "" },
        dreamProfiles: { tallType: "", bodyType: "", locationCategory: "" },
      },
      validatePassword: "",
      validatePasswordRules: [
        (v) => v === this.member.password || "비밀번호가 맞지 않습니다..",
      ],
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
    nameRules() {
      return this.$store.state.common.nameRules;
    },
    typeRules() {
      return this.$store.state.common.typeRules;
    },
  },
  components: {},
  methods: {
    setDetailLocationCategory(a) {
      this.setDreamLocationCategory(a);
      this.member.detailProfiles.locationCategory = a.value;
    },
    setDetailTallType(a) {
      this.member.detailProfiles.tallType = a.value;
    },
    setDetailBodyType(a) {
      this.member.detailProfiles.bodyType = a.value;
    },
    setDreamLocationCategory(a) {
      this.member.dreamProfiles.locationCategory = a.value;
    },
    setDreamTallType(a) {
      this.member.dreamProfiles.tallType = a.value;
    },
    setDreamBodyType(a) {
      this.member.dreamProfiles.bodyType = a.value;
    },
    requestJoin() {
      if (this.$refs.form.validate()) {
        this.$store.dispatch("REQUEST_JOIN", this.member);
      } else {
        alert("dd");
      }
    },
  },
};
</script>

<style scoped>
@media (min-width: 700px) {
  .register {
    width: 400px !important;
  }
}

.register {
  background-color: white;
  border-radius: 8px;
  width: 100%;
  border: white 1px solid;
  padding: 20px;
}
</style>

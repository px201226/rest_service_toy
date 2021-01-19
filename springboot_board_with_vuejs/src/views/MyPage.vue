<template>
  <div class="about">
    <v-card class="pa-4" outlined>
      <v-row row>
        <v-col class="flex-grow-0 flex-shrink-0">
          <v-avatar size="50px">
            <img src="@/assets/no_profile.gif" alt="John" />
          </v-avatar>
        </v-col>
        <v-col>
          <v-row>
            <span class="">{{ nickName }}</span>
          </v-row>
          <v-row>
            <span class="text-caption">카카오 ID</span>
          </v-row>
        </v-col>
      </v-row>

      <v-card-actions>
        <v-btn class="pink" dark outlined text>
          프로필 수정
        </v-btn>
        <v-btn class="pink" dark outlined text>
          로그아웃
        </v-btn>
      </v-card-actions>
    </v-card>
    <v-flex xs12>
      <v-data-table
        @click:row="clickItem"
        :headers="headers"
        :items="posts"
        :items-per-page="5"
        class="elevation-1"
      >
      </v-data-table>
      <v-data-table
        @click:row="clickItem"
        :headers="headers"
        :items="getPostList"
        :items-per-page="5"
        class="elevation-1"
      >
      </v-data-table>
    </v-flex>
  </div>
</template>

<script>
export default {
  name: "MyPage",

  data() {
    return {
      nickName: this.$store.getters.GET_USER.nickName,

      kakaoId: this.$store.getters.GET_USER.kakaoId,
      headers: [
        {
          text: "내용",
          value: "content",
          sortable: false,
        },
        {
          text: "작성자",
          value: "userNickName",
          sortable: false,
        },

        {
          text: "수정일",
          value: "modifyDate",
          sortable: false,
        },
        {
          text: "좋아요",
          value: "likes",
          sortable: false,
        },
      ],
      posts: [],
    };
  },

  computed: {
    getPostList() {},
  },
  created() {
    this.$store.dispatch("QUERY_GET_USER_POST_LIST").then((res) => {
      this.posts = res;
      console.log(this.posts);
    });
  },
  methods: {
    clickItem() {},
  },
};
</script>

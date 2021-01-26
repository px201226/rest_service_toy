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

    <div class="my-5">
      <p class="ma-3 text-h6 font-weight-black">내가 작성한 글</p>
      <v-divider class="pink" />
      <v-card class="py-3 mb-10">
        <v-data-table
          :headers="postHeaders"
          :items="posts"
          :items-per-page="5"
          mobile-breakpoint="0"
        >
          <template v-slot:item="{ item }">
            <tr @click="clickPostItem(item)">
              <td class="truncate">{{ item.content }}</td>
              <td>{{ item.comments }}</td>
              <td>{{ item.likes }}</td>
              <td>{{ item.modifyDate }}</td>
            </tr>
          </template>
        </v-data-table>
      </v-card>

      <p class="ma-3 text-h6 font-weight-black">내가 작성한 댓글</p>
      <v-divider class="pink" />
      <v-card class="py-3 mb-10">
        <v-data-table
          :headers="commentheaders"
          :items="comments"
          :items-per-page="5"
          mobile-breakpoint="0"
        >
          <template v-slot:item="{ item }">
            <tr @click="clickCommentItem(item)">
              <td class="truncate">{{ item.content }}</td>
              <td>{{ item.modifyDate }}</td>
            </tr>
          </template>
        </v-data-table>
      </v-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "MyPage",

  data() {
    return {
      nickName: this.$store.getters.GET_USER.nickName,

      kakaoId: this.$store.getters.GET_USER.kakaoId,

      postHeaders: [
        {
          text: "내용",
          value: "content",
          sortable: false,
          width: "50%",
        },
        {
          text: "댓글",
          value: "comments",
          sortable: false,
          width: "10%",
        },
        {
          text: "좋아요",
          value: "likes",
          sortable: false,
          width: "12%",
        },
        {
          text: "수정일",
          value: "modifyDate",
          sortable: false,
          width: "30%",
        },
      ],

      commentheaders: [
        {
          text: "내용",
          value: "content",
          sortable: false,
          width: "70%",
        },

        {
          text: "수정일",
          value: "modifyDate",
          sortable: false,
          width: "30%",
        },
      ],

      posts: [],
      comments: [],
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

    this.$store.dispatch("QUERY_GET_USER_COMMENT_LIST").then((res) => {
      this.comments = res;
      console.log(this.comments);
    });
  },
  methods: {
    clickPostItem(item) {
      this.$router.push("/posts/" + item.id);
    },
    clickCommentItem(item) {
      this.$router.push("/posts/" + item.postId);
    },
  },
};
</script>
<style>
.truncate {
  max-width: 1px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>

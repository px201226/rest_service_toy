<template>
<v-form>
    <v-container>
        <v-row>
            <v-text-field :counter="50" label="제목" name="title" required v-model="post.title" maxlength="50"></v-text-field>
        </v-row>
        <v-row>
            <v-textarea filled name="content" hint="내용을 입력해주세요." v-model="post.content" :counter="1000" maxlength="1000"> </v-textarea>
            </v-row>
        <v-row>
            <v-btn block color="primary" @click="updatePost">수정</v-btn>
        </v-row>
    </v-container>
</v-form>
</template>

<script>

export default {
    name: 'ArticleUpdateView',
    data() {
        return {
            post: this.$store.getters.GET_POST_DETAIL
        }
    },
    methods: {

        updatePost(title, content) {
            var id = this.$store.getters.GET_POST_DETAIL
            var postid = this.$store.getters.GET_POST_DETAIL.id;
            var requestPostUpdateDto = {
                     id: postid,
                    title: this.post.title,
                    content: this.post.content,
                    author: this.$store.getters.GET_USER_NAME,
                }; 
                
                this.$store.dispatch('QUERY_UPDATE_POST',requestPostUpdateDto);
                
            }
    },
  computed: {
    getPostDetail() {
      return this.$store.getters.GET_POST_DETAIL;
    },
  },
}
</script>

</script>

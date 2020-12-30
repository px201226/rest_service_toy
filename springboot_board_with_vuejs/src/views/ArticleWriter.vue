<template>
<v-form>
    <v-container>
        <v-row>
            <v-text-field :counter="50" label="제목" name="title" required v-model="title" maxlength="50"></v-text-field>
        </v-row>
        <v-row>
            <v-textarea filled name="context" hint="내용을 입력해주세요." v-model="content" :counter="1000" maxlength="1000"></v-textarea>
        </v-row>
        <v-row>
            <v-btn block color="primary" @click="onWriteClick">등록</v-btn>
        </v-row>
    </v-container>
</v-form>
</template>

<script>

export default {
    name: 'ArticleUpdateView',
    methods: {
        onWriteClick() {
            this.savePost(this.title, this.content)
		},
		
        savePost(title, content) {

            var requestPostDto = {
                title: title,
                content: content,
                author: this.$store.getters.GET_USER_NAME,
				redirectUrl: "/",
				onSuccessed: function(){
						alert('글등록이 완료되었습니다.')
				}
			};
			
            this.$store.dispatch('QUERY_WRITE_POST',requestPostDto);
			
			}
    },
    data() {
        return {
            title: '',
			content: '',
        }
    }
}
</script>

</script>

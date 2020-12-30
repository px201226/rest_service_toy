/* ## BoardList.vue 내용 */
<template>
<v-container>
    <v-data-table @click:row="clickItem"  :headers="headers" :items="getPostList" :items-per-page="5" class="elevation-1" >
    </v-data-table>
    <v-col class="text-right">
        <v-btn color="primary" @click="routePostArticle" >게시글 작성</v-btn>
    </v-col>
	
</v-container>
</template>

<script>

export default {
	name: 'BoardList',
	components:{
	},

    data() {
        return {
            headers: [{
                text: '번호',
                align: 'left',
                sortable: false,
                value: 'id',
            }, {
                text: '제목',
                value: 'title',
                sortable: false,
            }, {
                text: '작성자',
                value: 'author',
                sortable: false,
            }, {
                text: '수정일',
                value: 'modifiedDate',
                sortable: false,
            }],
            posts: this.$store.state.postsList

        }
	},
	created(){
		this.queryGetPostList();
	},
    computed: {
        getPostList() {
            return this.$store.getters.GET_POST_LIST;
        },
    },
    methods: {
		clickItem(item){
			console.log(item.id)
			this.$router.push("/posts/" + item.id)
		},
        queryGetPostList() {
			this.$store.dispatch('QUERY_POST_LIST');
		},
		routePostArticle(){
			this.$router.push("/save")
		}
    }
};
</script>

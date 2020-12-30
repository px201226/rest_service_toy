<template>
  <v-simple-table>
    <template v-slot:default>
      <colgroup>
        <col width="6%" />
        <col width="*" />
        <col width="10%" />
        <col width="15%" />
      </colgroup>
      <thead>
        {{
          getPostList
        }}
        <tr>
          <th class="text-center">
            번호
          </th>
          <th class="text-center">
            제목
          </th>
          <th class="text-center">
            작성자
          </th>
          <th class="text-center">
            날짜
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in getPostList" :key="item.title">
          <td>{{ item.calories }}</td>
          <td>{{ item.name }}</td>

          <td>{{ item.name }}</td>
          <td>{{ item.calories }}</td>
        </tr>
      </tbody>
    </template>
  </v-simple-table>
</template>

<script>
export default {
    data() {
        return {

        }
    },

    computed: {
        getPostList() {
			console.log("getPostList")
            console.log(this.$store.getters.getPostList);
            return this.$store.getters.getPostList;
        },
    },
    methods: {
        queryGetPostList() {
			console.log("queryGetPostLdssist")
            console.log(this.$store.dispatch('QUERY_GET_POSTLIST');
        }
    }
};
</script>

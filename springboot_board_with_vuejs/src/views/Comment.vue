<template>
  <v-row no-gutters>
    <v-avatar size="25px" class="ml-1 mt-2">
      <img
        src="https://lh5.googleusercontent.com/-2QXdr8wesbM/AAAAAAAAAAI/AAAAAAAAAAA/AMZuucnb024lRD57ypepPkcphVu_7loiBQ/s96-c/photo.jpg"
        alt="John"
      />
    </v-avatar>

    <v-col class="pa-2">
      <v-card color="px-6 py-3 grey lighten-4" elevation="1">
        <v-row>
          <p class="font-weight-regular ">
            {{ nickName }}
          </p>
          <v-btn class="ml-2" x-small icon @click="onEditButton">
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
          <v-btn class="" x-small icon @click="onDeleteButtonClick">
            <v-icon>mdi-delete</v-icon>
          </v-btn>

          <div class="ml-auto">
            <p class="text-right caption">
              {{ date }}
            </p>
          </div>
        </v-row>
        <v-row>
          <div v-if="!isEdit" v-html="content" class="body-2"></div>
          <v-row class="mx-1" v-if="isEdit">
            <v-textarea
              class="caption"
              v-model="editContent"
              auto-grow
              outlined
            ></v-textarea>
          </v-row>
        </v-row>
        <v-row>
          <v-btn
            block
            v-if="isEdit"
            color="primary"
            dark
            @click="onUpdateButtonClick"
          >
            <v-icon> mdi-check </v-icon>
            수정
          </v-btn>
        </v-row>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: "Comment",
  props: ["id", "nickName", "date", "content", "isWriter"],

  data() {
    return {
      isEdit: false,
      editContent: this.content,
    };
  },

  methods: {
    onEditButton() {
      this.isEdit = !this.isEdit;
    },
    onUpdateButtonClick() {
      this.isEdit = !this.isEdit;
    },

    onDeleteButtonClick() {},
  },
};
</script>

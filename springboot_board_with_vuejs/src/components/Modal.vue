<template>
  <v-dialog
    v-model="modal.open"
    max-width="290"
    persistent
    :retain-focus="false"
  >
    <v-card>
      <v-card-title> {{ modal.title }} </v-card-title>
      <hr />
      <v-card-text class="mt-3">
        {{ modal.content }}
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
          @click="submit"
          class="white--text mb-3 mr-3"
          color="primary"
          v-if="modal.option2 !== null"
        >
          {{ modal.option2 }}
        </v-btn>
        <v-btn
          @click="modalOption"
          class="white--text mb-3 mr-3"
          color="primary"
          >{{ modal.option1 }}</v-btn
        >
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "Modal",
  computed: {
    modal() {
      return this.$store.state.common.modal;
    },
  },
  methods: {
    modalOption() {
      if (this.modal.option1 === "닫기") {
        this.$store.commit("CLOSE_MODAL");
      } else {
        this.$emit("pass", this.modal.data);
      }
    },
    submit() {
      this.$emit("pass", this.modal.data);
      this.$store.commit("CLOSE_MODAL");
    },
  },
  destroyed() {
    this.$store.commit("CLOSE_MODAL");
  },
};
</script>

<style scoped></style>

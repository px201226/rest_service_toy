<template>
  <v-snackbar
    :bottom="snackbar.location === 'bottom'"
    :color="snackbar.color"
    :timeout="timeout"
    :top="snackbar.location === 'top'"
    v-model="snackbar.open"
  >
    <span class="white--text">{{ snackbar.text }}</span>

    <template v-slot:action="{ attrs }">
      <v-btn @click="snackbar.open = false" color="white--text" text>
        닫기
      </v-btn>
    </template>
  </v-snackbar>
</template>

<script>
export default {
  name: "SnackBar",
  data() {
    return {
      timeout: 5000,
    };
  },
  computed: {
    snackbar() {
      return this.$store.state.common.snackbar;
    },
  },
};
</script>

<style scoped></style>

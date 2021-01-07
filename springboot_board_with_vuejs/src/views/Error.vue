<template>
  <v-container class="fill-height fill-width jusity-center" fluid>
    <v-row align="center" class="fill-height" justify="center">
      <div class="login text-center elevation-15 pa-10">
        <v-card-title>
          <v-icon large left color="primary">
            mdi-alert-circle
          </v-icon>
          <span class="title font-weight-light">404 NOT FOUND</span>
        </v-card-title>

        <v-card-text class="headline font-weight-bold">
          {{ errorMessage }}
        </v-card-text>
      </div>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "Error",
  computed: {
    errorMessage() {
      return this.$store.state.common.errorMessage;
    },
  },
};
</script>

<style scoped></style>

<template>
  <div class="">
    <div v-if="currentTime">
      <div class="align-center d-flex flex-column">
        <p class="white--text digit segment">
          {{ hours | formatTime }}:{{ minutes | formatTime }}:{{
            seconds | formatTime
          }}
        </p>
      </div>
    </div>
    <div class="text-center" v-if="!currentTime">
      <p class="white--text digit segment">
        00:00:00
      </p>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    deadline: {
      type: String,
      required: true,
    },
    speed: {
      type: Number,
      default: 1000,
    },
  },
  data() {
    return {
      currentTime: Date.parse(this.deadline) - Date.parse(new Date()),
    };
  },
  mounted() {
    setTimeout(this.countdown, 1000);
  },
  computed: {
    seconds() {
      return Math.floor((this.currentTime / 1000) % 60);
    },
    minutes() {
      return Math.floor((this.currentTime / 1000 / 60) % 60);
    },
    hours() {
      return Math.floor(this.currentTime / (1000 * 60 * 60));
    },
  },
  filters: {
    formatTime(value) {
      if (value < 10) {
        return "0" + value;
      }
      return value;
    },
  },
  methods: {
    countdown() {
      this.currentTime = Date.parse(this.deadline) - Date.parse(new Date());
      if (this.currentTime > 0) {
        setTimeout(this.countdown, this.speed);
      } else {
        this.currentTime = null;
      }
    },
  },
};
</script>

<style scoped>
.digit {
  font-size: 76px;
  font-weight: 120;
  text-align: center;
}

@font-face {
  src: url("../assets/7segment.ttf");
  font-family: "7segment";
}

.segment {
  font-family: "7segment";
}
</style>

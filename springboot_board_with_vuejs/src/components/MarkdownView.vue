<template>
  <div v-html="convertMarktoHtml" />
</template>

<script>
import { eventBus } from "../main.js";

const marked = require("marked");
const axios = require("axios");
const tags = [
  "h1",
  "h2",
  "h3",
  "h4",
  "h5",
  "h6",
  "em",
  "strong",
  "del",
  "u",
  "ul",
  "li",
  "pre",
  "ol",
  "a",
  "img",
  "table",
  "blockquote",
  "p",
  "hr",
  "li",
];
export default {
  props: ["markdownstring"],
  data() {
    return {
      post: "",
    };
  },

  created() {
    eventBus.$on("FETCH", (post) => {
      this.post = post;
    });
  },

  computed: {
    convertMarktoHtml: function() {
      if (
        typeof this.post == "undefined" ||
        this.post == null ||
        this.post == ""
      )
        return;

      var post = this.post;
      var renderer = new marked.Renderer();
      this.setCustomRenderer(renderer);

      marked.setOptions({
        renderer: renderer,
        gfm: true,
        headerIds: false,
        tables: true,
        breaks: true,
        pedantic: false,
        smartLists: true,
        smartypants: false,
      });

      var markedHTML = marked(this.post.content);
      markedHTML = this.setPrefixHTMLClass(markedHTML, "my-3");
      return markedHTML;
    },
  },

  methods: {
    fetchMarkdown() {
      this.markdown = this.markdownstring;
    },
    setCustomRenderer(renderer) {
      // h1,h2 태그 커스텀
      renderer.heading = function(text, level) {
        if (level <= 2) {
          return (
            "<h" +
            level +
            ' class="mt-10">' +
            text +
            "</h" +
            level +
            '><hr color="#CFD8DC" size="4" class="mt-3 mb-5">'
          );
        } else {
          return "<h" + level + ">" + text + "</h" + level + ">";
        }
      };

      // hr 태그 커스텀
      renderer.hr = function() {
        return '<hr color="#ECEFF1" size="7">';
      };

      // li 태그 커스텀
      renderer.listitem = function(text, task, checked) {
        return '<li class="none listyle my-3">' + text + "</li>";
      };

      renderer.codespan = function(code) {
        return '<code style="color:red">' + code + "</code>";
      };

      renderer.code = function(code) {
        return "<pre><b>" + code + "</b></pre>";
      };

      renderer.image = function(href, title, text) {
        return '<img src="' + href + '" alt="">';
      };
    },

    setPrefixHTMLClass(markedHTML, prefix) {
      let changedHTML = markedHTML;
      if (prefix != "") {
        for (let tag of tags) {
          let classTempName;
          let regex = new RegExp("<" + tag + ">", "g"); // 시작 태그 찾기
          changedHTML = changedHTML.replace(
            regex,
            `<${tag} class="${prefix} ">`
          );
        }
      }
      return changedHTML; // changedText라는 새로운 값을 반환
    },
  },
};
</script>

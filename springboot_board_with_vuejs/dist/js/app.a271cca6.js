(function(t){function e(e){for(var r,s,i=e[0],c=e[1],u=e[2],p=0,d=[];p<i.length;p++)s=i[p],Object.prototype.hasOwnProperty.call(a,s)&&a[s]&&d.push(a[s][0]),a[s]=0;for(r in c)Object.prototype.hasOwnProperty.call(c,r)&&(t[r]=c[r]);l&&l(e);while(d.length)d.shift()();return o.push.apply(o,u||[]),n()}function n(){for(var t,e=0;e<o.length;e++){for(var n=o[e],r=!0,i=1;i<n.length;i++){var c=n[i];0!==a[c]&&(r=!1)}r&&(o.splice(e--,1),t=s(s.s=n[0]))}return t}var r={},a={app:0},o=[];function s(e){if(r[e])return r[e].exports;var n=r[e]={i:e,l:!1,exports:{}};return t[e].call(n.exports,n,n.exports,s),n.l=!0,n.exports}s.m=t,s.c=r,s.d=function(t,e,n){s.o(t,e)||Object.defineProperty(t,e,{enumerable:!0,get:n})},s.r=function(t){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(t,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(t,"__esModule",{value:!0})},s.t=function(t,e){if(1&e&&(t=s(t)),8&e)return t;if(4&e&&"object"===typeof t&&t&&t.__esModule)return t;var n=Object.create(null);if(s.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:t}),2&e&&"string"!=typeof t)for(var r in t)s.d(n,r,function(e){return t[e]}.bind(null,r));return n},s.n=function(t){var e=t&&t.__esModule?function(){return t["default"]}:function(){return t};return s.d(e,"a",e),e},s.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},s.p="/";var i=window["webpackJsonp"]=window["webpackJsonp"]||[],c=i.push.bind(i);i.push=e,i=i.slice();for(var u=0;u<i.length;u++)e(i[u]);var l=c;o.push([0,"chunk-vendors"]),n()})({0:function(t,e,n){t.exports=n("56d7")},"56d7":function(t,e,n){"use strict";n.r(e);n("e260"),n("e6cf"),n("cca6"),n("a79d");var r=n("2b0e"),a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-app",[n("v-app-bar",{attrs:{app:"",color:"blue darken-2",dark:""}},[n("div",{staticClass:"d-flex align-center"},[n("v-list-item",[t._v(" springboot ")])],1),n("v-spacer"),n("v-btn",{attrs:{href:"https://github.com/vuetifyjs/vuetify/releases/latest",target:"_blank",text:""}},[n("a",{staticClass:"mr-4",attrs:{href:"/oauth2/authorization/google"}},[t._v("Google로 로그인")]),n("v-icon",[t._v("mdi-login")])],1)],1),n("v-main",[n("v-container",[n("router-view")],1)],1)],1)},o=[],s=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-container",[n("v-data-table",{staticClass:"elevation-1",attrs:{headers:t.headers,items:t.posts,"items-per-page":5}}),t._v(" "+t._s(t.getPostList)+" "),n("v-col",{staticClass:"text-right"},[n("v-btn",{attrs:{color:"primary"},on:{click:t.queryGetPostList}},[t._v("게시글 작성")])],1)],1)},i=[],c=n("bc3a"),u=n.n(c),l={name:"BoardList",data:function(){return{headers:[{text:"번호",align:"left",sortable:!1,value:"id"},{text:"제목",value:"title",sortable:!1},{text:"작성자",value:"author",sortable:!1},{text:"수정일",value:"modifiedDate",sortable:!1}],posts:[{id:1,title:"abc",author:"이기수",modifiedDate:"2012-12-12"},{id:2,title:"abc",author:"이기수",modifiedDate:"2012-12-12"}]}},computed:{getPostList:function(){return console.log("getPostList"),console.log(this.$store.getters.getPostList),this.$store.getters.getPostList}},methods:{queryGetPostList:function(){console.log("queryGetPostList"),this.$store.dispatch("QUERY_POST_LIST")}}},p=l,d=n("2877"),f=n("6544"),v=n.n(f),m=n("8336"),h=n("62ad"),g=n("a523"),b=n("8fea"),_=Object(d["a"])(p,s,i,!1,null,null,null),x=_.exports;v()(_,{VBtn:m["a"],VCol:h["a"],VContainer:g["a"],VDataTable:b["a"]});var y={name:"App",components:{Home:x},data:function(){return{}}},P=y,w=n("7496"),O=n("40dc"),V=n("132d"),S=n("da13"),j=n("f6c4"),T=n("2fa4"),L=Object(d["a"])(P,a,o,!1,null,null,null),k=L.exports;v()(L,{VApp:w["a"],VAppBar:O["a"],VBtn:m["a"],VContainer:g["a"],VIcon:V["a"],VListItem:S["a"],VMain:j["a"],VSpacer:T["a"]});var C=n("8c4f"),E=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",[n("h1",{staticClass:"d-flex justify-center mt-3"},[t._v(t._s(t.getPost.title))]),n("p",{staticClass:"d-flex justify-center grey--text text--lighten-1"},[t._v(" "+t._s(t.getPost.author)+" / "+t._s(t.getPost.modifiedDate)+" ")]),n("v-divider"),n("div",{staticClass:"my-5"},[t._v(t._s(t.getPost.content))]),n("v-divider"),n("div",{staticClass:"d-flex mt-6"},[n("v-spacer"),n("v-btn",{staticClass:"d-flex justify-end ",on:{click:t.onChangedMsg}},[n("v-icon",{attrs:{left:""}},[t._v(" mdi-pencil ")]),t._v(" 글 수정 ")],1),n("v-btn",{staticClass:"d-flex justify-end"},[n("v-icon",{attrs:{left:""}},[t._v(" mdi-delete ")]),t._v(" 삭제 ")],1)],1)],1)},R=[],D={data:function(){return{id:this.$route.params.id}},computed:{getPost:function(){return console.log(this.$store.getters.getPost),this.$store.getters.getPost}},methods:{onChangedMsg:function(){console.log("dd"),this.$store.dispatch("QUERY_READ_POST2",1),this.$store.di}}},$=D,A=n("ce7e"),I=Object(d["a"])($,E,R,!1,null,null,null),U=I.exports;v()(I,{VBtn:m["a"],VDivider:A["a"],VIcon:V["a"],VSpacer:T["a"]});var M=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-form",[n("v-container",[n("v-row",[n("v-text-field",{attrs:{counter:50,label:"제목",name:"title",required:"",maxlength:"50"},model:{value:t.title,callback:function(e){t.title=e},expression:"title"}})],1),n("v-row",[n("v-textarea",{attrs:{filled:"",name:"context",hint:"내용을 입력해주세요.",counter:1e3,maxlength:"1000"},model:{value:t.context,callback:function(e){t.context=e},expression:"context"}})],1),n("v-row",[n("v-btn",{attrs:{block:"",color:"primary"},on:{click:t.writeClick}},[t._v("수정")])],1)],1)],1)},B=[],G={name:"ArticleUpdateView",methods:{},data:function(){return{title:"",context:"",uptDt:"",regDt:""}}},Q=G,Y=n("4bd4"),q=n("0fd9"),z=n("8654"),F=n("a844"),H=Object(d["a"])(Q,M,B,!1,null,null,null),J=H.exports;v()(H,{VBtn:m["a"],VContainer:g["a"],VForm:Y["a"],VRow:q["a"],VTextField:z["a"],VTextarea:F["a"]}),r["a"].use(C["a"]);var K=[{path:"/",name:"Home",component:x},{path:"/posts/:id",name:"ArticleDetailView",component:U},{path:"/update",name:"ArticleUpdateView",component:J}],N=new C["a"]({mode:"history",base:"/",routes:K}),W=N,X=n("2f62"),Z=(n("d3b7"),n("96cf"),n("1da1")),tt=u.a.create({baseURL:"http://ec2-13-125-170-210.ap-northeast-2.compute.amazonaws.com"}),et=tt;function nt(){return rt.apply(this,arguments)}function rt(){return rt=Object(Z["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.abrupt("return",et.get("/api/v1/posts"));case 1:case"end":return t.stop()}}),t)}))),rt.apply(this,arguments)}function at(t){return ot.apply(this,arguments)}function ot(){return ot=Object(Z["a"])(regeneratorRuntime.mark((function t(e){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.abrupt("return",et.get("/api/v1/posts/"+e));case 1:case"end":return t.stop()}}),t)}))),ot.apply(this,arguments)}var st={post:{title:"제목",content:"빈 내용",author:"작성자",modifiedDate:"일자"},postList:[]},it={getPost:function(t){return t.post},GET_POST_LIST:function(t){return t.postList}},ct={setPost:function(t,e){t.post=e},SET_POST_LIST:function(t,e){t.postList=e}},ut={QUERY_READ_POST:function(t,e){t.state;var n=t.commit,r=e.post;n("setPost",r)},QUERY_READ_POST2:function(t,e){t.state;var n=t.commit;post=at(e),n("setPost",post)},QUERY_POST_LIST:function(t){return Object(Z["a"])(regeneratorRuntime.mark((function e(){var n;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.prev=0,e.next=3,nt();case 3:return n=e.sent,t.commit("SET_POST_LIST",n.data),e.abrupt("return",n.data);case 8:return e.prev=8,e.t0=e["catch"](0),e.abrupt("return",Promise.reject(e.t0));case 11:return e.prev=11,console.log("End"),e.finish(11);case 14:case"end":return e.stop()}}),e,null,[[0,8,11,14]])})))()}},lt={state:st,mutations:ct,actions:ut,getters:it};r["a"].use(X["a"]);var pt=new X["a"].Store({state:{},mutations:{},actions:{},modules:{post:lt}}),dt=n("f309");r["a"].use(dt["a"]);var ft=new dt["a"]({theme:{dark:!0}});r["a"].config.productionTip=!1,new r["a"]({router:W,store:pt,vuetify:ft,render:function(t){return t(k)}}).$mount("#app")}});
//# sourceMappingURL=app.a271cca6.js.map
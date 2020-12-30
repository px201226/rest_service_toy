import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import ArticleDetailView from '../views/ArticleDetailView.vue'
import ArticleUpdateView from '../views/ArticleUpdateView.vue'
import ArticleWriter from '../views/ArticleWriter.vue'

import PostView from "../views/PostView.vue";
import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
import Matching from "../views/matching.vue";
import MatchingResult from "../views/MatchingResult.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/posts/:id',
    name: 'ArticleDetailView',
    component: ArticleDetailView
  },
  {
    path: '/update',
    name: 'ArticleUpdateView',
    component: ArticleUpdateView
  },
  {
	  path: '/save',
	  name: 'ArticleWriterView',
	  component: ArticleWriter
  },
  {
    path: '/matching',
    name: "Matching",
    component: Matching
  },
  {
    path: '/matching/apply',
    name: "Register",
    component: Register
  },
  {
    path: '/matching/result',
    name: "MatchingResult",
    component: MatchingResult
  },
  {
    path: '/register',
    name: "Register",
    component: Register
  },
  {
    path: '/login',
    name: "Login",
    component: Login
  },
 

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

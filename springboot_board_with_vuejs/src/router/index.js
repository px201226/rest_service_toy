import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component:() => import('../views/Home.vue')
  },
  {
    path: '/posts/:id',
    name: 'PostView',
    component: () => import('../views/PostView.vue')
  },

  // {
  //   path: '/update',
  //   name: 'ArticleUpdateView',
  //   component: ArticleUpdateView
  // },
  // {
	//   path: '/save',
	//   name: 'ArticleWriterView',
	//   component: ArticleWriter
  // },
  {
    path: '/matching',
    name: "Matching",
    component: () => import('../views/matching.vue')
  },
  {
    path: '/matching/apply',
    name: "Register",
    component:  () => import('../views/Register.vue')
  },
  {
    path: '/matching/result',
    name: "MatchingResult",
    component:  () => import('../views/MatchingResult.vue')
  },
  {
    path: '/register',
    name: "Register",
    component: () => import('../views/Register.vue')
  },
  {
    path: '/login',
    name: "Login",
    component: () => import('../views/Login.vue')
  },
 

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

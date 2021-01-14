import Vue from 'vue'
import VueRouter from 'vue-router'
import context from '../store';

Vue.use(VueRouter)

const validateLogin =  (to, from, next) => {
  console.log("forbiden");
  if (!context.getters.GET_IS_LOGIN) {
    context.commit("OPEN_SNACKBAR", {
      text: "로그인이 필요합니다!",
      color: "error",
      location: "top",
    });
    console.log("forbiden");
    next("forbidden")
  }else{
    console.log("god");
  next("forbidden")
  }
}

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
    component:  () => import('../views/MatchingResult.vue'),
    beforeEnter: (to, from, next) => validateLogin(to, from, next),
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
 
  {
    path: '/error',
    name: 'error`',
    component: () => import('../views/Error'),
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
  scrollBehavior (to, from, savedPosition) {
    // 원하는 위치로 돌아가기
    
    console.log("scroll")
    return { x: 0, y: 0 }
  }
})

export default router

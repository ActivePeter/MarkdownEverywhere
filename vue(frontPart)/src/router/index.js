import Vue from 'vue'
import VueRouter from 'vue-router'
import context from "../main"

Vue.use(VueRouter);

const Home = () => import('@/views/home/Home');
const routes = [
];

const router = new VueRouter({
  linkActiveClass: 'is-active',
  mode: 'history',
  base: "",
  routes
});

export default router

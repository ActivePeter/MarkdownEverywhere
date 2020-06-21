import Vue from 'vue'
import VueRouter from 'vue-router'
import context from "../main"

Vue.use(VueRouter);

const Home = () => import('@/views/home/Home');
const routes = [
  { path: '/:id'}
];

const router = new VueRouter({
  linkActiveClass: 'is-active',
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

export default router

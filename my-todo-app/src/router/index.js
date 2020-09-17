import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    props: true,
    component: Home,
  },
  {
    path: "/board/:slug",
    name: "board",
    props: true,
    component: () =>
      import(/* webpackChunkName: "board" */ "../views/Board.vue"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;

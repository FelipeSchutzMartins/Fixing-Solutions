import Vue from 'vue'
import VueRouter from "vue-router";
import CriarConta from "../components/CriarConta";
import Login from "../components/Login";

Vue.use(VueRouter);
const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        { path: '', name: 'login', component: Login  },
        { path: '/CriarConta', name: 'criarConta', component: CriarConta  }
    ]
});
export default router
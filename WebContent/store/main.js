import Vue from 'vue'
import App from '../App.vue'
import router from "../router";
import { BootstrapVue, BootstrapVueIcons } from 'bootstrap-vue'
import jQuery from "jquery";
import VueTheMask from 'vue-the-mask'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)
Vue.use(VueTheMask)

Vue.config.productionTip = false
window.$ = jQuery;

new Vue({
  el: '#app',
  router,
  render: h => h(App),
})

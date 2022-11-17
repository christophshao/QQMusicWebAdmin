/**
 * 在 main.js中全局引用
 * **/
import Vue from 'vue'
import App from './App'
import router from './router'  //自动扫描
import store from './store/index'
import ElementUI from 'element-ui';
import VCharts from 'v-charts'
// import echarts from "echarts";


import 'element-ui/lib/theme-chalk/index.css';
import '../src/assets/css/main.css';

Vue.use(ElementUI)
Vue.use(VCharts)
// Vue.prototype.$echarts = echarts;
Vue.use(router)




new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
});

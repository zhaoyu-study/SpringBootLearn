import Vue from 'vue'
import App from './App.vue'
import router from './router'
/*import store from './store/index'*/

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import './assets/gloable.css'
import request from '@/utils/request';

Vue.config.productionTip = false

Vue.use(ElementUI,{size:"mini"});

Vue.prototype.request=request //全局设置request

new Vue({
  router,
/*  store,*/
  render: h => h(App),
  beforeCreate(){
    Vue.prototype.$bus=this//安装全局事件总线
  }
}).$mount('#app')

import Vue from 'vue'
import App from './App.vue'
import router from './router'
import Axios from 'axios'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.config.productionTip = false
Vue.prototype.$axios=Axios
Vue.use(ElementUI)
//让axios请求保持为 json数据格式
Axios.defaults.headers.post['Content-Type']='application/json;charset=UTF-8';
//为保证能够获取session数据
Axios.defaults.withCredentials = true

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

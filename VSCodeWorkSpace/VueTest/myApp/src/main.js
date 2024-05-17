// import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router.js'
import { onBeforeMount, onMounted, ref } from 'vue'
// 引入 echarts
import * as echarts from 'echarts'
import ElementPlus from 'element-plus'
import 'element-plus/theme-chalk/index.css'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
const app = createApp(App)
// 全局挂载 echarts
app.config.globalProperties.$echarts = echarts

app.use(router)
app.use(ElementPlus, {
    locale: zhCn,
})
app.mount('#app')


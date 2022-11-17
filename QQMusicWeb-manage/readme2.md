### 这是前端-后台管理页面

#### 以下是第一次配置element-ui成功的代码

##### index.js页面：
```js
import Vue from 'vue'
import VueRouter from 'vue-router'

import Content from '../components/Content'
import Main from '../components/Main'
import lx from '../components/lx'


// 安装路由
Vue.use(VueRouter);

// 配置导出路由
export default new VueRouter({
    // 注意这里是routes
    routes: [
        {
            //路由路径
            path: '/content',
            name: 'content',
            //跳转的组件
            component:Content
        },
        {
            //路由路径
            path: '/main',
            name: 'main',
            //跳转的组件
            component:Main
        },
        {
            //路由路径
            path: '/lx',
            name: 'lx',
            //跳转的组件
            component:lx
        },
        
    ]
});

```

##### App.vue页面:
```js
<template>
  <div id="app">
    <h1>QQMusic</h1>
    <router-link to="/main">首页</router-link>
    <router-link to="/content">内容页</router-link>
    <router-link to="/lx">lx</router-link>
    <router-view></router-view>
  </div>
</template>

<script>
  export default {
    name: "App",
  };
</script>

<style>
  
</style>
```

##### main.js页面：
```js
import Vue from 'vue'
import App from './App'
import router from './router'  //自动扫描

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(router);
Vue.use(ElementUI);


new Vue({
  el: '#app',
  router,
  render: h => h(App)
});

```

##### 组件内容页面：(可以直接把elment-ui拿过来)
```js
<template>
  <div id="app">
    <h1>Content</h1>
  </div>
</template>

<script>
    export default{
        name: "Content"
    }
</script>

<style scoped>

</style>




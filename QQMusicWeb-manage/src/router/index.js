import Vue, { computed } from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter);

// 配置导出路由
export default new VueRouter({
    // 注意这里是routes
    routes: [
        {
            path: '/',
            component: resolve => require(['../views/Login.vue'],resolve)
        },
        {
            path: '/Home',
            component: resolve => require(['../components/Home.vue'],resolve),
            children:[
                {
                    path: '/admin',
                    component: resolve => require(['../views/adminPage.vue'],resolve)
                },
                {
                    path: '/Consumer',
                    component: resolve => require(['../views/ConsumerPage.vue'],resolve)
                },
                {
                    path: '/Singer',
                    component: resolve => require(['../views/SingerPage.vue'],resolve)
                },
                {
                    path: '/SongList',
                    component: resolve => require(['../views/SongListPage.vue'],resolve)
                },
                {
                    path: '/SongPage',
                    component: resolve => require(['../views/SongPage.vue'],resolve)
                },
                {
                    path: '/ListSong',
                    component: resolve => require(['../views/ListSongPage.vue'],resolve)
                },

            ]
        }
        
        
    ]
});
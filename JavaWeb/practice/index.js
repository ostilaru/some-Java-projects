// 配置路由的地方
import Vue from 'vue'
import VueRouter from 'vue-router'

// 使用插件
Vue.use(VueRouter)

// 引入路由组件
import Home from '@/pages/Home'
import Login from '@/pages/Login'
import Register from '@/pages/Register'
import Search from '@/pages/Search'
import Main from '@/pages/Main'

// 配置路由
export default new VueRouter({
    // 配置路由
    routes: [
        {
            path:"/home",
            component: Home
        },
        {
            path:"/login",
            component: Login
        },
        {
            path:"/register",
            component: Register
        },
        {
            path:"/search",
            component: Search
        },
        {
            path:"/main",
            component: Main
        }
        // 重定向：在项目跑起来的时候，访问/，立马让它定向到首页
        // {
        //     path:"*",
        //     redirect: "/home"
        // }
    ]
})

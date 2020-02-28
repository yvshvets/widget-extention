import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import ro from "element-ui/src/locale/lang/ro";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
        beforeEnter: requireAuth
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/redister',
        name: 'Register',
        component: Register
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        // component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

function requireAuth() {

}

export default router

import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../views/Login"

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/Chief',
    name: 'Chief',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/Chief.vue'),

    children:[
      {
        path: '/',
        name: 'index',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../components/index.vue'),
      },{

        path: '/modifyPassword',
        name: 'modifyPassword',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../components/modifyPassword.vue'),
      },{
        path: '/StudentMessage',
        name: 'StudentMessage',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../components/StudentMessage.vue'),
      },{
        path: '/CheckIn',
        name: 'CheckIn',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../components/CheckIn.vue'),
      },{
        path: '/RoomInformation',
        name: 'RoomInformation',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../components/RoomInformation.vue'),
      },{
        path: '/AddRoom',
        name: 'AddRoom',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../components/AddRoom.vue'),
      },{
        path: '/facilities',
        name: 'facilities',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../components/facilities.vue'),

      },{
        path: '/AddFac',
        name: 'AddFac',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../components/AddFac.vue'),
      },{
        path: '/ClassMe',
        name: 'ClassMe',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../components/ClassMe.vue'),
      },{
        path: '/AddClass',
        name: 'AddClass',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../components/AddClass.vue'),
      },{
        path: '/UserMe',
        name: 'UserMe',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../components/UserMe.vue'),
      },{
        path: '/AddUser',
        name: 'AddUser',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../components/AddUser.vue'),
      }

    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

//挂载路由导航守卫
router.beforeEach((to,from,next)=>{
  //to:表示将要访问的路径
  //from 从哪里来
  //next是一个函数   表示放行   next()表示放      next('/login') 表示强制跳转
    if(to.path === '/') return next()
    //获取用户id
    const  userID=window.sessionStorage.getItem('userId')
    if(!userID){
      alert("请先登录")
      return next('/')

    }
    next()//直接放行

})

export default router

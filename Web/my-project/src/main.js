import Vue from 'vue'
import VueRouter  from "vue-router"
import BootstrapVue from "bootstrap-vue"
import CC from "./components/custom/custom.components.js"
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap-vue/dist/bootstrap-vue.css"
import axios from 'axios'
import VueAxios from 'vue-axios'
import VueAuth from '@websanova/vue-auth'
import store from './api/store'

import 'vue-awesome/icons'

/* Register component with one of 2 methods */

import Icon from 'vue-awesome/components/Icon.vue'
import calendar from './components/custom/calendar.vue'

// globally (in your main .js file)
Vue.component('icon', Icon)
Vue.component('calendar', calendar)
import moment from "moment"
import  'vue2-animate/dist/vue2-animate.min.css'

import ClickOutside from 'vue-click-outside'
// Vue.component("icon",require("vue-icons"))
Vue.use(BootstrapVue)
Vue.use(VueRouter)
Vue.use(CC)
Vue.directive("ClickOutside",ClickOutside)
Vue.filter("noMiliDate",function(strt){strt = strt+"";if(strt.slice){return parseInt(strt.slice(0,strt.length-3));} return strt;})


// Vue.config.modifiers["out"]=
const eventHub = new Vue() // Single event hub



    // Vue.directive('todo-focus', function (el, binding) {
    //   if (binding.value) {
    //     el.focus()
    //     console.log("focus")
    //   }
    
  // });
// Distribute to components using global mixin
Vue.mixin({
    data: function () {
        return {
            // eventHub: eventHub,
            $bus:eventHub,
            moment: moment
        }
    }
})
Vue.prototype.$bus = eventHub;
// export default CC;
var data = {
  msg: 'Feedback Planning 90',
  moment:moment
}

const router = new VueRouter({
  mode:"history",
  routes: [
    {
      path: '/',
      name: 'App',
      component:require("./components/A.vue").default,
      props: {params: data},
      meta: {auth:null}
      ,
      children:[
        {
          path : "/login",
          name:"login",
          components: {gama:require("./components/login.vue").default},
          meta: {auth:false}
        },{
          path: "/inscription",
          name:"inscription",
          components: {gama:require("./components/inscription.vue").default},
          meta: {auth:false}
        }
      ]
    }
  ]
})

Vue.router=router
Vue.axios=axios
Vue.store=store
Vue.use(VueAuth, {
    auth         :  require('./api/setup/api.token.js'),
    http         :  require('./api/setup/axios.1.x.js'),
    router       :  require('@websanova/vue-auth/drivers/router/vue-router.2.x.js'),
    loginData    :  {url:"login/authenticate",redirect:false,fetchUser:false},
    fetchData    :  {enabled:false,redirect:false },
    logoutData   :  {url:"logout",makeRequest: true},
    refreshData  :  {enabled : false,url:"check"},
    registerData :  {url:"users/create"},
    authRedirect : "/",
    notFoundRedirect: {path:"/"}
})
// console.log(process)

Vue.axios.defaults.baseURL = 'http://'+location.hostname+':4000' ;


var VueVue = {
  router,
  template:"</App>",
  data () {
      return data
 
  },
  render: h => h(require("./App.vue").default),
  store:store,
  mounted(){
    // require("./components/custom/jquery.js")
    // require("./components/custom/fullcalendar.min.js")
    console.log("mounted NN")
  // if(this.$store.state.user && this.$store.state.user.length>0){    
  //   this.$user.loadID_Sess(this.$store.state.user[0])
  // }
  }
}
// const moment = require('moment').default
// require('moment/locale/fr').default


Vue.use(require('vue-moment'), {
    moment:require('moment')
})
import SetupCrud from "./api/setup/setup.crud.js"
import user from "./api/user.js"
import session from "./api/session.js"
import periode from "./api/periode.js"

import SetupSoup from "./api/setup/setup.soup.js"
import connexionSoup from "./api/soup/soupconnexion.js"
import userSoup from "./api/soup/soupuser.js"
import sessionSoup from "./api/soup/soupsession.js"

Vue.use(SetupCrud,{user:user,session:session,periode:periode})
Vue.use(SetupSoup,{
  soups:{
    connexion:connexionSoup,
    user:userSoup,
    session:sessionSoup
  },
  drivers:{
    $store:store,
    $auth:Vue.auth
  }
})
import SetupFusion from "./api/setup/setup.fusion.js"
Vue.use(SetupFusion);

// console.log(Vue)
const vue = new Vue(VueVue).$mount("#app")
// console.log(vue.$soup.$connexion)


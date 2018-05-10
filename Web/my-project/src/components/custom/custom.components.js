(function () {


   module.exports = function plugin(Vue,options) {
Vue.component('button-spinner', {
  template: '<div><b-button @click="clicked" :style.native="styley" :type="type" v-show="!loading" :variant="variant">\
               \
                <slot></slot>\
            </b-button>\
            <i  v-show="loading" class="fa fa-spinner fa-spin"></i></div>',
  props:[
    "type",
    "variant",
    "loading",
    "styley"
  ],
  methods:{
    clicked(){
      this.$emit("button-spinner-clicked");
    }
  }
});

Vue.component('b-button-router-link', {
  template: '<div :style="{display:s_display}"><b-button v-if="$route.name!=to"><router-link :to="{name: to}"><slot/></router-link></b-button></div>',
  props:{
    to:{},
    s_display:{default: "block"}
    }, 
  // ],
  methods:{
    clicked(){
      this.$emit("button-spinner-clicked");
    }
  }
});

}
})();
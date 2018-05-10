// import TK  from "../utils/tk.js";

(function () {
var TK = require("../utils/tk.js").default;
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

var FC = $.fullCalendar; // a reference to FullCalendar's root namespace
var View = FC.View;      // the class that all views must inherit from
var CustomView;          // our subclass

CustomView = View.extend({ // make a subclass of View

  initialize: function() {
    this.nstart = moment(this.start);
    this.mstart = moment(this.options.validRange.start);
    this.mend = moment(this.options.validRange.end);
    // called once when the view is instantiated, when the user switches to the view.
    // initialize member variables or do other setup tasks.
    
  },

  render: function() {

    var toFormat = function(f){
      return TK.upperF(f.format("MMMM YYYY"));
    }
    console.log(this)
 $(".fc-prev-button").prop('disabled', true); 
      $(".fc-next-button").prop('disabled', true); 
    this.title = toFormat(this.mstart)+" - "+ toFormat(this.mend)
    var dlf=`
        <style>
          .tdCustomCal {
            border-color: #6b4736 !important;
                vertical-align: middle !important;
    height: 129px;

          }
          .fc-today{
            background-color: black;
          }
        </style>
        <table class='t-center tcenter'>
            <tbody>`;

for (var m =  this.mstart.clone().startOf('month') ; m.isBefore(this.mend); m.add(1, 'months')) {

  if (this.nstart.startOf('month').isSame(m)) {


  dlf+=`
        <tr>
                <td class="tdCustomCal fc-today">`+toFormat(m)+`</td>
              </tr>
  `;
}else{
   dlf+=`
        <tr>
                <td class="tdCustomCal">`+toFormat(m)+`</td>
              </tr>
  `; 
  }

}
  dlf+="</tbody></table>";
    $(this.el).append(dlf);


      
    // responsible for displaying the skeleton of the view within the already-defined
    // this.el, a jQuery element.
  },

  setHeight: function(height, isAuto) {
    // responsible for adjusting the pixel-height of the view. if isAuto is true, the
    // view may be its natural height, and `height` becomes merely a suggestion.
  },

  renderEvents: function(events) {
    // reponsible for rendering the given Event Objects
  },

  destroyEvents: function() {
    // responsible for undoing everything in renderEvents
  },

  renderSelection: function(range) {
    // accepts a {start,end} object made of Moments, and must render the selection
  },

  destroySelection: function() {
    // responsible for undoing everything in renderSelection
  }

});

FC.views.customCACA = CustomView;
})();


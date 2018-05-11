<template><div></div></template>

<script>
	import TK from "../utils/tk.js"
export default  {	
  props: {
    events: {
      type: Array, 
      required: true
    },
    
    editable: {
      type: Boolean,
      required: false,
      default: false
    },
    
    droppable: {
      type: Boolean,
      required: false,
      default: false
    },
    selectable:{
      type: Boolean,
      required: false,
      default: false
    },
    from:{},
    to:{}
  },
  
  data: function()
  {
    return {
      cal: null,
      lm:"month"
    }
  },
  // mounted(){
  //   console.log("fffffkk")
  // },
  methods:{
    refresh(){
      this.cal.fullCalendar("refresh")
    },
    today(){
      console.log("TODAY")
      this.cal.fullCalendar("today")

    },
      between(){

        var from = this.from;
        var to = this.to;
         var self = this;
        return function(currentView){
          // console.log(date)
          var viewName=currentView.name
          if (viewName in ["month,agendaWeek,agendaDay"]) {
          if(currentView.start.isSame(moment(),"day") && viewName=="agendaDay"){
//             var currentHour = moment("hTT");
//     var $viewWrapper = $("div.fc-"+viewName+"-view div.fc-body");
//     var currentHourLabel = $viewWrapper.find("table tbody tr th:contains('"+ currentHour
// +"')");
    $(window).scrollTop($(".fc-now-indicator").offset().top, 1);
          }
        var minDate = from;
        var maxDate = to;
          if (minDate >= currentView.start && minDate <= currentView.end) {
      $(".fc-prev-button").prop('disabled', true); 
      $(".fc-prev-button").addClass('fc-state-disabled');
      return false; 
    }
    else {
      $(".fc-prev-button").removeClass('fc-state-disabled'); 
      $(".fc-prev-button").prop('disabled', false); 
    }
    // Future
    if (maxDate >= currentView.start && maxDate <= currentView.end) {
      $(".fc-next-button").prop('disabled', true); 
      $(".fc-next-button").addClass('fc-state-disabled'); 
      return false;
    } else {
      $(".fc-next-button").removeClass('fc-state-disabled'); 
      $(".fc-next-button").prop('disabled', false); 
    }
  }
    return true;

//           if(from){
//             // console.log(view)
//               if(date>from){
//               $(cell).addClass('disabled');
//               }

//           }
//           if(to){
// if (date > to){
//             $(cell).addClass('disabled');
//           } 
//           }
          
        }
      }
  },
  mounted: function()
  {
    var self = this;
    self.cal = $(self.$el);
    
    // console.log(self.cal)
    var args = {
      locale: "fr",
      header: {
        left:   'prev,next',
        center: '\n title',
        right:  'customCACA,month,agendaWeek,agendaDay'
      },
      height: "auto",
      scrollTime:"06:00:00",
      allDaySlot: true,
      slotEventOverlap: false,
      timeFormat: 'HH:mm',
      firstDay:1,
      defaultView:"customCACA",
      nowIndicator: true,
      themeSystem:"bootstrap4",
      events: self.events,
      views: {
		    listSession: {
		      type: 'list',
		      duration: { months: 3 },
		      buttonText: 'EventSession',
		      validRange: {
                start: this.from.format("YYYY-MM-DD"),//start date here
                end: this.to.format("YYYY-MM-DD") //end date here
            },
            visibleRange: {
                start: this.from.format("YYYY-MM-DD"),//start date here
                end: this.to.format("YYYY-MM-DD") //end date here
            }
        }
  	  },
      buttonText:{
        customCACA:"Session",
              listDay:"EventJour",
              listWeek:"EventSemaine",
              listMonth:"EventMois"

      },
            viewRender: function(view,el) {
              if (view.name=="customCACA") {
                  $(".fc-center").removeClass("mt-10").addClass("mt-42")
                }else{
                    $(".fc-center").removeClass("mt-42").addClass("mt-10")
                }


            	if (view.name=="listSession") {
            		if (!view.start.isSame(self.from)) {
 self.cal.fullCalendar('gotoDate',self.from.format("YYYY-MM-DD"));//start date here
                       	
            		return
            	}
            }
            	var f =el.parent().parent().find(".fc-toolbar").find(".fc-center h2");
            	f.first().html(view.title[0].toUpperCase()+view.title.slice(1))

            	// if (this.lm!=view.name) {
            	// 	this.lm!=view.name
            	// }
            	self.$bus.$emit("todoList::OK",TK.getFromObj(view,["name","title","start","end","intervalStart","intervalEnd"]));




},
      validRange: {
                start: this.from.format("YYYY-MM-DD"),//start date here
                end: this.to.format("YYYY-MM-DD") //end date here
            },
      customButtons: {
          Session: {
            text: 'Session',
             click: function(t) {
              console.log($(t.target).parent().find("button"))
              $(t.target).parent().find("button").each((tt,n)=>{
                console.log(n)
                $(n).removeClass("active")
              })
              $(t.target).toggleClass("active")
               self.$emit("sessionClick");
             }
          },
          TodoList: {
              text: 'TodoList',
              click: function(t) {
              	// console.log("tttt")
              	if (!$(t.target).hasClass("disablede") ) {
              		              	$(t.target).addClass("disablede")

                self.$emit("todolist::OK",self.cal.fullCalendar("getView"));
                 self.$bus.$emit("todoList::OK",self.cal.fullCalendar("getView"));

              	}else{
              	$(t.target).removeClass("disablede")
              		self.$emit("todolist::NOK",self.cal.fullCalendar("getView"));
              	}
              }
          }
      },
      
      dayClick: function(date)
      {
            self.$emit('day::clicked', date);
            console.log(date)
            self.cal.fullCalendar('gotoDate', date);
            self.cal.fullCalendar('changeView', 'agendaDay');
      },

      eventClick: function(event)
      {
            self.$emit('event::clicked', event);
      }
    }
    this.$parent.$on('todayFC', this.today);
    this.$parent.$on('refreshFC', this.refresh);
    if (self.editable)
    {
      args.editable = false;
      args.eventResize = function(event)
      {
        self.$emit('event::resized', event);
      }
      
      args.eventDrop = function(event)
      {
        self.$emit('event::dropped', event);
      }
    }
    
    if (self.droppable)
    {
      args.droppable = false;
      args.eventReceive = function(event)
      {
        self.$emit('event::received', event);
      }
    }
    if (self.selectable)
    {
      args.selectable = false;
      args.selectHelper = false;
      args.select = function(start,end)
      {

        self.$emit('event::selectable', {start:start,end:end});
      }
    }
        // args.viewRender = self.between()
    
    
      var tc=this.cal.fullCalendar(args);
        console.log(tc)

    
  }
  }
</script>

<style lang="scss" >

.fc-today,.fc-event{
	border: none;
	color: white!important;
	background-color: black!important;

}
.disablede{
	color: grey;
}
.btn-primary:not(:disabled):not(.disabled).active, .btn-primary:not(:disabled):not(.disabled):active, .show>.btn-primary.dropdown-toggle{
	background-color: $brown!important;
	border-color: $brown!important;
}
.fc-day{
	border-color: black!important;
}
	.fc-day-number{
		color:white!important;
	}
.table-bordered td,.table-bordered th{
		border-color: $brown!important;

}
	.fc .btn-primary,.fc-day-header,.fc-head-container{
		background-color: black!important;
		border-color: $brown!important;
	}
</style>
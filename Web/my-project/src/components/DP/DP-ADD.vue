<template>
<b-container>
 <div 
 	role="group" 
 	>
 	<b-form @submit.prevent="onSubmit" name="yourForm" id="theForm">
	<b-row>
        <b-col style="height: 100%;">
	<!-- <div class="" > -->

	 <b-form-group >
	    <label 
	    	:for="dpid">
	    	{{name}}:
	  	</label>

	    <b-form-input :id="dpid"
	    class="ci"
	                  v-model="namee"
	                  type="text"
	                  style="height: 100%;background-color: white;color:black;"
	                  :state="state"
	                  :aria-describedby="{getInputFeed,getInputHelp}"
	                  :placeholder="placeholder"
	                  required="">             
	    </b-form-input>
	    
	    <b-form-invalid-feedback 
	    	:id="getInputFeed()">
	    	{{feedback}}
	    </b-form-invalid-feedback>

	    <b-form-text 
	    	:id="getInputHelp()">
	    	{{info}}
	    </b-form-text>
	     </b-form-group >
	    </b-col>
	    <b-col>
	     <b-form-group >
	    <datepicker 
	    	:monday-first=true
	    	language="fr"
	    	required=""
	    	v-model="date" 
	    	placeholder="DEBUT"
	    	:bootstrap-styling=true
	    	@selected="onDate"
	    	minimum-view="month"
	    	:disabled="disabled">
	
	    </datepicker>
	     <b-form-invalid-feedback 
	    	class="feedDP">
	    	{{feedbackDP}}
	    </b-form-invalid-feedback>
	     </b-form-group >
	    <!-- <icon name="fa-calendar-plus"></icon>	 -->
	    <!-- </div> -->
 <b-form-group >
	     <b-form-input 
                  type="text"
              	  readonly
              	  :value="dateFin"
                  >    
                  {{this.dateFin}}  
                       
    	</b-form-input>

  <b-form-text 
	    	id="getInputHe" class="c-blackI">
	    	 90 Jours 
	    </b-form-text> 
	     </b-form-group > 
	
    </b-col>
    <b-col style="border:1px solid #e9ecef"> <b-button style="width: 100%;
    height: 100%;" type="submit" class="middle tcenter relative p-rel">OK<icon name="spinner" v-show="okok" style="color: white;"></icon></b-button></b-col>
	</b-row>
 	</b-form>

  </div>
  </b-container>
</template>

<script type="text/javascript">

import Datepicker from 'vuejs-datepicker';
import moment from 'moment';

export default {
	inherit: true,
	props:["name","feedback","info","placeholder","state","vmodel","dpid"],
	data(){
		return {
			disabled:{
				to: moment().add("1","month").toDate()
			},
			dateFin:"",
			namee:"",
			date:null,
			okok:false,
			feedbackDP:"mettre une date"
		};
	},
	components:{ Datepicker },
	methods:{
		onSubmit(f){
			if (!this.okD) {
				$(f).find(".feedDP").show();
				$(f).find(".feedDP").parent().attr("state",true);
				return;
			}
				this.okok=true;
			var $this=this
			// console.log("SUBMIT")
			// this.$el.submit();
			// console.log(this.date)
			// console.log(this.moment(this.date).format('x'))
			this.$user.addSession({"name":this.namee,"date":this.moment(this.date).add("1","day")})
			.then(()=>{
				this.$emit("stop");
				this.okok=false;
				this.$emit("SessionAdd")

			});
		},
		getMoment(){
			return this.moment().add("1","month").toDate()
		},
		capitalize(str) {
			return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
		},
		onDate(date){
			this.okD=true
			var slf=this.moment(date).add("3","months");
			var month = this.capitalize(slf.format("MMM"));
			this.dateFin=slf.format("DD")+" "+month+" "+slf.format("YYYY");
			console.log(this.dateFin)
		},
		getInputFeed(){
			return this.dpid+"Feedback";
		},
		getInputHelp(){
			return this.dpid+"Help";
		}
	}
}
</script>
<style type="text/css" scoped>

	.col :globals(b-col) {
		height: 100%;
	}

	.tcenter{
		text-align:center;
	}
	.middle{
		top:50%;
		transform: translateY(-50%);
	}
	.relative{
		position: relative;
	}
</style>
<style lang="scss">
	.ci *{
		&::-webkit-input-placeholder {
	font-style: italic;
	font-weight: 300;
	color: white;
 }
 &::-moz-placeholder {
	font-style: italic;
	font-weight: 300;
	color: white;
}
&::input-placeholder {
	font-style: italic;
	font-weight: 300;
	color: white;
}
	}

</style>
<template>
<div>
	<div class="me base bg-brown p-rel tcenter" style="margin-top:350px;">
<h1 style="color:white">
            Sessions
        </h1>
		<b-list-group >

			<b-list-group-item 
				@click.self="addf"  
				class="tcenter justify-content-between align-items-center">

				<DPADD 
				 	@SessionAdd="as"
				 	@stop="addf"
					v-if="add" 
					dpid="inputADD" 
					vmodel="ADD" 
					name="Add Session"
					placeholder="Nom de la Session">
				</DPADD>

	    	    <div 
	    	    	v-else class="tcenter" @click.self="addf" >
	    	    	AJOUTER UNE SESSIONS
	    	    </div>

	    	</b-list-group-item>

		</b-list-group>

		<b-list-group 
			v-for="t in getTodosC" 
			:key="t.id">

			  <b-list-group-item 
			  		class="d-flex justify-content-between align-items-center" @click.self="sessionS(t)">
			  		<b-badge 
			    		variant="primary" 
			    		pill>
			    		{{t.date | noMiliDate | moment("dddd Do MMMM YYYY")}}
			    	</b-badge>
			    		{{t.name}}

			    	<b-badge 
			    		variant="secondary" 
			    		pill>
			    		{{t.date | noMiliDate | moment("add","3 months","dddd Do MMMM YYYY") }}
			    	</b-badge>
			    	
			    	<span v-show="loadd(t)">
				<icon name="spinner" style="color:white;"></icon>
				</span>
			   <button-spinner type="submit" styley="color:black !important;" @button-spinner-clicked="deleteSess(t)" variant="error" :loading="load && actualT.id==t.id">
                X
            </button-spinner>
				<!-- <span style="color:red" @click="deleteSess(t)">X</span> -->
			  </b-list-group-item>

		</b-list-group>
	

	</div>
		<!-- <p> -->
			<blockquote class="blockquote" style="width: 60%;    margin-left: 20%;position: absolute;bottom: 30px;background-color: white;text-align: center;color: #6a4737;font-weight:  bold;"><span data-v-7ebd118f="" class="mb-0" style="
    width: 60%;
    position:  relative;
">
  					Une Session est une periode de 3 Mois.
  				</span></blockquote>
		<!-- </p> -->
	</div>
</template>

<script>

import DPADD from "./DP-ADD.vue"
import Date from 'datejs'
import moment from "moment"


// -|-
require('moment/locale/fr');

var d = Date.today;

var state = {
	disabled: {
		to: d, 
	}
}

export default {
	mounted(){
		console.log("mounted")


		this.getTodos()
	},	
	data(){
		return {
			todos: [],
			add:false,
			ADD:"",
			loading:false,
			loading2:false,
			actualT:null,
			loadd_:false
		}
	},
	computed:{
		getTodosC(){
			return this.$store.state.todos;
		},
		load(){
			return this.loading;
		}

	},
	methods:{

		sessionS(sess){
			this.actualT=sess;
			this.loadd_=true
			this.$store.state.okDP=false;
			this.$soup.$user.changeSessCurr(sess,this).then((res)=>{
				console.log("DP::sessionS(101)")
				console.log(res)
				this.$store.state.okDP=true;
				this.actualT=null;
				this.loadd_=false
				this.$emit("dp")
			});
		},
		loadd(t){
			return  this.loadd_ && t.id==this.actualT.id
		},		
		as(){
			setTimeout(this.getTodos, 1000);
		},
		deleteSess(t){
			this.loading=true;
			var $this=this;
			this.actualT=t;
			this.$session.sessA[t.id].DELETE().then(res=>{
				this.getTodos();
				this.loading=false;
				this.actualT=null;

			});
		},
		addf(){
			this.add=!this.add;
		},
		getTodos(){
			this.loading2=true;
			var $this=this;
			// this.$soup.user.loadSess()
			this.$soup.$user.loadSess(this).then(res=>{
				console.log("DP::getTodos\nNOTERROR(122)");
				// $this.$store.dispatch("setTodos",res.data);
				this.loading2=false;

			},err=>{
				console.log("DP::getTodos(132)")
				console.log(err);
			});
			return this.$store.state.todos;
		},
		customFormatter(value){
			return this.moment(String(value)).format('MM/DD/YYYY hh:mm');
		}
	},
	components:{DPADD:DPADD}
}

</script>
<style lang="scss" scoped>
*{
box-sizing: border-box;
}
	.list-group-item{
		cursor: pointer;
	}

	.me{
		text-align: center;
	}

</style>

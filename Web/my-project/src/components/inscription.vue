<template>
<div class="tcenter insc base">
<h1>
            Inscription
        </h1>
	<b-form @submit.prevent="inscription" :state="error" style="padding-top: 15px;
    padding-bottom: 15px;">
            <b-form-group id="exampleInputGroup2"  :state="error" label="Ton Identifiant:" label-for="exampleInput2">
                <b-form-input id="exampleInput2" :state="pb" @blur.native="checkUserName" placeholder="Ton Identifiant" :class="{loading : loadingUsername}" required="" type="text" v-model="data.body.username">
                <!-- <i  v-show="loadingUsername" class="fa fa-spinner fa-spin"></i> -->
                </b-form-input>
                <b-form-invalid-feedback id="inputLiveFeedback4">
			      User {{data.body.username}} already Exist
			    </b-form-invalid-feedback>
            </b-form-group>
            <b-form-group id="exampleInputGroup3"  label="Mot de Passe:" label-for="exampleInput3">
                <b-form-input type="password"  :state="error" id="exampleInput3" required="" placeholder="Mot de Passe" v-model="data.body.password">
                </b-form-input>

			</b-form-group>
			<b-form-group id="exampleInputGroup4"  label="Mot de Passe de Confirmation:" label-for="exampleInput4">
                <b-form-input type="password"   :state="error2" id="exampleInput4" required="" placeholder="Mot de Passe de Confirmation" @input.native="inputOK"  v-model="data.body.repassword" >
                </b-form-input>
<b-form-invalid-feedback id="inputLiveFeedback3">
			      not same password
			    </b-form-invalid-feedback>
			</b-form-group>

            <button-spinner class="bg-brown b" type="submit" variant="primary" :loading="load">
                OK
            </button-spinner>
             <b-form-invalid-feedback id="inputLiveFeedback2">
			      Error2
			    </b-form-invalid-feedback>
        </b-form></div>
</template>

<script>
	export default {
		data () {
			return {
				alreadyCP:false,
				data:{
				body:{
					username:"",
					password:"",
					repassword:""
				}},
				
				error:null,
				load:false,
				pb:null,
				loadU:false
			}
		},
		computed:{
			loadingUsername(){
				return this.loadU
			},
			error2(){
				 return  this.data.body.password.length==0 || !this.alreadyCP ? null : this.data.body.password== this.data.body.repassword 
			}

		},
		methods:{
			inputOK(){
				this.alreadyCP=true
			},
			// changeOK(){
				
			// },
			checkUserName(){
				if(this.data.body.username.length>0){
					this.loadU = true
					this.$user.checkExist(this.data.body.username,ok => {
						this.loadU = false
						this.pb=!ok.ok;} )
				}
			},

			inscription(){
				this.load=true
				this.$auth.register({
				    data: {
				    	username:this.data.body.username,
				    	password:this.data.body.password,
				    	password2:this.data.body.password
				    }, // data: {} in Axios
				    success: function (res) {
				    	var $this=this;
						this.load=false
						$this.$soup.user.loadUserNSess(this,this.data.body.username).then(()=>{
                             setTimeout(()=>{

                                    $this.$router.push({name:"App"});
                                    $this.$store.state.okDP=true
                                    //IT'S LOAD OK TO SHOW A
                                },500);
                         });

				    },
				    error: function () {
				    	this.load=false
				    	this.error=false
				    	$("#inputLiveFeedback2").show()
				    	},
				    autoLogin: true,
				    rememberMe: true,
				    fetchUser: false,
				    enabled:false,
				    redirect: {name: 'App'},
    // etc...
});
			}
		}
	}
</script>

<style  lang="scss" scoped>

.insc{
	 background-color: $brown;
}
</style>
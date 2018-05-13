<template>
    <div v-if="!error || (error && error==false)" class="tcenter Login base">
        <h1 @click.once.native.self="console.log('huhu');" style="color:white">
            Connexion
        </h1>
         <b-alert variant="success" :show="ok">Success Alert</b-alert>
         <b-alert variant="error" :show="errors && errors==false">PB Alert</b-alert>
        <b-form @submit.prevent="login" :state="error" style.native="width: 100%;">
            <b-form-group id="exampleInputGroup2"  :state="error" label="Ton Identifiant:" label-for="exampleInput2">
                <b-form-input id="exampleInput2" :state="error" placeholder="Enter username" required="" type="text" v-model="data.body.username">
                </b-form-input>
            </b-form-group>
            <b-form-group id="exampleInputGroup3"  label="Ton Mot de Passe:" label-for="exampleInput3">
                <b-form-input type="password"  :state="error" id="exampleInput3" required="" placeholder="Ton Mot de Passe" v-model="data.body.password">
                </b-form-input>

			     </b-form-group>

            <button-spinner class="bg-brown b" styley="border: 1px solid white !important;" type="submit" variant="primary" :loading="load">
                OK
            </button-spinner>
             <b-form-invalid-feedback id="inputLiveFeedback2" v-if="error">
			      Error2
			    </b-form-invalid-feedback>
        </b-form>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                data: {
         			body:{
                  	username: '',
                    password: ''
                }
                },
                load: false,
                errors: null,
                ok: false
            };
        },

        mounted() {
            // console.log(this.$auth.redirect());

            // Can set query parameter here for auth redirect or just do it silently in login redirect.
        },
        computed: {
        	error(){
        		return this.errors 
        	}
        },
        methods: {
        	   
            login() {
                try{
                this.load=true
                var $this=this;
                this.$auth.login({
                    data: this.data.body, // Axios
                    rememberMe: true,
                    redirect: false,
                    fetchUser: false
                })
                .then((res) => {
                    $this.load=false
                    console.log("LOGIN::login");
                    $this.ok= true
                    $this.errors=true
                     try{
                    $this.$soup.user.loadUserNSess(this,res.data.username).then(()=>{
                             setTimeout(()=>{

                                    $this.$router.push({name:"App"});
                                    $this.$store.state.okDP=true
                                    //IT'S LOAD OK TO SHOW A
                                    console.log("LOGIN::login/setTimeout(76)");
                                },1000);

                    })
                 
                }catch(a){
                    $this.$soup.logout();
                 }
                }, (res) => {
                    this.load=false
                    console.log('error ' + this.context);
                    this.errors = false;
                });
            }
            catch(a){
                this.$soup.logout();
            }
            }
        }
    }
</script>
<style scoped lang="scss">

    #exampleInput2,#exampleInput3 {
        padding-left: 0px;        
        padding-right: 0px;
        border-right-width: 0px;
        border-left-width: 0px;
    }

    //     width: 99%;
    //     padding-right: 0px;
    // }
    .Login{
        background-color: $brown;
    }
    // padding-right:0;

</style>
<style lang="scss">


</style>
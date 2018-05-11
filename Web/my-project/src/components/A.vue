<template>
    <div style="position: absolute;width: 100vw;height: 100vh;overflow: hidden;">
        <b-alert show="" v-if="pbResseau" variant="warning">
            Warning PAS DE RESEAU ou SERVER DECONNECTÉ, VEUILLEZ ATTENDRE...
        </b-alert>
        <nav style="text-align: center;position:relative;top:0px;width: 100vw;background-color: black;z-index: 99999;height:50px;">
          <p v-if="loading" style="position:absolute;text-align:center;" class="middle">
              <icon name="spinner"></icon>
              Veuillez patienter...
          </p>
            <button-spinner @button-spinner-clicked="settings"  styley="opacity:0.7;background-color: transparent!important;text-align:left!important;position:absolute;left:0px;" v-if="$auth.check()">
                Settings
            </button-spinner>
            <b-button-router-link s_display="inline" to="App" v-if="!$auth.check()" v-show="!$auth.check()">
                Home
            </b-button-router-link>
            <button-spinner @button-spinner-clicked="logout" styley="background-color: transparent!important;position:absolute;right:0px;" v-if="$auth.check()">
                Déconnexion
            </button-spinner>
            <div v-if="AOK()">
                <span @click="Editing()" class="btn btn-secondary " v-if="$auth.check()" v-show="!editing">
                    {{title}}
                    <icon name="pencil-alt" style="color: white">
                    </icon>
                </span>
                <span>
                    <input :class="['ATitle','edit', 'tcenter',pb ? 'border: 1px solid red':'']" @keyup.enter="enter" @keyup.esc="esc"   required="" type="text" v-click-outside="escO" v-if="editing" v-model="title" />
                    <icon name="spinner" style="    left: -30px;
;position:relative;color: white" v-show="load">
                    </icon>
                </span>
            </div>
            <b-button-router-link s_display="inline" to="login" v-if="!$auth.check()">
                Connexion
            </b-button-router-link>
            <b-button-router-link s_display="inline" to="inscription" v-if="!$auth.check()">
                Inscription
            </b-button-router-link>
        </nav>
        <div style="background: url(/src/assets/cal.jpg);width: 100%;height: 100%;top:0;left:0;background-size: cover;position: absolute;">
            <!-- <transition name="bounce"> -->
            <div :class="$route.name=='App' && !$auth.check() ? 'A' : 'Login'" :style="ok() ? 'text-align : left' : 'text-align : center'">
                <span style="left: 0px;position:relative;">
                    <img :class="wImg()" src="/src/assets/logo3.png">
                    
                </span>
                <div class="tcenter p-abs middle w-100" style="color:white;z-index: 99999" v-if="ok()">
                    <span class="ml-20" style="font-size: 20px;position:relative">
                        <span class="c-brown c-pointer">
                            {{$store.state.sesscur.date | noMiliDate | moment("dddd Do MMMM YYYY")}}
                            <icon name="calendar-alt" scale="1" style="opacity:0.7;color: white">
                            </icon>
                        </span>
                        <span>
                            <input :class="['edit', 'tcenter',pb ? 'border: 1px solid red':'']" @keyup.enter="enterS" @keyup.esc="escS"  required="" type="text" v-click-outside="escOS" v-if="editingS" v-model="$store.state.sesscur.name" />
                            <span @click="" class="mh-10 c-pointer" style="font-size: 30px;" v-if="!editingS">
                                {{$store.state.sesscur.name}}
                                <icon name="pencil-alt" scale="1.25" style="opacity:0.7;color: white" >
                                </icon>
                            </span>
                        </span>
                        <!-- <icon name="pencil-alt"></icon> -->
                        <span class="c-brown ">
                            {{$store.state.sesscur.date | noMiliDate | moment("add","3 months","dddd Do MMMM YYYY") }}
                            <!-- <icon  name="pencil-alt" style="font-size: 15px;color: white"></icon> -->
                        </span>
                    </span>
                    <b-button @click="cS()" style="right: 0px;position:absolute">
                        Changer de Session
                    </b-button>
                </div>
            </div>

            <b-container fluid v-if="homeOK()" class="p-abs con pdh-0" style="margin-top:148px;height:100vh;top:0px;">
               <home @homecs="cs" @loading="loading=false">
            </home>

            </b-container>

        </div>
        <DP v-if="dpOK()" @dp="loading=true">
        </DP>
        <keep-alive>
            <!-- <home @homecs="cs" v-if="homeOK()">
            </home> -->
        </keep-alive>
        <br>
            <br>
                <br>
                    <transition mode="out-in" name="f">
                        <router-view name="gama">
                        </router-view>
                    </transition>
                <!-- </br> -->
            <!-- </br> -->
        <!-- </br> -->
        <blockquote v-if="!$auth.check() && $route.name == 'App'" class="blockquote" style="width: 60%;margin-left: 20%;bottom: 0px;position: absolute;background-color: white;text-align: center;color: rgb(106, 71, 55);font-weight: bold;"><span data-v-7ebd118f="" class="mb-0" style="
    width: 60%;
    position:  relative;
">
                    TodoList pour chaque Sessions, Mois, Semaines et Jours
                </span></blockquote>
    </div>
</template>
<script>
import DP from "./DP/DP.vue"
import Home from "./Home/Home.vue"
export default {
    props: ["cacaol", "params"],
    components: {
        DP: DP,
        Home: Home
    },
    data() {
        return {
            editing: false,
            editingS: false,
            pb: false,
            load: false,
            title_:null,
            title_OLD:"",
            loading:false
                        // notLoad:true
            // okHome:false
            // cacaol : this.params
        }
    },
    beforeMount() {
        console.log("bMount")
        if (this.$auth.check()) {
                // this.title_=this.$store.state.user.username 

            // IF PAS DE USER -> LOGOUT
            if (this.$store.state.user == null) {
                console.log("OCO")
                // this.$auth.logout({makeRequest:false})
                this.$soup.$connexion.logout({
                    makeRequest: false
                });
                console.log(this.$auth.check())
                // IF PAS D'USER INFO, ON RELOAD LE USER
            }
            // else if (this.$store.state.user.userInfo == null) {
            //     // this.notLoad=false
            //     this.reloadUI().then(() => {
            //         this.fc();
            //     });
            //     // SINON ON FC JUSTE
            // }
            // SI PAS DP ON RELOAD
            else if (!this.okDPF()) {
                this.reload();
                
            }
        }
    },
    beforeUpdate() {
        console.log("BeforeUp");
            // this.title_=this.$store.state.user.username 

        // console.log(this.$store.state.user.userInfo.sessCur)
        if(! this.$auth.check()){
            this.title_ = undefined;
        //      this.fc().then(()=>{
        //          this.okHome=true;
        //      });
        }
    },
    mounted() {
        console.log("mounted A")
            this.title_=this.$store.state.user.username 
            this.title_OLD=this.title_;

    },
    created() {
        this.$store.dispatch("rmOKDP");
        console.log("created");
    this.title_=this.$store.state.user.username 

    },
    methods: {
        Editing(){
this.editing=true;
setTimeout(()=>{
$('.ATitle').focus()
 },500);       },
        escO() {
            console.log(this.editing)
            if (this.editing) {
                this.esc();
            }
        },
        escOS() {
            console.log(this.editingS)
            if (this.editingS) {
                this.escS();
            }
        },
        clog() {
            console.log("jj")
        },
        enterS() {},
        f(t) {
            t.focus()
        },
        f2(t) {
            t.focus()
        },
        enter() {
            if (this.title.length > 0) {
                this.load = true
                this.$user.checkExist(this.title, ok => {
                    this.pb = !ok.ok;
                    if (!ok.ok) {
                        this.$user.userId.PUTX({
                            key: "username",
                            value: this.title
                        }).then((r) => {
                            if (r.data == "ok") {
                                this.load = false
                                this.editing=false
                                this.title_OLD=this.title_;
                                this.$store.state.user.username=this.title_;

                            }
                            console.log(r)
                        });
                    } else {
                        this.load = false
                    }
                })
            }
        },
        esc() {
            this.title_=this.title_OLD;
            this.editing = false;
        },
        escS() {
            this.editingS = false;
        },
        settings() {},
        cS() {
            // this.okP = false;
            console.log("emitt")
            this.$bus.$emit("HOME::CS", {})
        },
        wImg() {
            if (this.$route.name == 'App' && !this.$auth.check()) {
                return 'AImg'
            } else {
                if (this.ok()) {
                    return "AImg2"
                } else {
                    return 'LoginImg';
                }
            }
        },
        ok() {
            return this.homeOK()
        },
        dpOK() {
            return this.authOK() && this.okDPOK() && !this.sesscurOK();
        },
        homeOK() {
            return this.authOK() && this.okDPOK() && this.sesscurOK();
        },
        authOK() {
            return this.$auth.check();
        },
        AOK(){
                return this.authOK()  && this.okDPOK() ;
        },
        okDPOK() {
            return this.$store.state.okDP;
        },
        sesscurOK() {
            return this.$store.state.sesscur;
        },
        be(el) {
            console.log("be")
            el.style.opacity = 0
        },
        beforeEnter(el, done) {
            console.log("enter")
            setInterval(() => {
                // el.style.opacity=1
                done()
            }, 500);
        },
        l(el, done) {
            console.log(el);
            $(el).fadeTo("slow", 0.0);
        },
        reload() {
            var $this = this;
            console.log("A::reload")
            return new Promise((r, e) => {
                this.$soup.$user.loadUserNSess(this, this.$store.state.user.username).then(() => {
                    console.log("A::reloadED")
                    $this.$store.state.okDP = true
                    r()
                });
            })
        },
        okDPF() {
            return this.$store.state.okDP;
        },
        fc() {
            // ON LOAD LA SESSION (ON EST LOGGED)
            var $this = this;
            return new Promise((r, e) => {
                console.log("A::fc")
                if (this.$store.state.user != null) {
                    // $this.$user.loadID_Sess($this.$store.state.user);
                    $this.$session.load($this).then(() => {
                        r();
                        $this.$store.state.logged = true;
                    });
                }
            })
        },
        cs(d) {
            console.log("A CS")
            this.$user.loadID_Sess(this.$store.state.user);
        },
        logout() {
            console.log(this.$store)
            this.$soup.$connexion.logout();
        }
    },
    computed: {
        title:{
            get(){
                this.title_=(this.title_ == undefined ? this.$store.state.user.username : this.title_)
                return this.title_;
            },
            set(k){
                this.title_=k
            }
        },
        okDP() {
            return this.$store.state.okDP
        },
        pbResseau() {
            return this.$store.state.pbResseau;
        }
    },
    directives: {
        'todo-focus': function(el, binding) {
            console.log("directives")
            console.log(binding.value)
            if (binding.value) {
                setTimeout(function() {
                    console.log(el)
                    el.focus();
                }, 1000);
                // el.focus()
            }
        }
    }
}
</script>
<style lang="scss">
$base:"f";
.#{$base}-enter-active {
    @include prefix(( transition-duration: 1s, transition-delay: 1s), $list); // opacity: 1;
    opacity: 0;
}
.#{$base}-enter, .#{$base}-leave-to {
    opacity: 0;
}
.#{$base}-enter-to {
    opacity: 0;
    // opacity: 1;
}
.#{$base}-leave-active
/* .slide-fade-leave-active below version 2.1.8 */

    {
    // transform: translateX(10px);
    // @include prefix((  transition-duration: 1s,transition-delay: 2s),$list);
    // opacity: 0;
}
.btn-secondary {
    font-size: 25px;
    background-color: rgba(0, 0, 0, 0.3);
    border-radius: 0px;
    /*width: 200px;*/
    /*height: 200px;*/
    color: white;
    border: none;
    &:hover {
        background-color: rgba(0, 0, 0, 0.3);
    }
}
a, a:hover {
    color: white;
}
</style><style lang="scss" scoped="">
.normal {
    background-color: rgba(0, 0, 0, 1);
    padding-top: 15px;
    padding-bottom: 15px;
    text-align: center;
    position: relative;
    transition: 1.5s;
}
.A {
    @extend .normal;
    top: 50%;
    transform: translateY(-50%);
    transition: 1.5s;
    transition-delay: 1.5s;
}
.Login {
    @extend .normal;
    border-top: 1px solid $brown;
    top: 50px;
}
.edit {
    position: relative;
    margin: 0; // width: 100%;
    font-size: 24px;
    font-family: inherit;
    font-weight: inherit;
    line-height: 1.4em;
    border: 0;
    color: white;
    padding: 6px;
    background-color: black;
    border: 1px solid #999;
    box-shadow: inset 0 -1px 5px 0 rgba(0, 0, 0, 0.2);
    box-sizing: border-box;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
}
.con{
    border-top:1px solid $brown;
}
.AImg {
    width: 600px;
    transition: 1.5s; // transition-delay: 0.5s;
}
.AImg2 {
    width: 200px;
    transition: 1.5s; // transition-delay: 0.5s;
}
.LoginImg {
    width: 300px;
    transition: 1.5s;
}
</style>

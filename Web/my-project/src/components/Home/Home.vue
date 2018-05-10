<template>
	<b-row style="background-color:#222">
    <!-- <div class="base" style="margin-top: 75px;"> -->

  <b-col cols="6" class="c-white pdh-0 bb" style="">
                        <div class="tcenter" v-show="false">
                            <b-button @click="ac">
                                {{text}} Cal
                            </b-button>
                        </div>
                        <!-- 
                                    <div v-if="okTDL2">
                                        <tdl :customname="customTDL2()" :title="TDL2">
                                        </tdl>
                                        <div class="tcenter">
                                            <b-button @click="ftdl2">
                                                Fermer TDL2
                                            </b-button>
                                        </div>
                                    </div> -->
                                    <calendar  style="height: calc(100vh - 180px);max-width: 96%;margin-left: 15px;
    overflow: scroll;" :droppable="true" :editable="true" :events="arr" :from="from()" :selectable="true" :to="toO()" @todolist::OK="okP = true;" @todolist::NOK="okP = false;re()" class="cal pdv-24 pdh-3" 
                                    >
                                    </calendar>
                               
       </b-col>
          <b-col cols="6" class="pdh-0">
        
                    <div class="tcenter" v-show="false">
                        <b-button @click="acT">
                            {{textT}} TodoListSession
                        </b-button>
                    </div>
                    <br>
                        <TDL :customName="customTDL2()" :title="nameTDL" class="" style="max-width: 96%;" :helper="Tdl2Helper">
                        </TDL>

                     </b-col>
    <!-- </div> -->
    </b-row>
</template>
<script>
    import TDL from "../TDL/TDL.vue"
import TK  from "../utils/tk.js"
	export default {
		data(){
			return {
				arr:[{
            title: 'Event1',
            start: '2018-04-10 12:30:00',
          end: '2018-04-10 16:30:00'
        }],
        afficheTL:false,
        afficheCal:false,
        text:"Afficher",
        textT:"Afficher",
        okTDL2:false,
        customTD2:null,
        okP:false,
        TDL2:"de la Session "+this.$store.state.sesscur.name,
        Tdl2Helper:"(pour les 3 mois)",
        nameC:{"month":"month","agendaWeek":"week","agendaDay":"day","listDay":"month",
"listWeek":"week",
"listMonth":"day"}
			}
		},
		components:{TDL:TDL},
		created(){
				this.$bus.$on("todoList::OK",this.tl)
				this.$bus.$on("todoList::NOK",()=>this.okP=false);
				// alert("HOMEEEsEE")
				console.log("HOME CREATED")
				this.$bus.$on("HOME::CS",()=>{
					console.log("HOME RECU")
					// this.okP=false;
					this.cS()

				});
			},
			computed:{
				nameTDL(){
				return 'TodoList '+this.TDL2;
			}
			},
		methods:{
			
			beforeCreate(){
				// console.log("bc Home")
			},
			updated(){
								// console.log("Up Home")

			},
			mounted(){
				this.$emit("loading")
				// console.log("mounted Home")
			},
			beforeMount(){
								// console.log("Bmounted Home")

			},
			ftdl2(){
				this.okTDL2 = false;
			},
			customTDL2(){
				// console.log("±customTDL2")
				// console.log(this.okP)
				return this.okP ? this.customTD2 : this.customSess();
			},
			re(){
				// console.log("re")
				this.TDL2=this.$store.state.sesscur.name;
				this.$emit("refreshTDL");

			},
			customSess(){
				var p="sess_"+this.custom();
				var self = this;
				var crud = self.$session.actual.todos;

				this.$fusion[p]={
					crud:crud,
					get:function(){
						return crud.GET();
					},
					add:function(p){
						return crud.POST(p);
					},
					delete:function(p){
						return crud.DELETE(id);
					},
					post:function(p){
						return crud.POST(p)
					}
				};
				console.log(this.$fusion);
				return p;
			},
			custom(){
				return moment().valueOf()+"";
			},
			tl(g){
				console.log("TL")
				console.log(this.okP)
				if (this.okP) {
					this.Tdl2Helper=""
				// return
				// setTimeout(()=>{},200)
				var p="td2_"+this.custom();
				var self = this;
				this.TDL2=g.title[0].toUpperCase()+g.title.slice(1)
				// console.log(g)
				// console.log(g.intervalStart.valueOf())
				var quoi = this.nameC[g.name] 

				//LISTE DES CRUD PERIODES

				var sessM = self.$session.actual.$p[quoi];
				var ts=g.intervalStart.valueOf();//TIMESTAMP
				// console.log(quoi)
				// console.log(ts)
				var idS = this.$soup.session.timestamp(quoi,ts)
				var crud =  sessM.
							list[idS].
							todos;
				// console.log(crud)
				// TK.objConcat(crud.optionsQ,{"timestamp":ts});
				this.$fusion[p]={
					crud:crud,
					get:function(){
						return crud.GET();
					},
					add:function(p){
						return crud.POST(p);
					},
					delete:function(p){
						return crud.DELETE(p);
					},
					post:function(p){
						return crud.POST(p);
					}
				};
				// console.log(this.$fusion);
				this.customTD2=p;
				// if (this.okTDL2) {
					console.log("refe")
					this.$emit("refreshTDL");
				// }
				// this.okTDL2 = true
			}
				// return p;
				// console.log(g)
				// console.log(g.intervalStart.valueOf())
			},
			from(){
				console.log("from")
				console.log(this.$options.filters["noMiliDate"](this.$store.state.sesscur.date));
				return this.moment(this.$store.state.sesscur.date);
			},
			toO(){
				return this.moment(this.$store.state.sesscur.date).add("3","months");
			},
			to(){
				console.log(this.$session.sessA[this.$store.state.sesscur.id].getTodos)
				return this.$session.sessA[this.$store.state.sesscur.id].getTodos;
			},
			acT(){
				this.textT = this.afficheTL ? "Afficher" : "Fermer";
				this.afficheTL = !this.afficheTL;
				// if (!this.afficheTL) {
				// 	// var self = this;
				// 	// setTimeout(()=>self.$emit("todayFC"), 500)
				// }
			},
			ac(){
				if (this.afficheCal) {
					this.text="Afficher"

				}else{
					this.text="Fermer"
					var self = this;
					setTimeout(()=>{self.$emit("todayFC");$(window).scrollTop($(self.$el).find(".cal").offset().top,1);}, 500)

					
				}
				this.afficheCal=!this.afficheCal
				
				
			},
			cS(){
				var self=this;
				console.log("clicked")
				this.$soup.$user.rmSessCurr(this).then((res)=>{
					// console.log(res)
					// if(res=="ok"){
						// console.log("oojojoj")
						self.$emit("homecs",{});
						self.$parent.$emit("homecs");
					// }
				});

			},
			 get (target, prop) {
        return this[prop] || 'MAGIC';
    }

		}

	}
</script>
<style scoped="" lang="scss">
// .bb{
// 	border-right: 1px solid $brown;
// }
    /**{
		text-align: center;

	}*/
</style>
import Crud from './crud.js'
import TK from '../components/utils/tk.js'
class Session extends Crud {
    constructor() {
        super("sessions");
        this._reset();
    }
    _reset(){
        super._reset()
        this.small=false
        this.sessA = [];
        this.isLoad = false;
        this.whenIsLoad = () => {return TK.promise()};
    }
    getTodos() {
        return this.get({
            "attr": ["todos"]
        }).then(console.log);
    }
    CustomConcreteCrudSuper(o){
        return super.CustomConcreteCrud(o)
    }
    //EACH SESSION HAS TODOS
    CustomConcreteCrud(o) {
        var secondObject         = this.CustomConcreteCrudSuper(o);
        secondObject.todos       = secondObject.CustomConcreteCrudSuper("todos");
        
        secondObject.small       = true
        secondObject.todos.small = true
        
        return secondObject;
    }
    load($this) {
        console.log("session::load")

        var me = this;
        //CONFIG SESSA N LAUCH WhenIsLoad
        return new Promise((r, e) => {
            if ($this.$store.state.todos && !this.small) {
                for (var i = 0; i < $this.$store.state.todos.length; i++) {
                    var g = $this.$store.state.todos[i];
                    this.sessA[g.id] = this.CustomConcreteCrud(g.id);
                }
                console.log("session::load/load")
                this.isLoad = true;
                var t = 0;
                if (this.sessA.length == 0) {
                    t = 500;
                }
                // setTimeout(() => {
                    // set
                    me.whenIsLoad(me).then(()=>{
        this.whenIsLoad = () => {return TK.promise()};
                        r();
                    });
                    // me.whenIsLoad = ()=>{}
                    // if ($this.$store.state.logged) {
                    //     $this.$soup.$user.reloadSessCur($this);
                    // }
                    
                // }, t);
            }
        });
    }
    configActualSess($id) {
        //IF LOGGER ACTUALIZE (CONFIG ACTUAL SESS CRUD) IFNOT SCHEDULE
        function actualize($id) {
            return ($this) => {
                return new Promise((r, e) => {
                    console.log("sessionJS::configActualSess:actualize")
                    $this.actual = $this.sessA[$id];
                    var a = $this.actual;
                    var p = $this.Vue.$periode;
                     a.p={}
                     a.$p={}
                    var ac = a.p
                    var acp = a.$p
                   
                    
                    // ac.day = a.super.CustomConcreteCrud("days");
                    // ac.week = a.super.CustomConcreteCrud("weeks");
                    a.months = a.CustomConcreteCrudSuper("months");
                    
                    
                    
                    acp.days = p.CustomConcreteCrud("days");
                    acp.weeks = p.CustomConcreteCrud("weeks",{"children":["days"]});
                    acp.months = p.CustomConcreteCrud("months",{"children":["weeks"]});

                    $this.Vue.$soup.session.loadPeriode().then(r);
                    
                });
            }
        }
        console.log("session:configActualSess")
        var $this = this;
        return new Promise((r, e) => {
            if ($this.isLoad) {
                actualize($id)($this).then(r);
            } else {
                console.log($id)
                $this.whenIsLoad = actualize($id);
                r()
            }
        })
    }
    configActualSessRM($this) {
        this.actual = null;
    }
}
export default Session;
import Crud from './crud.js'
import TK from '../components/utils/tk.js'
class Periode extends Crud {
    constructor() {
        super("");
        this._reset();
    }
    _reset(){
        super._reset()
         this.list={}
         this.pe=false;
    }
    //EACH P HAS TODOS
    CustomConcreteCrud(o,t) {
        var secondObject         = this.cloneCCC(o);
        if (t!=undefined && t) {
            secondObject.todos       = secondObject.cloneCCC("todos");        
        }
        return secondObject;
    }
    load($this) {
        var ac = this;
        $this.$day = ac.CustomConcreteCrud("days");
        $this.$week = ac.CustomConcreteCrud("weeks");
        $this.$month = ac.CustomConcreteCrud("months");

    }
    add(d){

        for (var i = 0; i < d.length; i++) {
            var id = d[i].id;
            var tt=this.CustomConcreteCrud(id,true);
            this.list[id]=tt;
        }
        
    }
    timestamp(){

    }
    // GET(){
    //     return new Promise((r,e)=>{
    //         super.GET(...arguments).then((res)=>{
    //         if(!this.pe){
    //         if (res.data!=undefined) {
    //             var d= res.data;
    //             for (var i = 0; i < d.length; i++) {
    //                 var p=d[i];
    //                 this.list[p.id]=this.CustomConcreteCrud()
    //             }
    //         }
    //     }else{
    //         r();
    //     }
    //     });
    //     });
    // }

}

export default Periode



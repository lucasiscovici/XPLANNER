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
         this.childrenF=[];
    }
    //EACH P HAS TODOS
    CustomConcreteCrud(o,params) {
       params= params == undefined ? [] : params;
       params["todos"]=true;
        var secondObject         = this.cloneCCC(o);
        if (params["todos"]) {
            secondObject.todos       = secondObject.cloneCCC("todos");        
        }
        if (params["children"]) {
            secondObject.childrenF=params["children"];
        }
        if (params["childrenF"]) {
            for (var i = 0; i < params["childrenF"].length; i++) {
                secondObject[params["childrenF"][i]]=secondObject.cloneCCC(params["childrenF"][i]);
            }
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
            // if (this.childrenF!=undefined) {


            var tt=this.CustomConcreteCrud(id,{"childrenF":this.childrenF});
            this.list[id]=tt;
        }
        // }
        
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



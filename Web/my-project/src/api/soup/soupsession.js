import soup from './soup.js'

import TK from '../../components/utils/tk.js'
class session extends soup {
    constructor(V, o) {
        super(V, o)
    }
    addToSessCur(quoi, ca, add) {
        var $th = this.Vue;
        console.log(this.Vue.store)
        var th = $th.store.state.sesscur;

        if(! quoi in th || th[quoi]==undefined){th[quoi]=[];}
        th[quoi] = add != undefined && add ?  th[quoi].concat(ca) : ca;
        
        $th.store.dispatch("setSessCurr", th);
    }
    loadPeriode() {
        var $this = this.Vue.$session;
        var a = $this.actual;
        var p = $this.Vue.$periode;
        var ac = a.p
        var acp = a.$p
        return new Promise((r, e) => {
            a.months.GET().then((res) => {
                acp.months.add(res.data)
                this.addToSessCur("months", res.data)
                // r()
                // return 
                var ml = TK.objToArr(acp.months.list)
                console.log(ml)
                for (var i = 0; i < ml.length; i++) {
                    var mi = ml[i];
                    mi.weeks.GET().then((res2) => {
                        acp.weeks.add(res2.data)
                        this.addToSessCur("weeks", res2.data, true)
                        var tyu=TK.objToArr(acp.weeks.list)
                        for (var j = 0; j < tyu.length; j++) {
                            var wi = tyu[j];
                            wi.days.GET().then((res3) => {
                                this.addToSessCur("days", res3.data, true)
                                acp.days.add(res3.data)
                            });
                        }
                    })
                }
                r()
            })
        })
    }
    timestamp(quoi,ts){
    	var $th = this.Vue;
        var th = $th.store.state.sesscur;
        console.log(moment(ts))
        var id = th[quoi].filter(d=>moment(ts).isSame(d.date,"day"));
    		console.log(id);
    	return id[0].id;
    }
}
export default session
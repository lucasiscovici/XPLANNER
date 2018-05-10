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
        th[quoi] = add != undefined && add ? th[quoi].concat(ca) : ca;
        $th.store.dispatch("setSessCurr", th);
    }
    loadPeriode() {
        var $this = this.Vue.$session;
        var a = $this.actual;
        var p = $this.Vue.$periode;
        var ac = a.p
        var acp = a.$p
        return new Promise((r, e) => {
            ac.month.GET().then((res) => {
                acp.month.add(res.data, true)
                this.addToSessCur("month", res.data)
                r()
                return 
                for (var i = 0; i < acp.month.list.length; i++) {
                    var mi = acp.month.list[i];
                    mi.GET().then((res2) => {
                        acp.week.add(res2.data)
                        this.addToSessCur("week", res2.data, true)
                        for (var j = 0; j < acp.week.list.length; j++) {
                            var wi = acp.week.list[j];
                            wi.GET().then((res3) => {
                                this.addToSessCur("month", res3.data, true)
                                acp.day.add(res3.data)
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
import ConcreteCrud from './crud.js'
class User extends ConcreteCrud {
    constructor(Vue) {
        super("users", Vue);
        this._reset()
    }
    _reset(){
    	super._reset()
        this.userId=null
		this.sess=null
		this.sesscurr=null
    }
    getSessions() {
        var $this = this;
        // this.Vue.$session.sessA={};
        return this.sess != null ? new Promise(function(resolve, reject) {
            $this.sess.GET().then((res) => {
                resolve({
                    "data": res.data
                })
            }, (err) => {
                reject(err)
            })
        }) : this._Error(this);
    }
    addSession(sf) {
        return this.sess.POST(sf);
    }
    PUTX(d){
        return this.CustomConcreteCrud("x").PUT(d);
    }
    loadID_Sess($user, $this) {
        // console.log($user)
        if ($user != null && $user.id != null) {
            // console.log("okSESS")
            // console.log(this)
            this.userId = this.CustomConcreteCrud($user.id);
            this.sess = this.userId.CustomConcreteCrud("sessions");
            // this.sessID = this.sess.CustomConcreteCrud("todos");
            // this.sess.todos = this.sess.CustomConcreteCrud("todos");
            this.sesscurr = this.userId.CustomConcreteCrud("sesscurr");
            // this.$session.load($this);
        }
    }
    changeSessCurr(s) {
        return this.sesscurr.POST({
            "id": s.id
        });
    }
    rmSessCurr() {
        //Utils.checkOrInit(this,"")
        return this.sesscurr.DELETE();
    }
    _Error($this) {
        // console.log($this.Vue.$soup)
        // $this.Vue.$soup.$connexion.logout()
        return new Promise(function(resolve, reject) {
            resolve([]);
        });
    }
}
export default User;
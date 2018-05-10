import soup from './soup.js'
import TK from '../../components/utils/tk.js'

class user extends soup {
    constructor(Vue, options) {
        super(Vue, options)
    }
    changeSessCurr(sess, $this) {
        // this.Vue.store.dispatch("setSessCurr",sess);
        return new Promise((r, e) => {
            $this.user.changeSessCurr(sess).then(res => {
                if (res.data == "ok") {
                    this.loadUserNSess($this,$this.$store.state.user.username).then(r)
                    // this._changingSessCurr(sess, $this);
                    // r()
                } else {
                    e();
                }
            })
        })
    }
    rmSessCurr($this) {
        var me = this;
        var sess = $this.$store.state.sesscur;
        return new Promise((r, e) => {
            $this.user.rmSessCurr().then(res => {
                console.log("kjkjkj")
                console.log(res)
                TK.log(me, res)
                if (res.data == "ok") {
                    me._rmvingSessCurr(sess, $this);
                    r()
                } else {
                    e();
                }
            })
        });
    }
    _rmvingSessCurr(ls, $this) {
        var st = $this.$store;
        var std = st.dispatch;
        console.log("ddkjlj")
        console.log(ls)
        if (ls != null) {
            std("rmSessCurr");
            // std("rmUISessCurr");
            $this.$session.configActualSessRM($this);
        }
    }
    _changingSessCurr(ls, $this) {
        // STORE -> CONFIG CRUD_SESSION
        return new Promise((r, e) => {
            var st = $this.$store;
            var std = st.dispatch;
            if (ls != null) {
                std("setSessCurr", ls).then(()=>{


                $this.$session.configActualSess(ls.id).then(r);
                });
            } else {
                r()
            }
        });
    }
    reloadSessCur($this) {
        // CHECK SESSCURR N GO TO CHANGE
        return new Promise((r, e) => {
            e = e == undefined ? () => {} : e;
            var ls = $this.$store.state.user.userInfo;
            if (!ls && !ls.sessCur) {
                if (e) {
                    e()
                }
            } else {
                ls = ls.sessCur
                this._changingSessCurr(ls, $this).then(r);
            }
        });
    }
    loadUser($this, username) {
        var me = this;
        return new Promise((r, e) => {
            var us = $this.$user;
            //CRUD -> USER  -> STORE  && CONFIG CRUD 
            us.getBy("username", username).then(res => {
                var da = res.data[0];
                var st = $this.$store;
                var std = st.dispatch;
                std("postUser", da).then(() => {
                    console.log("SOUPUSER::loadUser(54)")
                    us.loadID_Sess(da);
                    me.reloadSessCur($this)
                    r();
                });
            }, err => e())
        });
    }
    loadSess($this) {
        return new Promise((r, e) => {
            console.log("SOUPUSER::loadSess")
            //CRUD -> SESS  -> STORE
            $this.$user.getSessions().then(res => {
                console.log("SOUPUSER::loadSess/ok")
                $this.$store.dispatch("setTodos", res.data);
                r();
            }, err => e())
        });
    }
    loadUserNSess($this, username) {
        $this.$session.isLoad=false
        return new Promise((r, e) => {
            this.loadUser($this, username).then(res => {
                console.log("SoupUser::load")
                //GET SESSIONS OF USER
                this.loadSess($this).then(()=>{
                    $this.$session.load($this);
                    r();
                })
            });
        })
    }
}
export default user;
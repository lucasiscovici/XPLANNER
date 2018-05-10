import TK from "../components/utils/tk.js"
Object.defineProperty(Object.prototype, "length", {
    get: function () {
        return Object.keys(this).length;
    }
});

class Url{
    constructor(query,options) {
        this.query=query
        this.options=options == undefined ? {} : options
    }
    build(){
        // console.log("URL::")

        if (this.options.length>0) {
            var k = Object.entries(this.options).map(([p,v])=>p+"="+v);
            // console.log(k)
            return this.query+"?"+k.join("&");
        }
        return this.query;
    }
    static c(q,o){
        return new Url(q,o)
    }

}
class Crud {
    constructor(nom, Vue, store) {
        this.nom = nom;
        this.Vue = Vue;
        this.optionsQ={};
        // this.store=store
    }
    _reset(){
        this.optionsQ={}
    }
    axios() {
        return this.Vue.axios;
    }
    _blank(type,url,body){
        if (this.optionsQ.lenght>0) {
            TK.objConcat(url.options,this.optionsQ)
        }
        return this.axios()({
            url: url.build(),
            method: type,
            data:body==undefined ? {} : body
        });
    }
    _get(url) {
       
        return this._blank("GET",url);
    }
    GET(list) {
        var attr = []
        list = list==undefined ? {} : list
        if (list["attr"]) {
            attr = list["attr"].join(",")
            // delete list["attr"]
            list["attr"]=attr
        }
        
        var url = Url.c(this.nom,list)
        //console.log(attr.length)
        return this._get(url);
    }
    _patch(url,body){
        return this._blank("PATCH",url,body);
    }
    _put(url,body){
        return this._blank("PUT",url,body);
    }
    PUT(d,b){
        return this._put(Url.c(this.nom,d),b);
    }
    PATCH(d, custom) {
        if (custom != undefined && custom) {
            return this.CustomConcreteCrud(d.id).PATCH(d);
        }
        var url = Url.c(this.nom)
        return this._patch(url,d);
    }
    _post(url,body){
        return this._blank("POST",url,body);
    }
    POST(d,opts) {
        var url = Url.c(this.nom,opts)
        return this._post(url,d);
    }
    _delete(url,body){
        return this._blank("DELETE",url,body);
    }
    DELETE(id, custom) {
        if (id == undefined) {
            var url = Url.c(this.nom)
            return this._delete(url);
        }
        if (custom != undefined && custom == true) {
            return this.CustomConcreteCrud(id).DELETE();
        }
        var url = Url.c(this.nom)

        return this._delete(url,{"id": id});
    }
    getID(id) {
        return this._get(Url.c(this.nom + "/" + id));
    }
    xX(x, n, e) {
        var url = Url.c(this.nom + "/" + x,{"key":n,"value":e})
        return this._get(url);
    }
    getBy(n, e) {
        return this.xX("x", n, e)
    }
    checkExist(u, d) {
        return this.xX("check", "username", u).then(g => {
            console.log(g.data);
            d(g.data)
        });
    }
    clone() {
        return Object.assign(Object.create(Object.getPrototypeOf(this)), this)
    }
    cloneCCC(o) {
        var nObject = Object.assign(Object.create(Object.getPrototypeOf(this)), this)
        if (nObject._reset != undefined) {
            nObject._reset()
        }
        if (o != undefined) {
            nObject._addToName(o)
        }
        return nObject;
    }
    _addToName(o) {
        this.nom += "/" + o;
    }
}
export default class ConcreteCrud extends Crud {
    constructor(qui, Vue) {
        super(qui, Vue);
    }
    CustomConcreteCrud(o) {
        this.last=o
        return this.cloneCCC(o)
    }
}
// export default ConcreteCrud;
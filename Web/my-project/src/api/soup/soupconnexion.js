import soup from './soup.js'
class connexion extends soup{
  constructor(Vue,options){
    super(Vue,options)
  }
}
connexion.prototype.logout= function(args) {
  this.Vue.store.dispatch("rmInfos");
  return this.Vue.auth.logout(args);
}

export default connexion;


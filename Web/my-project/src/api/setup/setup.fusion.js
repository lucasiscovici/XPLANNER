

(function () {


   module.exports = function plugin(Vue,options) {
        // var crud = options["crud"]
        // console.log(options)
        Vue.prototype["$fusion"] = require("../fusion/fusion.js").default
        Vue.prototype["fusion"] = Vue.prototype["$fusion"]; 
        Vue["$fusion"] = Vue.prototype["$fusion"];
        Vue["fusion"]=Vue["$fusion"];
        // console.log(Vue.$store)
        // for(var index in options.soups) { 

        //     var attr = options.soups[index]; 
        // // if(index=="crud"){
        // //   continue
        // //  }else{
        //     var t=new attr(Vue,options.drivers);
        //     t.Vue=Vue;
        //     // console.log(index)
        //     var key="$"+index
        //         Vue["$fusion"][key]=t
        //         Vue["$fusion"][index]=t
        //          // console.log(Vue[index])
        //     // }
             
        //      Vue.prototype["$fusion"][key] = Vue["$fusion"][key]
        //      Vue.prototype["$fusion"][index] = Vue["$fusion"][index]
        //     Vue.prototype["fusion"]=Vue.prototype["$fusion"]
        //     Vue["fusion"]=Vue["$fusion"]
        // }
// console.log(Vue.$user)
// console.log(Vue.$session) 
       
}

})();

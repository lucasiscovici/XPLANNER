

// odule.exports.Crud = Crud
(function () {


   module.exports = function plugin(Vue,options) {
		// var crud = options["crud"]
        // console.log(options)
        Vue.prototype["$soup"] = {}; 
        Vue["$soup"] = {};
        // console.log(Vue.$store)
		for(var index in options.soups) { 
            console.log(index)
    		var attr = options.soups[index]; 
            console.log(attr)
    	// if(index=="crud"){
    	// 	 continue
    	// 	}else{
    		var t=new attr(Vue,options.drivers);
    		t.Vue=Vue;
            // console.log(index)
            var key="$"+index
    			Vue["$soup"][key]=t
                Vue["$soup"][index]=t
                 // console.log(Vue[index])
    		// }
    		 
    		 Vue.prototype["$soup"][key] = Vue["$soup"][key]
             Vue.prototype["$soup"][index] = Vue["$soup"][index]
            Vue.prototype["soup"]=Vue.prototype["$soup"]
            Vue["soup"]=Vue["$soup"]
		}
// console.log(Vue.$user)
// console.log(Vue.$session) 
       
}

//         if (exports != undefined && typeof exports == "object") {
//   module.exports = plugin
// } else if (typeof define == "function" && define.amd) {
//   define([], function(){ return plugin })
// } else if (window.Vue && window.axios) {
//   Vue.use(plugin, window.axios)
// }

	
})();
// export default Crud

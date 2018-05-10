

// odule.exports.Crud = Crud
(function () {


   module.exports = function plugin(Vue,options) {
		vue = Vue
		// var crud = options["crud"]
        // console.log(options)
		for(var index in options) { 

    		var attr = options[index]; 
    	// if(index=="crud"){
    	// 	 continue
    	// 	}else{
    		var t=new attr(Vue);
    		t.Vue=Vue;
             var key="$"+index
            // console.log(index)
    			Vue[key]=t
                Vue[index]=t
                 // console.log(Vue[index])
    		// }
    		
    		 Vue.prototype[key] = Vue[key]
             Vue.prototype[index] = Vue[key]
        
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

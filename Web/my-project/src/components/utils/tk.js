
export default  { 
	 objConcat : function(){
    for (var i=1; i<arguments.length; i++)
       for (var a in arguments[i])
         arguments[0][a] = arguments[i][a];
   return arguments[0];
},
objToArr: function(obj){
var f=[]
for (var j in Object.keys(obj)) {
	f.push(obj[j])
}
return f;
},
	log :function(thi,mess) {
		console.log("DEBUG: "+this.constructor.name+"|"+thi.toString()
       .substr( 0, thi.toString().indexOf( "(" ) )
       .replace( "function ", "" ))
		// body...
	},
promise : function(d,ok){
	ok = ok == undefined ? true : ok;
	return new Promise((resolve, reject) => {
    // console.log('Initial');
    if(!ok){
    	reject(d)
    }else{
    resolve(d);
	}
})
},
Objrmv:function(obj,what) {
	// body...
	for (var i = 0; i < obj.length; i++) {
		delete obj[i][what]
	}
	return obj;

},
	randomString : function() {
		return Math.random().toString(36).substr(0,10);
	},
	swap : function(json){
  var ret = {};
  for(var key in json){
    ret[json[key]] = key;
  }
  return ret;
},
noKeys: function(obj,keys,deep){
	if(deep != undefined && deep){
				var arr=[];
			for (var i = 0; i < obj.length; i++) {
				arr.push(this.noKeys(obj[i],keys,false));
			}
			return arr;
	}else{
	for (var i = 0; i < keys.length; i++) {
		delete obj[keys[i]];
	}
	return obj;
}	
},
	changeKeyName : function(obj, newKeys,inverted, deep) {
		// console.log("DEBUG::changeKeyName")
		// console.log(obj)
		// console.log(newKeys)
		// console.log(inverted)
		// console.log(deep)
	function swap(json){
  var ret = {};
  for(var key in json){
    ret[json[key]] = key;
  }
  return ret;
}
var nk = newKeys;
		var  invert = inverted == undefined ? false : inverted;
		if (invert) {
			nk=swap(newKeys);
			console.log(nk)
		}

		if (deep != undefined && deep == true) {
			var arr=[];
			for (var i = 0; i < obj.length; i++) {
				arr.push(this.changeKeyName(obj[i],nk,false,false));
			}
			return arr;

		}else{
			const keyValues = Object.keys(obj).map(key => {
		    const newKey = nk[key] || key;
		    return { [newKey]: obj[key] };
			
	  });
				  return Object.assign({}, ...keyValues);

		}
	},
	arrConcat:function(a,b,co) {
		return  co!=undefined && co ? b.concat(a) : a.concat(b);
	},
	unObs:function(obj,f) {
		for (var i = 0; i < obj.length; i++) {
			obj[i]=Object.freeze(obj[i])
		}
		if (f!=undefined && f){
			return obj
		}
	},
	val2key : function(val,array){
	    for (var key in array) {
	        if(array[key] == val){
	            return key;
	        }
	    }
	 return false;
	},
	upperF: function(f){
		return f[0].toUpperCase()+f.slice(1)
	}
}


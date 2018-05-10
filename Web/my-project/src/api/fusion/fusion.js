var fusion ={
	get :function(target, name) {
		console.log("fusion")
		console.log(target)
		console.log(name)
        if (name in target) {
            return target[name];
        }
        // if (name == 'length') {
        //     return Infinity;
        // }
        return target.arr[name];
    }
}

class FUSION{
	constructor(){
		this.arr={}
	}
	add(k,d){
		this.arr[k]=d;
	}
	 asProxy ()
    {
        let handler = {
            /**
             * This function is called whenever any property on the Proxy 
             * is called.
             * 
             * @param target the "parent" object; the object the proxy 
             *        virtualizes
             * @param prop the property called on the Proxy
             */
            get: function (target, prop)

            {
            	// console.log(target)
            	// console.log(prop)
                /* This will return the property on the "parent" object
                 */

                if (typeof target[prop] !== 'undefined')
                    return target[prop]
               	if (typeof target.arr[prop] !== 'undefined')
               		return target.arr[prop]
               	
               	target[prop]={}


                // TODO: implement custom logic
            },
             set: function(target, prop, valeur) {
             	console.log(target)
             	console.log(prop)
             	console.log(valeur)
             	target.arr[prop]=valeur;
             	return true;
             }
        }

        return new Proxy(this, handler)
    }

}

export default new FUSION().asProxy();
// export default new Proxy(new FUSION(), fusion).default;
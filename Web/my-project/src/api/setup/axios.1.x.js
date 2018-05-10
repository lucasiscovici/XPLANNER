module.exports = {
  _init: function () {
      if ( ! this.options.Vue.axios) {
          return 'axios.js : Vue.axios must be set.'
      }
  },

  _interceptor: function (req, res) {
    var _this = this;

    if (req) {
      this.options.Vue.axios.interceptors.request.use(function (request) {
        // console.log("ICI");
        request.headers['Access-Control-Allow-Origin']= '*'
        req.call(_this, request);
        
        return request;
      }, function (error) {
        req.call(_this, error.request);
        return Promise.reject(error);
      })
    }

    if (res) {
      this.options.Vue.axios.interceptors.response.use(function (response) {
     _this.options.Vue.store.state.pbResseau=false;
        res.call(_this, response);
  
  
        return response;
      }, function (error) {
        // console.log(error)
        // console.log(JSON.stringify(error))
        if(error.response==undefined || error.response.status=="403"){
          // _this.options.Vue.store.state.pbResseau=error.response==undefined;
          _this.options.Vue.$soup.$connexion.logout({makeRequest:error.response!=undefined,redirect:_this.options.authRedirect})
        }
           
        
        if (error && error.response) {
          res.call(_this, error.response);
        }
        
        return Promise.reject(error);
      })
    }
  },

  _invalidToken: function (res) {
    if (res.status === 401) {
      return true;
    }
  },

  _httpData: function (res) {
    return res.data || {};
  },

  _http: function (data) {
    var http = this.options.Vue.axios(data);

    http.then(data.success, data.error);

    return http;
  },

  _getHeaders: function (res) {
    return res.headers;
  },

  _setHeaders: function (req, headers) {
    req.headers.common = Object.assign({}, req.headers.common, headers);
  }
}

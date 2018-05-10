module.exports = {
    
    request: function (req, token) {
        console.log(token)
        this.options.http._setHeaders.call(this, req, {"x-auth-token": token});
    },
    
    response: function (res) {
        var headers = this.options.http._getHeaders.call(this, res),
            token = headers["x-auth-token"];
        console.log(headers)
        if (token) {
            console.log(token)
            // token = token.split(/Bearer\:?\s?/i);
            return token.trim();
        }
    }
};
package com.LUCUX.XPlanner.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.LUCUX.XPlanner.conf.AuthTokenFilter;
import com.LUCUX.XPlanner.response.UserTransfer;


public class ResponseEntityT  {
	
	
	static public<T extends UserTransfer>  ResponseEntity<T>  c(T body) {
	
		MultiValueMap<String, String> args = new HttpHeaders();
		args.add(AuthTokenFilter.authTokenHeaderName, body.getToken());
			args.add("Access-Control-Expose-Headers", AuthTokenFilter.authTokenHeaderName);
		return  new ResponseEntity<T>(body,args,body.getStatus());
		// TODO Auto-generated constructor stub
	}



	 
	

}

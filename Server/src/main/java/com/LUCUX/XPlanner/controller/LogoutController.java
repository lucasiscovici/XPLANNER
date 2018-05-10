package com.LUCUX.XPlanner.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.LUCUX.XPlanner.model.User;
import com.LUCUX.XPlanner.service.UserService;
import com.LUCUX.XPlanner.util.Constants;
import com.LUCUX.XPlanner.util.CustomError;
import com.LUCUX.XPlanner.util.TokenUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.tools.rmi.ObjectNotFoundException;

//@RestController
//@RequestMapping("/logout")
public class LogoutController  {

	
	//@Autowired
	private UserService userService;
	
	//@PostMapping
	public ResponseEntity<String> logout(HttpServletRequest request) {
		System.out.println("logout");
		String token = request.getHeader("x-auth-token");
		User us = userService.findByUsernameOrEmail(TokenUtil.getUserNameFromToken(token));
		us.tokens.stream().filter(list -> list.getToken().equals(token)).findFirst().get().setValable(false);
		userService.save(us);
		return new ResponseEntity<String>("",HttpStatus.OK);
	}

}

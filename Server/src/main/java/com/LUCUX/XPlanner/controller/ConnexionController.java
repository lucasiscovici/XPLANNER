package com.LUCUX.XPlanner.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.LUCUX.XPlanner.model.Token;
import com.LUCUX.XPlanner.model.User;
import com.LUCUX.XPlanner.repository.TokenRepository;
import com.LUCUX.XPlanner.request.AuthenticationRequest;
import com.LUCUX.XPlanner.response.UserTransfer;
import com.LUCUX.XPlanner.security.CustomUserDetailsService;
import com.LUCUX.XPlanner.service.UserService;
import com.LUCUX.XPlanner.util.Constants;
import com.LUCUX.XPlanner.util.TokenUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController

public class ConnexionController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	@Qualifier("customUserDetailsService")
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private UserService userService ;
	@Autowired
	private TokenRepository tokenRepository ;
	
	@PostMapping(value="/login/authenticate" )
	@ApiOperation(value = "authenticate")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constants.SUCCESS, response = UserTransfer.class),
			@ApiResponse(code = 403, message = Constants.FORBIDDEN),
			@ApiResponse(code = 422, message = Constants.USER_NOT_FOUND),
			@ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED) })
	public ResponseEntity<UserTransfer> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
		try {
			String username = authenticationRequest.getUsername();
			String password = authenticationRequest.getPassword();
			
			System.out.println("username "+username+" passwd "+password);
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
			Authentication authentication = this.authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

			List<String> roles = new ArrayList<>();

			for (GrantedAuthority authority : userDetails.getAuthorities()) {
				roles.add(authority.toString());
			}
			String tok = TokenUtil.createToken(userDetails);
			UserService userService = customUserDetailsService.getUserService();
			User user = userService.findByUsernameOrEmail(username);
			Token tokene = new Token();
			tokene.token = tok;
			tokene.setValable(true);
			user.tokens.add(tokene);
			saveUserTokenIfNotFound(tokene);
			saveUserInfoIfNotFound(user);
			return ResponseEntityT.c(new UserTransfer(userDetails.getUsername(), roles,
					tok, HttpStatus.OK));

		} catch (BadCredentialsException bce) {
			return new ResponseEntity<UserTransfer>(new UserTransfer(), HttpStatus.UNAUTHORIZED);

		} catch (Exception e) {
			
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			e.printStackTrace();
			throw e;
			//return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}
	@Transactional
    private final User saveUserInfoIfNotFound(User userInfo) {
		
        User user = userService.save(userInfo);
        return user;
    }
	@Transactional
    private final Token saveUserTokenIfNotFound(Token userInfo) {
		
        Token user = tokenRepository.save(userInfo);
        return user;
    }
	
	@PostMapping(value="/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		System.out.println("logout");
		String token = request.getHeader("x-auth-token");
		User us = userService.findByUsernameOrEmail(TokenUtil.getUserNameFromToken(token));
		us.tokens.stream().filter(list -> list.getToken().equals(token)).findFirst().get().setValable(false);
		userService.save(us);
		return new ResponseEntity<String>(""+us.getId(),HttpStatus.OK);
	}

}

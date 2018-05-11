package com.LUCUX.XPlanner.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.*;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.LUCUX.XPlanner.TEST.TestBase;
import com.LUCUX.XPlanner.conf.Configuration;
import com.LUCUX.XPlanner.model.User;
import com.LUCUX.XPlanner.model.IDS;
import com.LUCUX.XPlanner.model.Session;
import com.LUCUX.XPlanner.model.UserInfo;
import com.LUCUX.XPlanner.repository.UserRepository;
import com.LUCUX.XPlanner.service.SessionService;
import com.LUCUX.XPlanner.service.UserService;
import com.LUCUX.XPlanner.service.impl.UserServiceImpl;
import com.LUCUX.XPlanner.util.Constants;
import com.LUCUX.XPlanner.util.CustomError;
import com.LUCUX.XPlanner.util.TK;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.tools.rmi.ObjectNotFoundException;
import java.util.function.Supplier;

@RestController
@RequestMapping("/users")
public class UserController  {


	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRep;
	
	@Autowired
	private SessionService sessionService;
	
	@ApiOperation(value="get users")
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUsers(@RequestParam MultiValueMap<String,String> params) {
		try {
			Boolean joined = TK.checkKeyBoolInParams(Configuration.Joined,params);
			System.out.println(joined);
			ObjectWriter mapper = ! joined ? TK.JSONMapper(TK.toMapSSL(
															TK.s(Configuration.FilterJSONJoined,
																	UserInfo.getFiltered)
															)
														  ): TK.JSONMapper(
																  TK.toMapSSL(
																		  TK.s(Configuration.FilterJSONJoined))
																  );
			
			List<User> users = userService.getUsers();
			
			String us= TK.toJSON(users, mapper);
			
			return new ResponseEntity<String>(us, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			CustomError error = new CustomError("An error has occured");
			return new ResponseEntity<CustomError>(error, HttpStatus.EXPECTATION_FAILED);
		}
		
	}
	
	@ApiOperation(value = "Get user by id")
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constants.SUCCESS, response = User.class),
			@ApiResponse(code = 403, message = Constants.FORBIDDEN),
			@ApiResponse(code = 422, message = Constants.USER_NOT_FOUND),
			@ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED) })
	public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId) {

		try {
			User user = userService.findById(userId);
			return new ResponseEntity<User>(user, HttpStatus.OK);

		} catch (ObjectNotFoundException onfe) {
			onfe.printStackTrace();
			CustomError error = new CustomError("User with id = " + userId + " is not found");
			return new ResponseEntity<CustomError>(error, HttpStatus.UNPROCESSABLE_ENTITY);

		} catch (Exception e) {
			e.printStackTrace();
			CustomError error = new CustomError("An error has occured");
			return new ResponseEntity<CustomError>(error, HttpStatus.EXPECTATION_FAILED);
		}

	}
	@PostMapping(value="/create")
	public ResponseEntity<String> post(@RequestBody User user,HttpServletRequest req) throws ParseException, IOException {
	

		User us = userService.createUserIfNotFound(user.getUsername(), user.getPassword());
		ObjectWriter mapper = TK.JSONMapper(TK.toMapSSL(
				TK.s(Configuration.FilterJSONJoined,
			UserInfo.getFiltered),
				TK.s("USERFilter",
				User.getFiltered),
				TK.s("SessionFilter",Session.getFiltered)
				)
			);
		String uds= TK.toJSON(us,mapper);
		
		return new ResponseEntity<String>(uds, HttpStatus.OK);
	}
	
	@GetMapping(value="/x")
	public ResponseEntity<String> gettX(@RequestParam("key") String key,@RequestParam("value") String value) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		List<User> users = userService.getX(key,value);
		ObjectWriter mapper = TK.JSONMapper(TK.toMapSSL(
												TK.s(Configuration.FilterJSONJoined,
											UserInfo.getFiltered),
												TK.s("USERFilter",
												User.getFiltered)
												,TK.s("SessionFilter",Session.getFiltered)
												)
											);

		String uds= TK.toJSON(users,mapper);

		return new ResponseEntity<String>(uds, HttpStatus.OK);
	}
	
	@GetMapping(value="/check")
	public ResponseEntity<String> check(@RequestParam("key") String key,@RequestParam("value") String value) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		List<User> users = userService.getX(key,value);

		String uds= "{\"ok\":"+String.valueOf(users.size()>0)+"}";
		return new ResponseEntity<String>(uds, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}/sessions")
	public ResponseEntity<String> sessions(@PathVariable("id") long id) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException,ObjectNotFoundException {
		List<Session> sess = userService.findById(id).userInfo.getSess(); 
		System.out.println("IDKKK: "+userService.findById(id).userInfo.getId());
		for (Session k : sess) {
			System.out.println(k.getId());
		}

		ObjectWriter mapper = TK.JSONMapper(
									TK.toMapSSL(
										 TK.s("SessionFilter",Session.getFiltered)));
		String us= TK.toJSON(sess, mapper);
		return new ResponseEntity<String>(us,HttpStatus.OK);
	}
@GetMapping(value="/{id}/userinfo")
	public ResponseEntity<String> ui(@PathVariable("id") long id) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException,ObjectNotFoundException {
		UserInfo sess = userService.findById(id).userInfo; 

		System.out.println(sess.getSess().size());

		ObjectWriter mapper = TK.JSONMapper(TK.toMapSSL(
										 TK.s("SessionFilter",new String[]{}),
										 TK.s("JoinedFilter",new String[]{})));
		String us= TK.toJSON(sess, mapper);
		us+=" "+sess.getSess().size();
		return new ResponseEntity<String>(us,HttpStatus.OK);
	}
	@PostMapping(value="/{id}/sessions")
	public ResponseEntity<String> sessions(@PathVariable("id") long id,@RequestBody Session sess) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException,ObjectNotFoundException {
		User us = userService.findById(id); 
		sess.ui=us.userInfo;
		sess.months = TK.createStructureMWD2(sess.date);
		us.userInfo.sess.add(sess);
		userService.save(us);
		
		// ObjectWriter mapper = TK.JSONMapper(
									// TK.toMapSSL(
										 // TK.s("SessionFilter",Session.getFiltered)));
		// String us= TK.toJSON(sess, mapper);
		return new ResponseEntity<String>(""+sess.getId(),HttpStatus.OK);
	}
	@GetMapping(value="/pwd")
	public ResponseEntity<String> gePa(@RequestParam("pwd") String sess) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException,ObjectNotFoundException {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
		String encodedPassword = passwordEncoder.encode(sess);  
		// ObjectWriter mapper = TK.JSONMapper(
									// TK.toMapSSL(
										 // TK.s("SessionFilter",Session.getFiltered)));
		// String us= TK.toJSON(sess, mapper);
		return new ResponseEntity<String>(encodedPassword,HttpStatus.OK);
	}
	
	@PostMapping(value="/{id}/sesscurr")
	public ResponseEntity<String> changeSessCurr(@PathVariable("id") long id,@RequestBody IDS ids) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException,ObjectNotFoundException {
		User us = userService.findById(id);
		//Security.check()
		if (us!=null) {
			Supplier<Stream<Session>> sessSupplier = () -> us.userInfo.getSess().stream().filter(h->h.getId()==ids.getId());
			long c = sessSupplier.get().count();
			if(c!=0) {
				us.userInfo.sessCur=sessSupplier.get().findFirst().get();
				userRep.save(us);
				return new ResponseEntity<String>("ok",HttpStatus.OK);
			}
		}
	
	return new ResponseEntity<String>("Nok",HttpStatus.BAD_REQUEST);
	}
	@PutMapping("{id}/x")
	public ResponseEntity<String> changeAttr(@PathVariable("id") long id,@RequestParam("key") String key,@RequestParam("value") String value) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException,ObjectNotFoundException {
		User us = userService.findById(id);
		set(us, key, value);
		userRep.saveAndFlush(us);
		return new ResponseEntity<String>("ok",HttpStatus.OK);
		
	}
	public static boolean set(Object object, String fieldName, Object fieldValue) {
	    Class<?> clazz = object.getClass();
	    while (clazz != null) {
	        try {
	            Field field = clazz.getDeclaredField(fieldName);
	            field.setAccessible(true);
	            field.set(object, fieldValue);
	            return true;
	        } catch (NoSuchFieldException e) {
	            clazz = clazz.getSuperclass();
	        } catch (Exception e) {
	            throw new IllegalStateException(e);
	        }
	    }
	    return false;
	}
	@DeleteMapping(value="/{id}/sesscurr")
	public ResponseEntity<String> changeSessCurr(@PathVariable("id") long id) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException,ObjectNotFoundException {
		User us = userService.findById(id);
		//Security.check()
		if (us!=null) {
			UserInfo ui = us.userInfo;
			ui.sessCur=null;
			userService.save(us);
			return new ResponseEntity<String>("ok",HttpStatus.OK);
			
		}
	
	return new ResponseEntity<String>("Nok",HttpStatus.BAD_REQUEST);
	}
	
}

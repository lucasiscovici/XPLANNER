package com.LUCUX.XPlanner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.LUCUX.XPlanner.Custom.GetMappingB;
import com.LUCUX.XPlanner.conf.Configuration;
import com.LUCUX.XPlanner.model.UserInfo;
import com.LUCUX.XPlanner.service.UserInfoService;
import com.LUCUX.XPlanner.service.impl.UserInfoServiceImpl;
import com.LUCUX.XPlanner.repository.UserInfoRepository;
import com.LUCUX.XPlanner.util.Constants;
import com.LUCUX.XPlanner.util.CustomError;
import com.LUCUX.XPlanner.util.TK;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
//@RequestMapping("TODOS")
public class UserInfoController  {
	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private UserInfoRepository userInfoRepository;
	//GET
			@GetMappingB(Configuration.userinfos+"s")
			public String get(@RequestParam MultiValueMap<String,String> params) 
					throws  JsonProcessingException {
				
				Boolean joined = TK.checkKeyBoolInParams(Configuration.Joined,params);
				System.out.println(joined);
				ObjectWriter mapper = ! joined ? TK.JSONMapper(TK.toMapSSL(TK.s(Configuration.FilterJSONJoined,UserInfo.getFiltered))) : TK.JSONMapper(TK.toMapSSL(TK.s(Configuration.FilterJSONJoined)));
				
				List<UserInfo> ui =  userInfoRepository.findAll();
				
				return TK.toJSON(ui, mapper);
			}
			
			//GET{id}
			@GetMappingB(Configuration.userinfos+"/{id:[\\d]+}")
			public String getID(@PathVariable("id") long id ,@RequestParam MultiValueMap<String,String> params) 
					throws JsonProcessingException {
					Boolean joined = TK.checkKeyBoolInParams(Configuration.FilterJSONJoined,params);
				
					ObjectWriter mapper = ! joined ? TK.JSONMapper(TK.toMapSSL(TK.s(Configuration.FilterJSONJoined,UserInfo.getFiltered))) : TK.JSONMapper();
					
					UserInfo ui =  userInfoRepository.findById(id).get();		
				return TK.toJSON(
						ui,
						mapper
						);
			}
			
			

			//GET{name}
			@GetMappingB(Configuration.userinfos+"/{name:[a-z0-9]+[a-z][a-z0-9]+}")
			public String getName(@PathVariable("name") String name ,@RequestParam MultiValueMap<String,String> params) 
					throws  JsonProcessingException {
					Boolean joined = TK.checkKeyBoolInParams(Configuration.FilterJSONJoined,params);
					
					ObjectWriter mapper = joined ? TK.JSONMapper(TK.toMapSSL(TK.s(Configuration.FilterJSONJoined,UserInfo.getFiltered))) : TK.JSONMapper();
					
					UserInfo ui =  userInfoRepository.findBy("name",name);	
				return TK.toJSON(
						ui,
						mapper
						);
			}
			
			@PostMapping(value=Configuration.userinfos)
			public ResponseEntity<Void> postUser(@RequestBody UserInfo user, UriComponentsBuilder ucBuilder,@RequestParam MultiValueMap<String,String> params) {
				if(userInfoRepository.existsById(user.getId())){
			        System.out.println("A User with name "+user.user.getUsername()+" already exist");
			        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			    }
			 
			    userInfoRepository.save(user);
			     
			    HttpHeaders headers = new HttpHeaders();
			    headers.setLocation(ucBuilder.path(Configuration.userinfos+"/{id}").buildAndExpand(user.getId()).toUri());
			    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
				
			}
			

			@PutMapping(Configuration.userinfos+"/{id:[0-9]+}")
			public ResponseEntity<Void> putUser(@PathVariable("id") long id,@RequestBody UserInfo user, UriComponentsBuilder ucBuilder ,@RequestParam MultiValueMap<String,String> params) {
				if(! userInfoRepository.existsById(user.getId())){
			        System.out.println("A User with name "+user.user.getUsername()+" not already exist");
			        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			    }
				userInfoRepository.save(user);
				// HttpHeaders headers = new HttpHeaders();
				    //headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
				 return new ResponseEntity<Void>( HttpStatus.OK);
			}
}

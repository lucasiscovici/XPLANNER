package com.LUCUX.XPlanner.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.LUCUX.XPlanner.model.Month;
import com.LUCUX.XPlanner.model.Session;
import com.LUCUX.XPlanner.model.Todo;
import com.LUCUX.XPlanner.model.TodoWrapper;
import com.LUCUX.XPlanner.model.Todos;
import com.LUCUX.XPlanner.model.UserInfo;
import com.LUCUX.XPlanner.service.SessionService;
import com.LUCUX.XPlanner.service.impl.SessionServiceImpl;
import com.LUCUX.XPlanner.repository.SessionRepository;
import com.LUCUX.XPlanner.repository.TodoRepository;
import com.LUCUX.XPlanner.repository.UserInfoRepository;
import com.LUCUX.XPlanner.util.Constants;
import com.LUCUX.XPlanner.util.CustomError;
import com.LUCUX.XPlanner.util.TK;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping("sessions")
public class SessionController  {
	@Autowired
	private SessionService sessionService;
	@Autowired
	private TodoRepository tr;
	@Autowired
	private SessionRepository sessionRepository;
	
	@Autowired
	private UserInfoRepository uiR;
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable("id") long id) {
		Session s = sessionRepository.getOne(id);
		
		UserInfo ui = s.getUi();
		if( ui.sessCur==s){
			ui.sessCur=null;
			//uiR.save(ui);
		}
		
		ui.getSess().remove(s);
		s.ui=null;
		uiR.save(ui);
		//sessionRepository.delete(s);
		return  new ResponseEntity<String>(""+id,HttpStatus.OK);
	
		
	}
	
	@GetMapping(value="{id}")
	public ResponseEntity<String> G(@PathVariable("id") long id,@RequestParam MultiValueMap<String,String> params) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException,ObjectNotFoundException {
		
		
		Optional<Session> sess = sessionRepository.findById(id); 
		String us ="error";
		if(sess.isPresent()) {
		List<String> sessF= new ArrayList<String>();
		if (params.containsKey("attr")) {
			sessF.addAll(TK.toList(params.getFirst("attr").split(",")));
		}

		ObjectWriter mapper = sessF.size() > 0 ? 
			  TK.JSONMapper(TK.toMapSSL(
						TK.s("SessionFilter",TK.filter(Session.getFiltered,sessF))
							)
						)
			: TK.JSONMapper(TK.toMapSSL(
					TK.s("SessionFilter",Session.getFiltered),
					TK.s("JoinedFilter",UserInfo.getFiltered)
					)
				);
		
		us= TK.toJSON(sess.get(), mapper);
		//us+=" "+sess.getSess().size();
		}
		return new ResponseEntity<String>(us,HttpStatus.OK);
	}
	@GetMapping(value="{id}/todos")
	public ResponseEntity<String> Gtodos(@PathVariable("id") long id,@RequestParam MultiValueMap<String,String> params) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException,ObjectNotFoundException {
		List<Todo> t = sessionRepository.findById(id).get().getTodos();
		String us = TK.toJSON(t);
		return new ResponseEntity<>(us,HttpStatus.OK);
	}
	@PostMapping(value="{id}/todos",consumes="application/json",produces="application/json")
	@ResponseBody
	public ResponseEntity<String> Ptodos(@PathVariable("id") long id,@RequestBody TodoWrapper todos) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException,ObjectNotFoundException {
		Session sess = sessionRepository.findById(id).get();
		List<Todo> lt = todos;
			System.out.println(lt.size());
			//System.out.println(todos.get(0).getName());
		//lt.forEach(d->d.setId(null));
		sess.getTodos().clear();
		for (Todo todo : lt) {
			tr.save(todo);
		}
		sess.getTodos().addAll(lt);
		
		sessionRepository.save(sess);
		Session sess2 = sessionRepository.findById(id).get();
		String us = TK.toJSON(sess2.getTodos());
		return new ResponseEntity<>(us,HttpStatus.OK);
	}
	@GetMapping(value="{id}/todos/{idt}")
	public ResponseEntity<String> Gtodo(@PathVariable("id") long id,@PathVariable("idT") long idT) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException,ObjectNotFoundException {
		Todo t = sessionRepository.findById(id).get().getTodos().stream().filter(d-> Long.compare(d.getId(),idT)==0).findFirst().get();
		String us = TK.toJSON(t);
		return new ResponseEntity<>(us,HttpStatus.OK);
	}
	@DeleteMapping(value="{id}/todos/{idt}")
	public ResponseEntity<String> Dtodos(@PathVariable("id") long id,@PathVariable("idT") long idT) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException,ObjectNotFoundException {
		Session t = sessionRepository.findById(id).get();
		t.setTodos(TK.StreamToArray(t.getTodos().stream().filter(d-> Long.compare(d.getId(),idT)!=0)));
		sessionRepository.saveAndFlush(t);
		return new ResponseEntity<>(t.getId()+"",HttpStatus.OK);
	}
	@GetMapping(value="{id}/months")
	public ResponseEntity<String> GMtodo(@PathVariable("id") long id) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException,ObjectNotFoundException {
		List<Month> t = sessionRepository.findById(id).get().getMonths();
		String us = TK.toJSON(t);
		return new ResponseEntity<>(us,HttpStatus.OK);
	}
	
}

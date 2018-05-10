package com.LUCUX.XPlanner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.LUCUX.XPlanner.model.Month;
import com.LUCUX.XPlanner.model.Session;
import com.LUCUX.XPlanner.model.Todo;
import com.LUCUX.XPlanner.model.TodoWrapper;
import com.LUCUX.XPlanner.repository.MonthRepository;
import com.LUCUX.XPlanner.repository.TodoRepository;
import com.LUCUX.XPlanner.util.Constants;
import com.LUCUX.XPlanner.util.CustomError;
import com.LUCUX.XPlanner.util.TK;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping("months")
public class MonthController  {

	@Autowired
	private MonthRepository monthRepository;
	@Autowired
	private TodoRepository tr;
	@GetMapping(value="{id}")
	public ResponseEntity<String> GM(@PathVariable("id") long id) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException,ObjectNotFoundException {
		Month t = monthRepository.findById(id).get();
		String us = TK.toJSON(t);
		return new ResponseEntity<>(us,HttpStatus.OK);
	}
	@GetMapping(value="{id}/todos")
	public ResponseEntity<String> Gtodo(@PathVariable("id") long id) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException,ObjectNotFoundException {
		List<Todo> t = monthRepository.findById(id).get().getTodos();
		String us = TK.toJSON(t);
		return new ResponseEntity<>(us,HttpStatus.OK);
	}
	@DeleteMapping(value="{id}/todos/{idT}")
	public ResponseEntity<String> Dtodo(@PathVariable("id") long id,@PathVariable("idT") long idT) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException,ObjectNotFoundException {
		Month m =monthRepository.findById(id).get();
		m.getTodos().removeIf(h->Long.compare(h.getId(),idT)==0);
		monthRepository.saveAndFlush(m);
		return new ResponseEntity<>(""+idT,HttpStatus.OK);
	}
	@PostMapping(value="{id}/todos",consumes="application/json",produces="application/json")
	@ResponseBody
	public ResponseEntity<String> Ptodos(@PathVariable("id") long id,@RequestBody TodoWrapper todos) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException,ObjectNotFoundException {
		Month sess = monthRepository.findById(id).get();
		List<Todo> lt = todos;
		sess.getTodos().clear();
		for (Todo todo : lt) {
			tr.save(todo);
		}
		sess.getTodos().addAll(lt);
		
		monthRepository.save(sess);
		Month sess2 = monthRepository.findById(id).get();
		String us = TK.toJSON(sess2.getTodos());
		return new ResponseEntity<>(us,HttpStatus.OK);
	}
}

package com.LUCUX.XPlanner.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.LUCUX.XPlanner.model.Role;
import com.LUCUX.XPlanner.service.RoleService;
import com.LUCUX.XPlanner.util.Constants;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@ApiOperation(value = "Get all roles")
	@RequestMapping(value = "/getAllRole", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constants.SUCCESS, response = Role.class),
			@ApiResponse(code = 403, message = Constants.FORBIDDEN),
			@ApiResponse(code = 422, message = Constants.NOT_FOUND),
			@ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED)})
	public ResponseEntity<List<Role>> getAllRole() {

		try {
			List<Role> roles = roleService.findAll();
			return new ResponseEntity<List<Role>>(roles, HttpStatus.OK);
			
		} catch (ObjectNotFoundException onfe) {
			onfe.printStackTrace();
			return new ResponseEntity<List<Role>>(Collections.emptyList(), HttpStatus.UNPROCESSABLE_ENTITY);
		}

		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Role>>(Collections.emptyList(), HttpStatus.EXPECTATION_FAILED);
		}

	}

}

package com.LUCUX.XPlanner.service;

import java.util.List;

import com.LUCUX.XPlanner.model.Role;

import javassist.tools.rmi.ObjectNotFoundException;

public interface RoleService {
	
	 List<Role> findAll()throws ObjectNotFoundException;
}

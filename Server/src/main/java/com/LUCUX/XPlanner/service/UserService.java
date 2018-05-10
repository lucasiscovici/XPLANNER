package com.LUCUX.XPlanner.service;

import java.util.List;
import java.text.ParseException;

import com.LUCUX.XPlanner.model.User;

import javassist.tools.rmi.ObjectNotFoundException;

public interface UserService {
	
	 User findByUsernameOrEmail(String usermaneOrEmail);
	 User findById(Long id) throws ObjectNotFoundException;
	 User createUserIfNotFound(final String username,final String password )throws ParseException;
	 User save(User us);
	 List<User> getUsers();
	 List<User> getX(String key, String value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException; 
}

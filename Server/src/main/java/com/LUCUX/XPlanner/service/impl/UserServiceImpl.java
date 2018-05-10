package com.LUCUX.XPlanner.service.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.LUCUX.XPlanner.model.User;
import com.LUCUX.XPlanner.repository.UserRepository;
import com.LUCUX.XPlanner.service.UserService;
import com.LUCUX.XPlanner.TEST.TestBase;
import com.LUCUX.XPlanner.model.UserInfo;
import javassist.tools.rmi.ObjectNotFoundException;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	public UserRepository userRepository;
	


	@Override
	@Transactional(readOnly = true)
	public User findByUsernameOrEmail(String usernameOrEmail) {
		try {
			User user = userRepository.findByUsernameOrEmail(usernameOrEmail);
			return user;
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	@Transactional(readOnly = true)
	public User findById(Long id) throws ObjectNotFoundException {
		Optional<User> user = userRepository.findById(id);
		if (user == null) {
			throw new ObjectNotFoundException("User not found");
		}
		return user.get();
	}

	@Override
	@Transactional
	public User createUserIfNotFound(String username, String password) throws ParseException{
		// TODO Auto-generated method stub
		 
		        User user = userRepository.findByUsernameOrEmail(username);
		        if (user == null) {
		            user = new User();
		            user.setUsername(username);
		            System.out.println(password);
		            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		            user.setPassword(passwordEncoder.encode(password));

		            UserInfo ui = TestBase.TestCreateMWD();
					user.userInfo=ui;
					ui.user=user;
		            //user.setEmail(email);
		            //user.setEnabled(true);
		            userRepository.save(user);
		            //user = userRepository.save(user);
		        }else {
		        	return null;
		        }
		        return user;
		    
	}

	@Override
	public User save(User us) {
		// TODO Auto-generated method stub
		userRepository.saveAndFlush(us);
		return us;
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public List<User> getX(String key, String value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		User us = new User();
		final Field field = us.getClass().getDeclaredField(key);
		// Allow modification on the field
		field.setAccessible(true);
		// Sets the field to the new value for this instance
		field.set(us, value);
		// TODO Auto-generated method stub
		return userRepository.findAll(Example.of(us));
	}
}

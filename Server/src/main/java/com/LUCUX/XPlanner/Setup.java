package com.LUCUX.XPlanner;

import java.text.ParseException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.LUCUX.XPlanner.TEST.TestBase;
import com.LUCUX.XPlanner.model.User;
import com.LUCUX.XPlanner.model.UserInfo;
import com.LUCUX.XPlanner.repository.UserRepository;
import com.LUCUX.XPlanner.service.UserInfoService;
import com.LUCUX.XPlanner.service.UserService;

@Component
public class Setup implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;
    
   
    @Autowired
    private UserService userRepository;
    
    @Autowired
    private UserInfoService userInfoS;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		return;
//		User us = createUserIfNotFound("luluo","caca");
//		try {
//			if (us != null) {
//			UserInfo ui = TestBase.TestCreateMWD();
//			us.userInfo=ui;
//			this.saveUserInfoIfNotFound(us);
//			}
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch(Exception e){
//			
//		}
		
	}
	@Transactional
    private final User createUserIfNotFound(final String username,final String password) {
        // User user = userRepository.createUserIfNotFound(username,password);
        	User us=  new User();
        	us.setUsername(username);
        	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
    		String encodedPassword = passwordEncoder.encode(password);  
        	us.setPassword(encodedPassword);
        return us;
    }
	@Transactional
    private final User saveUserInfoIfNotFound(User userInfo) {
		
        User user = userRepository.save(userInfo);
        return user;
    }


}
package com.LUCUX.XPlanner;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

import com.LUCUX.XPlanner.model.User;
import com.LUCUX.XPlanner.repository.UserRepository;
import com.LUCUX.XPlanner.service.impl.UserServiceImpl;


@SpringBootApplication
public class Application {
	 private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	   private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");


	public static void main(String[] args) {
		Date date = new Date();
		System.out.println("sys"+sdf.format(date));
		SpringApplication.run(Application.class, args);
		//Application.createUser();
	}
	public static void createUser() {
		User u = new User();
		UserServiceImpl usi = new UserServiceImpl();
		u.setUsername("lulu");
		u.setPassword("caca");
		usi.userRepository.save(u);
		
	}

		@Bean
	   public LocalEntityManagerFactoryBean entityManagerFactory(){
	       LocalEntityManagerFactoryBean factoryBean = new LocalEntityManagerFactoryBean();
	       factoryBean.setPersistenceUnitName("manager1");
	       
	    return factoryBean;
	}
}

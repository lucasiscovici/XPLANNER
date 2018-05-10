package com.LUCUX.XPlanner.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.LUCUX.XPlanner.conf.Configuration;


public class EM {
	EntityManager em;
	public EM() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(Configuration.persistenceUnitName);
		em = emf.createEntityManager();
	}
}

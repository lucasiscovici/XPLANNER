package com.LUCUX.XPlanner.controller;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import com.LUCUX.XPlanner.model.CrudD;
import com.LUCUX.XPlanner.model.IDS;
import com.LUCUX.XPlanner.util.TK;
@SuppressWarnings("unchecked")
public class EMCrud<A> extends EM {
	
	public String typeName = "";
	public EMCrud(String typeName) {
		this.typeName=typeName;
	}
	//GET ALL
	

	public List<A> getAll() {
		String name = this.typeName;
		return em.createQuery(TK.getAll(name)).getResultList();
	}

	public A getById(long id) {
		return getBy("id", id);
	}
	
	public A getBy(String nameM,long id) {
		return getBy(nameM,""+id);
	}
	
	public A getBy(String nameM,String id) {
		String name = this.typeName;
		try {
			
			return (A) em.createQuery(TK.getWith(nameM,name,id)).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public boolean exist(IDS user) {
		// TODO Auto-generated method stub
		return getById(user.getId()) != null;
	}
	
	public void create(Object car) {
		transaction((A) car);
	}
	
	public EntityTransaction getEntityTransaction() {
		return em.getTransaction();
	}
	
	public void transaction(Object car) {
		EntityTransaction tx = getEntityTransaction();
		tx.begin();
		try {
			em.persist(car);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}
	}
	
}

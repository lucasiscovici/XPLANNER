package com.LUCUX.XPlanner.TEST;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.LUCUX.XPlanner.model.Session;
import com.LUCUX.XPlanner.model.UserInfo;
import com.LUCUX.XPlanner.service.UserService;
import com.LUCUX.XPlanner.service.impl.UserServiceImpl;
import com.LUCUX.XPlanner.util.TK;

import javassist.tools.rmi.ObjectNotFoundException;

public class TestBase {
	static EntityManagerFactory emf=Persistence.createEntityManagerFactory("manager1");
	static EntityManager entityManager = TestBase.emf.createEntityManager();

	public static void main(String[] args) throws ParseException, ObjectNotFoundException {
		
		UserService us = new UserServiceImpl();
		
	us.findById((long) 114).userInfo.sess.forEach(System.out::println);
		// TestBase.TestCreateMWD();
		// System.out.println("TestBaseMWD");
		// TestBase.TestBaseMWD();
		
	}
	public static UserInfo testBase() throws ParseException {
		System.out.println("TestCreateMWD");
		return TestBase.TestBaseMWD(TestBase.TestCreateMWD());
		// System.out.println("TestBaseMWD");
		// TestBase.TestBaseMWD();
	}

	public static UserInfo testGet() throws ParseException {
		
				System.out.println("TestGET");
//					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//					Date d = sdf.parse("01/03/2018");
					String d = "babar";
					UserInfo sess = (UserInfo) TestBase.entityManager.createQuery("select c from UserInfo c where c.name like :name").setParameter("name", d ).getSingleResult();

					return sess;
	}
	
	public static UserInfo TestCreateMWD() throws ParseException {

		UserInfo u = new UserInfo();
		
		
		Session sess = new Session();
		sess.ui=u;
		sess.setName("default");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d = sdf.parse("01/03/2018");
		
		sess.date=d;
		sess.months = TK.createStructureMWD(d);
		DateFormat sF = DateFormat.getDateTimeInstance(

				DateFormat.FULL,

				DateFormat.FULL, new Locale("FR","fr"));
		// for (Month m : sess.months) {
		// 	System.out.println("MONTH "+sF.format(m.date));
		// 	for (Week w : m.weeks) {
		// 		System.out.println("Week "+sF.format(w.date));
		// 		for (Day da : w.days) {
		// 			System.out.println("DAY "+sF.format(da.date));
		// 		}
		// 	}
		// }
		sess.ui=u;
		u.sess.add(sess);
		// u.sessCur=sess;
		
		return u;
	}
	public static UserInfo TestBaseMWD(UserInfo u) {

				EntityTransaction tx = entityManager.getTransaction();
				tx.begin();
				try {
					UserInfo sess = TestBase.TestCreateMWD();
					entityManager.persist(sess);
					tx.commit();
				} catch (Exception e) {
					tx.rollback();
					e.printStackTrace();
				}
				return u;
				
		
	}
}

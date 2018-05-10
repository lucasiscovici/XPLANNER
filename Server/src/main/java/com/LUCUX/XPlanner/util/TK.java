package com.LUCUX.XPlanner.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.LUCUX.XPlanner.*;
import com.LUCUX.XPlanner.model.Day;
import com.LUCUX.XPlanner.model.Month;
import com.LUCUX.XPlanner.model.Session;
import com.LUCUX.XPlanner.model.UserInfo;
import com.LUCUX.XPlanner.model.Week;

public class TK {
	static UserInfo createUserWithStructureMWD(String name) throws ParseException {
		Session sess = new Session();
		UserInfo user = new UserInfo();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d = sdf.parse("01/03/2018");
		
		sess.date=d;
		sess.months = TK.createStructureMWD(d);
		user.sess.add(sess);
		user.sessCur=sess;
		return user;
	}
	public static Map<String, List<String>> MultiValueMapToMap(MultiValueMap<String,String> n){
		Map<String, List<String>> map = new HashMap<>();
		for (Entry<String, List<String>> fir : n.entrySet()) {
			map.put(fir.getKey(), fir.getValue());
		}
		return map;
	}
	public static String[] s(String... strings) {
			return strings;
	}
	public static String[] slice(String[] s,int l, int u) {
		if(u==-1) u = s.length;
		 return Arrays.copyOfRange(s,l,u);
	}
	public static String[] slice(String[] s,int l) {
		 return Arrays.copyOfRange(s,l,s.length);
	}
	public static Map<String, String[]> toMapSSL(String[]... m) {
		Map<String, String[]> k = new HashMap<>();
		for (String[] strings : m) {
			k.put(strings[0], TK.slice(strings, 1,-1));
		}
		return k;
	}
	@SuppressWarnings("unchecked")
	public static<T>  String toJSON(T staff,ObjectWriter mapp) throws JsonProcessingException {
		ObjectWriter mapper = mapp;

		return mapper.writeValueAsString((T) staff);
	}
	public static String toJSON(Object staff) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return TK.toJSON(staff, mapper.writer());
	}
	static int numMonth = 3;
	public static long _1H_sec = 60 * 60;
	public static List<Month> createStructureMWD(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		List<Month> lm = new ArrayList<>();
		for (int i = 0; i <TK.numMonth; i++) {
			Month m = new Month();
			cal.setTime(d);
			cal.add(Calendar.MONTH, i);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			m.date=cal.getTime();
			int myMonth=cal.get(Calendar.MONTH);
			List<Week> lw = new ArrayList<>();
			Week w = null;
			while (myMonth==cal.get(Calendar.MONTH)) {
				if ((cal.get(Calendar.DAY_OF_MONTH)-1)%7==0) {
		    			w = new Week();
		    			w.date=cal.getTime();
		    			lw.add(w);
			    }
				Day dd = new Day();
				dd.date=cal.getTime();
				
				w.days.add(dd);
			    cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			m.weeks.addAll(lw);
			lm.add(m);
		}
		return lm;
		
	}
	public static List<Month> createStructureMWD2(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		List<Month> lm = new ArrayList<>();
		List<Day> lmda = new ArrayList<>();
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();

		for (int i = 0; i <TK.numMonth; i++) {
			Month m = new Month();
			cal.setTime(d);
			cal.add(Calendar.MONTH, i);
			cal.set(Calendar.DAY_OF_MONTH, 1);

			m.date=cal.getTime();
			int myMonth=cal.get(Calendar.MONTH);
			map.put(myMonth,i);
			// List<Week> lw = new ArrayList<>();
			// Week w = null;
			while (myMonth==cal.get(Calendar.MONTH)) {
				// if ((cal.get(Calendar.DAY_OF_MONTH)-1)%7==0) {
		  //   			w = new Week();
		  //   			w.date=cal.getTime();
		  //   			lw.add(w);
			 //    }
				Day dd = new Day();
				dd.date=cal.getTime();
				
				lmda.add(dd);
			    cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			// m.weeks.addAll(lw);
			lm.add(m);
		}
		List<Week> lmdw = new ArrayList<>();

		Calendar calo = Calendar.getInstance();
		calo.setTime(d);
		calo.set(Calendar.DAY_OF_MONTH, 1);
		int j = calo.get(Calendar.DAY_OF_WEEK)-1;
		if (j==0) {
			j=7;
		}
		j-=1;
		int iii=0;
		List<Day>  dda = new ArrayList<>();
		Week w = null;
		for (Day ddv: lmda ) {
			
			if (iii%7==0) {
				if (iii!=0) {
					w.days.addAll(dda);
					lmdw.add(w);
					dda.clear();
				}
				w = new Week();
				w.date=ddv.getDate();
			}
			dda.add(ddv);
			iii+=1;

		}
		if (dda.size()>0) {
			w.days=dda;
		}

		// List<Month> lmdm = new ArrayList<>();

		for (Week ww: lmdw ) {
			Calendar calp = Calendar.getInstance();
			calp.setTime(ww.getDate());
			int month = calp.get(Calendar.MONTH);
			int vr = map.get(month);
			lm.get(vr).getWeeks().add(ww);

			Calendar cal2 = Calendar.getInstance();
			List<Day> sdl =ww.getDays();
			cal2.setTime(sdl.get(sdl.size()-1).getDate());
			int month2 = cal2.get(Calendar.MONTH);
			if (month2!=month) {
				
			
				int vrP = map.get(month2);
				lm.get(vrP).getWeeks().add(ww);
			}

		}
		return lm;
		
	}
	public static int getLastDay(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.DAY_OF_MONTH, 1); 
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	public static Date getFirstDayDate(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.DAY_OF_MONTH, 1); 
		return cal.getTime();

	}
	
	public static boolean keyInMap(String string) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static ObjectWriter JSONMapper(Map<String, String[]> ignore) {
		return _JSONMapper(ignore, false);  
	}
	public static ObjectWriter _JSONMapper(Map<String, String[]> ignore,Map<String, String[]> filterss) {
		ObjectMapper mapper = new ObjectMapper();
	    FilterProvider filters = new SimpleFilterProvider();
	    	for (Entry<String, String[]> i : ignore.entrySet()) {
	    		((SimpleFilterProvider) filters).addFilter(i.getKey(),  SimpleBeanPropertyFilter.serializeAllExcept(i.getValue()));  
		}
	    	for (Entry<String, String[]> i : filterss.entrySet()) {
	    		((SimpleFilterProvider) filters).addFilter(i.getKey(),  SimpleBeanPropertyFilter.filterOutAllExcept(i.getValue()) );  
		}
	    return mapper.writer(filters); 
	}
	public static ObjectWriter _JSONMapper(Map<String, String[]> ignore,boolean reverse) {
		ObjectMapper mapper = new ObjectMapper();
	    FilterProvider filters = new SimpleFilterProvider();
	    	for (Entry<String, String[]> i : ignore.entrySet()) {
	    		((SimpleFilterProvider) filters).addFilter(i.getKey(), reverse ? SimpleBeanPropertyFilter.filterOutAllExcept(i.getValue()) : SimpleBeanPropertyFilter.serializeAllExcept(i.getValue()));  
		}
	    		
	    return mapper.writer(filters); 
	}
	public static ObjectWriter JSONMapperR(Map<String, String[]> ignore) {

	    return _JSONMapper(ignore, true); 
	}
	
	public static ObjectWriter JSONMapper() {
		ObjectMapper mapper = new ObjectMapper();
	    return mapper.writer(); 
	}
	public static String getAll(String name) {
		// TODO Auto-generated method stub
		return "select u from "+name+" u";
	}
	public static Boolean checkKeyBoolInParams(String string, MultiValueMap<String, String> params) {
		// TODO Auto-generated method stub

		return params.containsKey(string) && params.getFirst(string).equals("true");
	}
	public static String getWithId(String name, long id) {
		// TODO Auto-generated method stub
		return getWith("id",name,""+id);
	}
	public static String getWith(String nameM, String name, String id) {
		// TODO Auto-generated method stub
		return "select u from "+name+" u where u."+nameM+" = "+id;
	}
	public static String[] s(String filterjsonjoined, String[] getFiltered) {
		List<String> k = TK.toList((filterjsonjoined));
		k.addAll(TK.toList(getFiltered));
		return TK.LSToarr(k);
	}
	public static  String[] LSToarr(List<String> k) {
		return k.toArray(new String[k.size()]);
	}
 	static public <E> List<E> toList(E... d){
		return new ArrayList<>(Arrays.asList(d));
	}
	public static  <E>  List<E> StreamToArray(Stream<E> filter) {
		// TODO Auto-generated method stub
		return filter.collect(Collectors.toList());
	}
	
	public static String[] filter(String[] p, List<String> kk) {
		return TK.LSToarr(
	
			TK.StreamToArray(
					TK.toList(
							p
							).stream().filter(k->!kk.contains(k))
					)
				);
	}
	
}

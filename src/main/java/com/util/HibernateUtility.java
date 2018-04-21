package main.java.com.util;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


public class HibernateUtility {
	private static final SessionFactory sessionFactory;
	static{
		try{
			sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory(); 
		}catch(Throwable throwable){
			System.err.println("SessionFactory creation failed"+throwable);
			throw new ExceptionInInitializerError(throwable);
		}
	}
	
	public static synchronized SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	
	public static void performDDL(String nativeSql) {		
		Session session = null;
		Transaction transaction = null;
		try{
			session = getSessionFactory().openSession();
			transaction = session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createNativeQuery(nativeSql);			
			query.executeUpdate();		  
			transaction.commit();    		
		} catch(RuntimeException e) {
			try {
				transaction.rollback();
			} catch(RuntimeException rbe) {
				e.printStackTrace();
				System.err.printf("Failed to rollback.");
			}			
		} finally {
			if(session!=null) {
				session.close();
			}    		
		}
	}
	

	@SuppressWarnings("unchecked")
	public static List<String> performSelect(String nativeSql, char delimiter){
		List<String> returnValue = new ArrayList<String>();
		Session session = null;
		Transaction transaction = null;
		try{
			session = getSessionFactory().openSession();
			transaction = session.beginTransaction();			
			@SuppressWarnings("rawtypes")
			Query query = session.createNativeQuery(nativeSql);	
			List<Object[]> wut = query.getResultList();
			int count = 0;
			Object test = wut.get(0);
			if(test instanceof String) {
				String returnString = count + "";
				for(Object string: wut) {
					returnString = returnString + ","+string;
				}
				returnValue.add(returnString);				
			} else {			
			for (Object[] objects: wut) {				
				String returnString = count + "";
				for(Object object: objects) {
					if (object instanceof Integer) {
						returnString = returnString + "," + Integer.toString((Integer) object);						
					} else if (object.hashCode() == 0){
						returnString = returnString + ","+"";
					}
					else {
						returnString = returnString + ","+object;
					}
				}
				returnValue.add(returnString);
			}
			}
			transaction.commit();    		
		} catch(RuntimeException e) {
			try {
				System.out.println("error");
				e.printStackTrace();
				transaction.rollback();
			} catch(RuntimeException rbe) {
				System.err.printf("Failed to rollback.");
			}			
		} finally {
			if(session!=null) {
				session.close();
			}    		
		}
		return returnValue;
	}
}

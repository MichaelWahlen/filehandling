package main.java.com.util;

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
	
	public static void performSQL(String nativeSql) {
		
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
				System.err.printf("Failed to rollback.");
			}			
		} finally {
			if(session!=null) {
				session.close();
			}    		
		}
	}

}

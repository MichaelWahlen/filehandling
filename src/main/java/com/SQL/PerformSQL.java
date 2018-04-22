package main.java.com.SQL;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


public class PerformSQL {
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
		} catch(Exception e) {
			e.printStackTrace();
		}  finally {
			if(session!=null) {
				session.close();
			}    		
		}
	}
	
	public static List<String>  performSelect(String nativeSql) {
		Session session = null;
		List<String> returnValue = new ArrayList<String>();
		try{
			session = getSessionFactory().openSession();
			@SuppressWarnings("unchecked")
			Query<Object[]> query = session.createNativeQuery(nativeSql);
			List<Object[]> queryResult = query.getResultList();								
			for (Object[] objects: queryResult) {				
				StringBuilder returnString = new StringBuilder("");
				for(Object object: objects) {
					if (object instanceof Integer) {
						returnString.append("," + Integer.toString((Integer) object));						
					} else if (object.hashCode() == 0){
						returnString.append(",");
					}
					else {
						returnString.append(","+object);
					}
				}
				returnValue.add(returnString.toString().substring(1));
			}			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(session!=null) {
				session.close();
			}    		
		}
		return returnValue;
	}
	
	public static String getSingle(String nativeSql) {		
		Session session = null;
		String returnValue = "";
		try{
			session = getSessionFactory().openSession();
			@SuppressWarnings("unchecked")
			Query<Object[]> query = session.createNativeQuery(nativeSql);
			List<Object[]> queryResult = query.getResultList();
			Object test = queryResult.get(0);
			if(test instanceof String) {
				//returnValue = "ID";
				for(Object string: queryResult) {
					returnValue = returnValue + ","+string;
				}
				returnValue = returnValue.substring(1);
			}			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(session!=null) {
				session.close();
			}    		
		}
		return returnValue;
	}

}

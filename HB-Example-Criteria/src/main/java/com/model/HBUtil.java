package com.model;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HBUtil {
	
	private static SessionFactory sessionFactory;
	
	static SessionFactory buildSessionFactory() {
		try {
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			System.out.println("Hibernate Configuration loaded");
			
			ServiceRegistry serviceregistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
					System.out.print("hibernate service registry created");
			SessionFactory sessionFactory = configuration.buildSessionFactory(serviceregistry);
			return sessionFactory;
		}
		catch(Throwable ex) {
			System.err.println("Initial SessionFactory created failed"+ex);
			ex.printStackTrace();
			throw new ExceptionInInitializerError();
		}
	}
	
	public static SessionFactory getSessionFactory() {
		if(sessionFactory==null) {
			sessionFactory=buildSessionFactory();
		
		}
		return sessionFactory;
	}

}
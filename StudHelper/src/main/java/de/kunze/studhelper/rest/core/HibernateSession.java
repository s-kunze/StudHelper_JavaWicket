package de.kunze.studhelper.rest.core;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class HibernateSession {

	private static HibernateSession instance;

	private SessionFactory sessionFactory;

	public static HibernateSession getInstance() {
		if (instance == null) {
			instance = new HibernateSession();
		}
		return instance;
	}

	private HibernateSession() {
		try {
			AnnotationConfiguration config = new AnnotationConfiguration().configure();

			 new SchemaExport(config).setOutputFile("./scripts/create.sql").create(true,false);

			sessionFactory = config.buildSessionFactory();
		} catch (Exception e) {
			System.err.println("FEHLER:");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}

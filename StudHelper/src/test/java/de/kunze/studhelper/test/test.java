package de.kunze.studhelper.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.kunze.studhelper.rest.core.HibernateSession;

public class test {

	public static void main(String[] args) {
		SessionFactory sessionFactory = HibernateSession.getInstance().getSessionFactory();
		Session session = sessionFactory.openSession();
	}

}

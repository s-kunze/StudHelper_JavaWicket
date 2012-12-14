package de.kunze.studhelper.test;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.kunze.studhelper.rest.core.HibernateSession;
import de.kunze.studhelper.rest.models.dao.BaseDao;
import de.kunze.studhelper.rest.models.user.Admin;

public class InitialImport {

//	private SessionFactory sessionFactory;
//	private Session session = null;

//	@Before
//	public void tearUp() {
//		try {
//			sessionFactory = HibernateSession.getInstance().getSessionFactory();
//			session = sessionFactory.openSession();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	@After
//	public void tearDown() {
//		try {
//			session.close();
//			sessionFactory.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
//	@Test
//	public void importAdminUser() {
//		Admin admin = new Admin();
//		byte[] arr = DigestUtils.md5("admin");
//		try {
//			String password = new String(arr, "UTF-8");
//			admin.setPassword(password);
//			admin.setUsername("admin");
//			
//			BaseDao<Admin> dao = new BaseDao<Admin>(Admin.class);
//			dao.save(admin);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}		
//	}
	
}

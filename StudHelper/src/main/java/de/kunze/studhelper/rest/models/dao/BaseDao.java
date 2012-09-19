/**
 * This file is part of StudHelper_JavaWicket
 */
package de.kunze.studhelper.rest.models.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import de.kunze.studhelper.rest.core.HibernateSession;

/**
 * This is the implementation of a BaseDao
 * @author Stefan Kunze
 *
 * @param <T>
 */
public class BaseDao<T> implements IBaseDao<T> {

	private final Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public BaseDao() {
		this.persistentClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public BaseDao( final Class<T> persistentClass )
    {
        super();
        this.persistentClass = persistentClass;
    }
	
	@SuppressWarnings("unchecked")
	public T get(Integer id) {
		SessionFactory sessionFactory = HibernateSession.getInstance().getSessionFactory();
		Session session = sessionFactory.openSession();
		
		T result = (T) session.get(persistentClass, id);			
		
		session.close();
		sessionFactory.close();
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		SessionFactory sessionFactory = HibernateSession.getInstance().getSessionFactory();
		Session session = sessionFactory.openSession();
		
		List<T> result = null;
		
		Criteria crit = session.createCriteria(persistentClass);
        result = crit.list();
		
        session.close();
		sessionFactory.close();
        
		return result;
	}

	public Integer save(T t) {
		SessionFactory sessionFactory = HibernateSession.getInstance().getSessionFactory();
		Session session = sessionFactory.openSession();
		
		Integer id = null;
		Transaction ta = null;
		try {
			if (t != null) {
				ta = session.beginTransaction();
				id = (Integer) session.save(t);
				ta.commit();
			} 
		} catch(HibernateException e) {
			if(ta != null) 	ta.rollback();
			e.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		
		return id;
	}

	public void update(T t) {
		SessionFactory sessionFactory = HibernateSession.getInstance().getSessionFactory();
		Session session = sessionFactory.openSession();
	
		Transaction ta = null;
		try {
			if (t != null) {
				ta = session.beginTransaction();
				session.update(t);
				ta.commit();
			} 
		} catch(HibernateException e) {
			if(ta != null) 	ta.rollback();
			e.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		
	}

	public void delete(T t) {
		SessionFactory sessionFactory = HibernateSession.getInstance().getSessionFactory();
		Session session = sessionFactory.openSession();
	
		Transaction ta = null;
		try {
			if (t != null) {
				ta = session.beginTransaction();
				session.delete(t);
				ta.commit();
			} 
		} catch(HibernateException e) {
			if(ta != null) 	ta.rollback();
			e.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
	}

}

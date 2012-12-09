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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kunze.studhelper.rest.core.HibernateSession;

/**
 * This is the implementation of a BaseDao
 * @author Stefan Kunze
 *
 * @param <T>
 */
public class BaseDao<T> implements IBaseDao<T> {

	private static Logger logger = LoggerFactory.getLogger(BaseDao.class);
	
	private final Class<T> persistentClass;
	
	protected SessionFactory sessionFactory;
	protected Session session;
	
	@SuppressWarnings("unchecked")
	public BaseDao() {
		this.persistentClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	
		this.sessionFactory = HibernateSession.getInstance().getSessionFactory();
		this.session = sessionFactory.openSession();
	}
	
	public BaseDao( final Class<T> persistentClass )
    {
        super();
        this.persistentClass = persistentClass;
        
        this.sessionFactory = HibernateSession.getInstance().getSessionFactory();
		this.session = sessionFactory.openSession();
    }
	
	@SuppressWarnings("unchecked")
	public T get(Long id) {
		
		
		T result = null;
		Transaction ta = null;
		try {
			ta = this.session.beginTransaction();
			
			result = (T) this.session.get(persistentClass, id);	
			
			ta.commit();
		} catch(HibernateException e) {
			if(ta != null) 	ta.rollback();
			logger.error("", e);
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		List<T> result = null;
		Transaction ta = null;
		try {
			ta = this.session.beginTransaction();
		
			Criteria crit = this.session.createCriteria(persistentClass);
			result = crit.list();
			
			ta.commit();
		} catch(HibernateException e) {
			if(ta != null) 	ta.rollback();
			logger.error("", e);
		}
        
		return result;
	}

	public Long save(T t) {
		Long id = null;
		Transaction ta = null;
		try {
			if (t != null) {
				ta = this.session.beginTransaction();
				id = (Long) this.session.save(t);
				ta.commit();
			} 
		} catch(HibernateException e) {
			if(ta != null) 	ta.rollback();
			logger.error("", e);
		}
		
		return id;
	}

	public boolean update(T t) {
	
		Transaction ta = null;
		try {
			if (t != null) {
				ta = this.session.beginTransaction();
				this.session.merge(t);
//				this.session.saveOrUpdate(t);
				ta.commit();
			} 
		} catch(HibernateException e) {
			if(ta != null) 	ta.rollback();
			logger.error("", e);
			
			return false;
		}
		
		return true;
	}

	public boolean delete(T t) {
		Transaction ta = null;
		try {
			if (t != null) {
				ta = this.session.beginTransaction();
				this.session.delete(t);
				ta.commit();
			} 
		} catch(HibernateException e) {
			if(ta != null) 	ta.rollback();
			logger.error("", e);
			return false;
		}
		
		return true;
	}

	public void close() {
		if(this.session != null) {
			session.close();
		}
		if(this.sessionFactory != null) {
			sessionFactory.close();
		}
	}
	
}

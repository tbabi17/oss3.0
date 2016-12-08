package mn.mxc.oss.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class GenericDao<T>{

	public Session session;
	@Autowired
	public SessionFactory sessionFactory;
	public Criteria crit;

	public Session getSession() {
//		if (session == null)
//			session = sessionFactory.openSession();
//		else
//			session.clear();
//		return session;
		return sessionFactory.openSession();
	}

	public void save(final T entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		session.close();
	}

	public void update(final T entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.merge(entity);
		tx.commit();
		session.close();
	}

	protected T findOne(final Class<T> type, final int id) {
		return (T) getSession().get(type, id);
	}

	public void delete(final T entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(entity);
		tx.commit();
		session.close();
	}

	public long total(final Class<T> type) {
		crit = getSession().createCriteria(type);
		crit.setProjection(Projections.rowCount());
		long total = (Long)crit.uniqueResult();
		return total;
	}

	public long total() {
		crit.setProjection(Projections.rowCount());
		long total = (Long)crit.uniqueResult();
		return total;
	}

	public <T> List<T> findAll(final Class<T> type) {
		crit = getSession().createCriteria(type);
		List<T> list = crit.list();
		return list;
	}

	public <T> List<T> findAll(final Class<T> type, int page, int size) {
		crit = getSession().createCriteria(type);
		crit.setFirstResult((page - 1) * size);
		crit.setMaxResults(size);
		List<T> list = crit.list();
		return list;
	}

	public boolean deleteById(Class<?> type, Serializable id) {
		Object persistentInstance = session.load(type, id);
		if (persistentInstance != null) {
			session.delete(persistentInstance);
			return true;
		}
		return false;
	}
}

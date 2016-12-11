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
	public long total = 0;

	public Session getSession() {
//		if (session == null)
//			session = sessionFactory.openSession();
//		else
//			session.clear();
//		return session;
		if (session != null && session.isOpen()) {
			session.clear();
			return sessionFactory.getCurrentSession();
		}
		return sessionFactory.openSession();
	}

	public void close() {
		if (session != null)
		session.close();
	}

	public void save(final T entity) {
//		Session session = sessionFactory.openSession();
		session = getSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
	}

	public void update(final T entity) {
//		Session session = sessionFactory.openSession();
		session = getSession();
		Transaction tx = session.beginTransaction();
		session.merge(entity);
		tx.commit();
	}

	protected T findOne(final Class<T> type, final int id) {
		session = getSession();
		Transaction tx = session.beginTransaction();
		T item = (T)session.get(type, id);
		tx.commit();
		return item;
	}

	public void delete(final T entity) {
		session = getSession();
		Transaction tx = session.beginTransaction();
		session.delete(entity);
		tx.commit();
	}

	public long total(final Class<T> type) {
		session = getSession();
		Transaction tx = session.beginTransaction();
		crit = session.createCriteria(type);
		crit.setProjection(Projections.rowCount());
		long total = (Long)crit.uniqueResult();
		tx.commit();
		return total;
	}

	public long totalUniq(Criteria crit) {
		crit.setProjection(Projections.rowCount());
		crit.setFirstResult(0);
		crit.setMaxResults(1);
		long total = (Long)crit.uniqueResult();
		return total;
	}

	public long total() {
		return total;
	}

	public <T> List<T> findAll(final Class<T> type) {
		session = getSession();
		Transaction tx = session.beginTransaction();
		crit = session.createCriteria(type);
		List<T> list = crit.list();
		total = totalUniq(crit);
		tx.commit();
		return list;
	}

	public <T> List<T> findAll(final Class<T> type, int page, int size) {
		session = getSession();
		Transaction tx = session.beginTransaction();
		crit = session.createCriteria(type);
		crit.setFirstResult((page - 1) * size);
		crit.setMaxResults(size);
		List<T> list = crit.list();
		total = totalUniq(crit);
		tx.commit();
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

package mn.mxc.oss.dao;

import mn.mxc.oss.domain.Product;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

@Repository
public class ProductDao extends GenericDao<Product> {
    public Product findOne(final int id) {
        return findOne(Product.class, id);
    }

    public List<Product> findAll(int page, int size) {
        return findAll(Product.class, page, size);
    }

    public List<Product> findBySearch(String value) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        crit = session.createCriteria(Product.class);
        crit.setFirstResult(0);
        crit.setMaxResults(10);
        Criterion name = Restrictions.like("name", "%"+value+"%");
        Criterion brand = Restrictions.like("brand", "%"+value+"%");
        LogicalExpression exp1 = Restrictions.or(name, brand);
        crit.add(exp1);
        List<Product> list = crit.list();
        total = totalUniq(crit);
        tx.commit();
        return list;
    }
    public List<Product> findBrands() {
        session = getSession();
        Transaction tx = session.beginTransaction();
        String SQL_QUERY = "SELECT product FROM Product product GROUP BY product.brand";
        Query query = session.createQuery(SQL_QUERY);
        List<Product> list = query.list();
        tx.commit();
        return list;
    }
    public List<Product> findNyaravs() {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        String SQL_QUERY = "SELECT product FROM Product product GROUP BY product.descr";
        Query query = session.createQuery(SQL_QUERY);
        List<Product> list = query.list();
        tx.commit();
        return list;
    }

    public List<Product> findByAvailable(int warehouseId) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.getNamedQuery("productsAvailable");
        query.setParameter("warehouseId", new Integer(warehouseId));
        List<Product> list = query.list();
        tx.commit();
        return list;
    }
    public List<Product> findHistoryProducts(int customer_id) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        String SQL_QUERY = "SELECT product FROM Product product";
        Query query = session.createQuery(SQL_QUERY);
        List<Product> list = query.list();
        tx.commit();
        return list;
    }
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

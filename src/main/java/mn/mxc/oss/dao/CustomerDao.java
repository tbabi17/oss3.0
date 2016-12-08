package mn.mxc.oss.dao;

import mn.mxc.oss.domain.Customer;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDao extends GenericDao<Customer> {
    public Customer findOne(final int id) {
        return findOne(Customer.class, id);
    }

    public List<Customer> findAll(int page, int size) {
        return findAll(Customer.class, page, size);
    }

    public List<Customer> findBySearch(String value) {
        Session session = getSession();
        crit = session.createCriteria(Customer.class);
        crit.setFirstResult(0);
        crit.setMaxResults(10);
        Criterion name = Restrictions.like("name", value+"%");
        Criterion phone = Restrictions.like("phone", value+"%");
        LogicalExpression exp1 = Restrictions.or(name, phone);
        crit.add(exp1);
        List<Customer> list = crit.list();
        return list;
    }

    public List<Customer> findByNonRoute() {
        Session session = getSession();
        crit = session.createCriteria(Customer.class);
        crit.setFirstResult(0);
        crit.setMaxResults(10);
        crit.add(Restrictions.eq("route", new Integer(1)));
        List<Customer> list = crit.list();
        return list;
    }
}

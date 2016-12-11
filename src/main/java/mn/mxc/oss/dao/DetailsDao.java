package mn.mxc.oss.dao;

import mn.mxc.oss.domain.Details;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DetailsDao extends GenericDao<Details> {
    public Details findOne(final int id) {
        return findOne(Details.class, id);
    }

    public List<Details> findAll(int page, int size) {
        return findAll(Details.class, page, size);
    }

    public List<Details> findByOrderId(int orderId, int page, int size) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        crit = session.createCriteria(Details.class);
        crit.setFirstResult((page - 1)*size);
        crit.setMaxResults(size);
        crit.add(Restrictions.eq("orderId", orderId));
        List<Details> list = crit.list();
        total = totalUniq(crit);
        tx.commit();
        return list;
    }
}

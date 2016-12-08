package mn.mxc.oss.dao;

import mn.mxc.oss.domain.Details;
import org.hibernate.Session;
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
        Session session = getSession();
        crit = session.createCriteria(Details.class);
        crit.setFirstResult((page - 1)*size);
        crit.setMaxResults(size);
        crit.add(Restrictions.eq("orderId", orderId));
        List<Details> list = crit.list();
        return list;
    }
}

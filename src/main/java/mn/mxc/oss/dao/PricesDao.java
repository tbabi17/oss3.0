package mn.mxc.oss.dao;

import mn.mxc.oss.domain.Prices;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PricesDao extends GenericDao<Prices> {
    public Prices findOne(final int id) {
        return findOne(Prices.class, id);
    }
    public Prices findByProductId(int pid) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        crit = session.createCriteria(Prices.class);
        crit.setFirstResult(0);
        crit.setMaxResults(10);
        crit.add(Restrictions.eq("productId", pid));
        Prices price = (Prices) crit.list();
        total = totalUniq(crit);
        tx.commit();
        return price;
    }
    public List<Prices> findAll(int page, int size) {
        return findAll(Prices.class, page, size);
    }
}

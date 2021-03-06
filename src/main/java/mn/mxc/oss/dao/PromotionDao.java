package mn.mxc.oss.dao;

import mn.mxc.oss.domain.Promotion;
import mn.mxc.oss.domain.PromotionDetails;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class PromotionDao extends GenericDao<Promotion> {
    public Promotion findOne(final int id) {
        return findOne(Promotion.class, id);
    }

    public List<Promotion> findAll(int page, int size) {
        return findAll(Promotion.class, page, size);
    }

    public List<Promotion> findByActive(int page, int size) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = new Date();
        String today = dateFormat.format(date);
        session = getSession();
        Transaction tx = session.beginTransaction();
        crit = session.createCriteria(Promotion.class);
        crit.setFirstResult((page - 1)*size);
        crit.setMaxResults(size);
        crit.add(Restrictions.eq("status", "active"));
        /*
        crit.add(Restrictions.gt("startDate", today));
        crit.add(Restrictions.lt("endDate", today));*/
        List<Promotion> list = crit.list();
        total = totalUniq(crit);
        tx.commit();
        return list;
    }

    public List<Promotion> findByNonActive(int page, int size) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        crit = session.createCriteria(Promotion.class);
        crit.setFirstResult((page - 1)*size);
        crit.setMaxResults(size);
        crit.add(Restrictions.eq("status", "inactive"));
        List<Promotion> list = crit.list();
        total = totalUniq(crit);
        tx.commit();
        return list;
    }

    public PromotionDetails deleteDetails(PromotionDetails promotionDetails) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        session.delete(promotionDetails);
        tx.commit();
        return promotionDetails;
    }

}

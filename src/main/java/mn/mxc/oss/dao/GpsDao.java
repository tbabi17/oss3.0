package mn.mxc.oss.dao;

import mn.mxc.oss.domain.Gps;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;


public class GpsDao extends GenericDao<Gps> {
    public Gps findOne(final int id) {
        return findOne(Gps.class, id);
    }
    public List<Gps> findAll(int page, int size) {
        return findAll(Gps.class, page, size);
    }
    public List<Gps> findByUserDate(int userid){
        session = getSession();
        Transaction tx = session.beginTransaction();
        crit = session.createCriteria(Gps.class);
        crit.add(Restrictions.eq("UserId",userid));
        List<Gps> list = crit.list();
        if (list.size() > 0) {
            tx.commit();
            return list;
        }
        tx.commit();
        return null;
    }
}

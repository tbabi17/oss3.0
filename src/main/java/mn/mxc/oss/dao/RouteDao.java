package mn.mxc.oss.dao;

import mn.mxc.oss.domain.Route;
import mn.mxc.oss.domain.RouteOnly;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RouteDao extends GenericDao<Route> {
    public Route findOne(final int id) {
        return findOne(Route.class, id);
    }

    public List<Route> findAll(int page, int size) {
        return findAll(Route.class, page, size);
    }
    public List<RouteOnly> findRoutes(int page, int size) {
        return findAll(RouteOnly.class, page, size);
    }
    public List<Route> findByActive(int page, int size) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        crit = session.createCriteria(Route.class);
        crit.setFirstResult((page-1)*size);
        crit.setMaxResults(size);
        crit.add(Restrictions.ne("id", new Integer(1)));
        List<Route> list = crit.list();
        total = totalUniq(crit);
        tx.commit();
        return list;
    }
}

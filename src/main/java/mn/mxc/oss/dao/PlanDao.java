package mn.mxc.oss.dao;

import mn.mxc.oss.domain.Plan;
import mn.mxc.oss.domain.PlanDetails;
import mn.mxc.oss.domain.PlanUsers;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlanDao extends GenericDao<Plan> {
    public Plan findOne(final int id) {
        return findOne(Plan.class, id);
    }

    public List<Plan> findAll(int page, int size) {
        return findAll(Plan.class, page, size);
    }

    public List<Plan> findByActive(int page, int size) {
        Session session = getSession();
//        Query query = session.getNamedQuery("planExecute");
//        query.executeUpdate();
        crit = session.createCriteria(Plan.class);
        crit.setFirstResult((page - 1)*size);
        crit.setMaxResults(size);
        crit.add(Restrictions.eq("status", "active"));
        List<Plan> list = crit.list();
        return list;
    }

    public List<Plan> findByNonActive(int page, int size) {
        Session session = getSession();
        crit = session.createCriteria(Plan.class);
        crit.setFirstResult((page - 1)*size);
        crit.setMaxResults(size);
        crit.add(Restrictions.eq("status", "inactive"));
        List<Plan> list = crit.list();
        return list;
    }

    public PlanDetails deleteDetails(PlanDetails planDetails) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(planDetails);
        tx.commit();
        session.close();

        return planDetails;
    }

    public PlanUsers deleteUsers(PlanUsers planUsers) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(planUsers);
        tx.commit();
        session.close();

        return planUsers;
    }
}

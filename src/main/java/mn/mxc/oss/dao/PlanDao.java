package mn.mxc.oss.dao;

import mn.mxc.oss.domain.Plan;
import mn.mxc.oss.domain.PlanDetails;
import mn.mxc.oss.domain.PlanUsers;
import org.hibernate.Query;
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
        session = getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.getNamedQuery("planExecute");
        query.executeUpdate();

        query = session.getNamedQuery("planExecuteByUser");
        query.executeUpdate();

        crit = session.createCriteria(Plan.class);
        crit.setFirstResult((page - 1)*size);
        crit.setMaxResults(size);
        crit.add(Restrictions.eq("status", "active"));
        List<Plan> list = crit.list();
        total = totalUniq(crit);
        tx.commit();
        return list;
    }

    public List<Plan> findByNonActive(int page, int size) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        crit = session.createCriteria(Plan.class);
        crit.setFirstResult((page - 1)*size);
        crit.setMaxResults(size);
        crit.add(Restrictions.eq("status", "inactive"));
        List<Plan> list = crit.list();
        total = totalUniq(crit);
        tx.commit();
        return list;
    }

    public PlanDetails deleteDetails(PlanDetails planDetails) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        session.delete(planDetails);
        tx.commit();

        return planDetails;
    }

    public PlanUsers deleteUsers(PlanUsers planUsers) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        session.delete(planUsers);
        tx.commit();

        return planUsers;
    }
}

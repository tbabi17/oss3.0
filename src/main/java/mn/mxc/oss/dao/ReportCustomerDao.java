package mn.mxc.oss.dao;

import mn.mxc.oss.domain.ReportCustomer;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReportCustomerDao extends GenericDao<ReportCustomer> {

    public List<ReportCustomer> report(String startDate, String endDate, int page, int size) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        String sql = "delete from ReportCustomer";
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.executeUpdate();

        Query query = session.getNamedQuery("reportCustomer");
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.executeUpdate();


        crit = session.createCriteria(ReportCustomer.class);
        crit.setFirstResult((page-1)*size);
        crit.setMaxResults(size);
        List<ReportCustomer> list = crit.list();
        total = totalUniq(crit);
        tx.commit();
        return list;
    }

    public List<ReportCustomer> findAll(int page, int size) {
        return findAll(ReportCustomer.class, page, size);
    }
}

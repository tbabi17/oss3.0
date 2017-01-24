package mn.mxc.oss.dao;

import mn.mxc.oss.domain.ReportProduct;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReportProductDao extends GenericDao<ReportProduct> {

    public List<ReportProduct> report(String startDate, String endDate, int page, int size) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        String sql = "delete from ReportProduct";
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.executeUpdate();

        Query query = session.getNamedQuery("reportProduct");
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.executeUpdate();

        sql = "update reportProduct set lastCount=firstCount+inCount+outCount,lastAmt=firstAmt+inAmt-outAmt";
        sqlQuery = session.createSQLQuery(sql);
        sqlQuery.executeUpdate();


        crit = session.createCriteria(ReportProduct.class);
        crit.setFirstResult((page-1)*size);
        crit.setMaxResults(size);
        List<ReportProduct> list = crit.list();
        total = totalUniq(crit);
        tx.commit();
        return list;
    }

    public List<ReportProduct> findAll(int page, int size) {
        return findAll(ReportProduct.class, page, size);
    }
}

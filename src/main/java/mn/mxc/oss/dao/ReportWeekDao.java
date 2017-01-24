package mn.mxc.oss.dao;

import mn.mxc.oss.domain.ReportWeekDay;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReportWeekDao extends GenericDao<ReportWeekDay> {

    public List<ReportWeekDay> report(String startDate, String endDate, int page, int size) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        String sql = "delete from ReportWeekDay";
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.executeUpdate();

        Query query = session.getNamedQuery("weekDay");
        query.setParameter("userType", "salesman");
        query.executeUpdate();

        sql = "update ReportWeekDay set totalMCount=monMCount+tueMCount+wedMCount+thuMCount+friMCount+satMCount+sunMCount," +
                "totalECount=monECount+tueECount+wedECount+thuECount+friECount+satECount+sunECount," +
                "totalAmt=monAmt+tueAmt+wedAmt+thuAmt+friAmt+satMCount+sunAmt";
        sqlQuery = session.createSQLQuery(sql);
        sqlQuery.executeUpdate();

        crit = session.createCriteria(ReportWeekDay.class);
        crit.setFirstResult((page-1)*size);
        crit.setMaxResults(size);
        List<ReportWeekDay> list = crit.list();
        total = totalUniq(crit);
        tx.commit();
        return list;
    }

    public List<ReportWeekDay> findAll(int page, int size) {
        return findAll(ReportWeekDay.class, page, size);
    }
}

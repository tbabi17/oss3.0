package mn.mxc.oss.dao;

import com.sun.deploy.util.StringUtils;
import mn.mxc.oss.domain.Details;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
        session = getSession();
        Transaction tx = session.beginTransaction();
        crit = session.createCriteria(Details.class);
        crit.setFirstResult((page - 1)*size);
        crit.setMaxResults(size);
        crit.add(Restrictions.eq("orderId", orderId));
        List<Details> list = crit.list();
        total = totalUniq(crit);
        tx.commit();
        return list;
    }
    public List<Details> recentProducts(List<String> orders) {
        String orders_in = convertToString(orders);
        session = getSession();
        Transaction tx = session.beginTransaction();
        String SQL_QUERY = "SELECT d FROM Details d WHERE d.orderId in ("+orders_in+") GROUP BY d.productId";
        Query query = session.createQuery(SQL_QUERY);
        List<Details> list = query.list();
        tx.commit();
        return list;
    }
    static String convertToString(List<String> orders) {
        StringBuilder builder = new StringBuilder();
        // Append all Integers in StringBuilder to the StringBuilder.
        for (String number : orders) {
            builder.append("'"+number+"'");
            builder.append(",");
        }
        // Remove last delimiter with setLength.
        builder.setLength(builder.length() - 1);
        return builder.toString();
    }
}

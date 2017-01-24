package mn.mxc.oss.dao;

import mn.mxc.oss.domain.*;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StockDao extends GenericDao<StockCurrent> {

    public StockCurrent findOne(final int id) {
        return findOne(StockCurrent.class, id);
    }

    public int calc(int warehouseId) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        String sql = "delete from stockend where endDate=curDate()";
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.executeUpdate();

        sql = "insert into stockend (productId,qty,price,amount,endDate,warehouseId)" +
              "select productId,sum(qty) as first,avg(price),sum(amount),curdate(),"+warehouseId+" from stockbalance where wareHouseId="+warehouseId+" and createdDate>ifnull((select max(endDate) from stockend),subdate(curdate(),1)) group by productId";
        sqlQuery = session.createSQLQuery(sql);
        int result = sqlQuery.executeUpdate();
        tx.commit();

        return result;
    }

    public List<BalanceByUser> balanceByUsers(String startDate, String endDate) {
        //Query query = session.getNamedQuery("findStockByStockCodeNativeSQL").setString("stockCode", "7277");
        session = getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.getNamedQuery("balanceByUser");
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        List<BalanceByUser> list = query.list();
        tx.commit();
        return list;
    }

    public List<BalanceByProduct> balanceByProduct(String startDate, String endDate) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.getNamedQuery("balanceByProduct");
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        List<BalanceByProduct> list = query.list();
        tx.commit();
        return list;
    }

    public List<BalanceByDay> balanceByDay(String startDate, String endDate) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.getNamedQuery("balanceByDay");
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        List<BalanceByDay> list = query.list();
        tx.commit();
        return list;
    }

    public List<StockCurrent> balance(int warehouseId, String startDate, String endDate, int page, int size) {
        //insert into stockend (productId,qty,price,amount,endDate,warehouseId)
        // select productId,sum(qty) as first,avg(price),sum(amount),curdate(),1 from stockbalance where wareHouseId=1 group by productId
        session = getSession();
        Transaction tx = session.beginTransaction();
        String sql = "delete from stockcurrent where wareHouseId="+warehouseId;
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.executeUpdate();

        Query query = session.getNamedQuery("balanceCheck");
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("warehouseId", new Integer(warehouseId));
        query.executeUpdate();

        sql = "update stockcurrent set lastBalance=startBalance+orlogo+zarlaga where warehouseId="+warehouseId;
        sqlQuery = session.createSQLQuery(sql);
        sqlQuery.executeUpdate();

        crit = session.createCriteria(StockCurrent.class);
        crit.setFirstResult((page-1)*size);
        crit.setMaxResults(size);
        crit.add(Restrictions.eq("wareHouseId", new Integer(warehouseId)));
        crit.addOrder(Order.desc("lastBalance"));
        List<StockCurrent> list = crit.list();
        total = totalUniq(crit);
        tx.commit();
        return list;
    }

    public List<StockCurrent> findAll(int page, int size) {
        return findAll(StockCurrent.class, page, size);
    }

    public List<StockCurrent> findAvailable(int page, int size) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        crit = session.createCriteria(StockCurrent.class);
        crit.setFirstResult((page-1)*size);
        crit.setMaxResults(size);
        crit.add(Restrictions.gt("lastBalance", (double)0.0f));
        crit.addOrder(Order.desc("lastBalance"));
        List<StockCurrent> list = crit.list();
        total = totalUniq(crit);
        tx.commit();
        return list;
    }
}

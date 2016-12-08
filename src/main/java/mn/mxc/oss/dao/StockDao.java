package mn.mxc.oss.dao;

import mn.mxc.oss.domain.*;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StockDao extends GenericDao<StockEnd> {

    public StockEnd findOne(final int id) {
        return findOne(StockEnd.class, id);
    }

    public int calc(int warehouseId) {
        //insert into stockend (productId,qty,price,amount,endDate,warehouseId)
        // select productId,sum(qty) as first,avg(price),sum(amount),curdate(),1 from stockbalance where wareHouseId=1 group by productId
        Session session = getSession();
        String sql = "delete from stockend where endDate=curDate()";
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.executeUpdate();

        sql = "insert into stockend (productId,qty,price,amount,endDate,warehouseId)" +
              "select productId,sum(qty) as first,avg(price),sum(amount),curdate(),"+warehouseId+" from stockbalance where wareHouseId="+warehouseId+" and createdDate<SUBDATE(CURDATE(),1) group by productId";
        sqlQuery = session.createSQLQuery(sql);
        int result = sqlQuery.executeUpdate();
        session.close();

        return result;
    }

    public List<BalanceByUser> balanceByUsers() {
        //Query query = session.getNamedQuery("findStockByStockCodeNativeSQL").setString("stockCode", "7277");
        session = getSession();
        Query query = session.getNamedQuery("balanceByUser");
        return query.list();
    }

    public List<BalanceByProduct> balanceByProduct() {
        session = getSession();
        Query query = session.getNamedQuery("balanceByProduct");
        return query.list();
    }

    public List<BalanceByDay> balanceByDay() {
        session = getSession();
        Query query = session.getNamedQuery("balanceByDay");
        return query.list();
    }

    public List<StockCurrent> balance(int warehouseId, int page, int size) {
        //insert into stockend (productId,qty,price,amount,endDate,warehouseId)
        // select productId,sum(qty) as first,avg(price),sum(amount),curdate(),1 from stockbalance where wareHouseId=1 group by productId
        Session session = getSession();
        String sql = "delete from stockcurrent where wareHouseId="+warehouseId;
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.executeUpdate();
//
//        sql = "insert into stockcurrent (productId,startBalance,orlogo,zarlaga,lastBalance,warehouseId)" +
//                " select p.id,(select sum(qty) from stockend where endDate='2016-11-30' and productId=p.id) as a," +
//                "(select sum(qty) from stockbalance where createdDate between '2016-12-01' and CURDATE() and productId=p.id and qty>0) as b," +
//                "(select sum(qty) from stockbalance where createdDate between '2016-12-01' and CURDATE() and productId=p.id and qty<0) as c," +
//                "0," + warehouseId +
//                " from product as p group by p.id";

        sql = "insert into stockcurrent (productId,startBalance,orlogo,zarlaga,lastBalance,wareHouseId)" +
                " select p.id,(select sum(qty) from stockend where productId=p.id) as a," +
                "(select sum(qty) from stockbalance where productId=p.id and qty>=0) as b," +
                "(select sum(qty) from stockbalance where productId=p.id and qty<=0) as c," +
                "0," + warehouseId +
                " from product as p";
        sqlQuery = session.createSQLQuery(sql);
        sqlQuery.executeUpdate();

        sql = "update stockcurrent set lastBalance=startBalance+orlogo+zarlaga where warehouseId="+warehouseId;
        sqlQuery = session.createSQLQuery(sql);
        sqlQuery.executeUpdate();

        crit = session.createCriteria(StockCurrent.class);
        crit.setFirstResult((page-1)*size);
        crit.setMaxResults(size);
        crit.add(Restrictions.eq("wareHouseId", new Integer(warehouseId)));
        List<StockCurrent> list = crit.list();
        return list;
    }

    public List<StockEnd> findAll(int page, int size) {
        return findAll(StockEnd.class, page, size);
    }
}

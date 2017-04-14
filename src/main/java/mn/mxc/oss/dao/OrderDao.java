package mn.mxc.oss.dao;

import mn.mxc.oss.domain.Details;
import mn.mxc.oss.domain.Orders;
import mn.mxc.oss.domain.StockBalance;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDao extends GenericDao<Orders> {
    public Orders findOne(final int id) {
        return findOne(Orders.class, id);
    }

    public List<Orders> findAll(int page, int size) {
        return findAll(Orders.class, page, size);
    }

    public List<Orders> findByNonNewOrder(int page, int size) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        crit = session.createCriteria(Orders.class);
        crit.setFirstResult((page - 1)*size);
        crit.setMaxResults(size);
        crit.add(Restrictions.eq("status", "success"));
        crit.addOrder(Order.desc("createdDate"));
        List<Orders> list = crit.list();
        total = totalUniq(crit);
        tx.commit();
        return list;
    }

    public List<Orders> findByNewOrder(int page, int size) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        crit = session.createCriteria(Orders.class);
        crit.setFirstResult((page - 1)*size);
        crit.setMaxResults(size);
        crit.add(Restrictions.ne("status", "success"));
        crit.addOrder(Order.desc("createdDate"));
        List<Orders> list = crit.list();
        total = totalUniq(crit);
        tx.commit();
        return list;
    }

    public List<Orders> findBySearch(int userId, String start, String end, int page, int size) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        crit = session.createCriteria(Orders.class);
        crit.setFirstResult((page - 1)*size);
        crit.setMaxResults(size);
        if (userId != 0)
            crit.add(Restrictions.eq("userId", new Integer(userId)));
        crit.add(Restrictions.between("createdDate", start+" 00:00:00", end+" 23:59:59"));
        List<Orders> list = crit.list();
        total = totalUniq(crit);
        tx.commit();
        return list;
    }

    public List<Orders> findByCoordinate(int userId, String start, String end, int page, int size) {
        session = getSession();
        Transaction tx = session.beginTransaction();
        crit = session.createCriteria(Orders.class);
        crit.setFirstResult((page - 1)*size);
        crit.setMaxResults(size);
        if (userId != 0)
            crit.add(Restrictions.eq("userId", new Integer(userId)));
        crit.add(Restrictions.between("createdDate", start+" 00:00:00", end+" 23:59:59"));
        crit.add(Restrictions.eq("mode", "zarlaga"));
        crit.add(Restrictions.gt("lat", 0.0f));
        crit.addOrder(Order.asc("createdDate"));
        List<Orders> list = crit.list();
        total = totalUniq(crit);
        tx.commit();
        return list;
    }

    public List<Orders> findByCustomerOrder(int customer_id, int page, int size){
        session = getSession();
        Transaction tx = session.beginTransaction();
        crit = session.createCriteria(Orders.class);
        crit.setFirstResult((page - 1)*size);
        crit.setMaxResults(size);
        crit.add(Restrictions.eq("customerId", customer_id));
        crit.addOrder(Order.desc("createdDate"));
        List<Orders> list = crit.list();
        total = totalUniq(crit);
        tx.commit();
        return list;

    }

    public void update(final Orders entity) {
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(entity);

        crit = session.createCriteria(StockBalance.class);
        crit.add(Restrictions.eq("orderId", new Integer(entity.getId())));
        List<StockBalance> stocklist = crit.list();
        for (int i = 0; i < stocklist.size(); i++) {
            StockBalance stock = stocklist.get(i);
            session.delete(stock);
        }

        if ("success".equals(entity.getStatus())) {
            List<Details> list = entity.getDetailsList();
            if (list != null)
                for (int i = 0; i < list.size(); i++) {
                    Details detail = list.get(i);
                    StockBalance stockBalance = new StockBalance();
                    stockBalance.setProductId(detail.getProductId());
                    stockBalance.setOrderId(entity.getId());
                    stockBalance.setAmount(detail.getAmount());
                    stockBalance.setPrice(detail.getPrice());
                    if (entity.getMode().equals("zarlaga"))
                        stockBalance.setQty(-detail.getQty());//zarlaga
                    else
                        stockBalance.setQty(+detail.getQty());//orlogo
                    stockBalance.setWareHouseId(entity.getWarehouseId());
                    stockBalance.setUserId(entity.getUserId());
                    stockBalance.setCustomerId(entity.getCustomerId());
                    session.saveOrUpdate(stockBalance);
                }
        } else
        if ("alert".equals(entity.getStatus())) {

        }

        tx.commit();
        session.close();
    }
    public void updateMany(final List<Orders> orders) {
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        int l = 0;
        while (l < orders.size()) {
            Orders entity = orders.get(l);
            session.saveOrUpdate(entity);
            crit = session.createCriteria(StockBalance.class);

            crit.add(Restrictions.eq("orderId", new Integer(entity.getId())));
            List<StockBalance> stocklist = crit.list();
            for (int i = 0; i < stocklist.size(); i++) {
                StockBalance stock = stocklist.get(i);
                session.delete(stock);
            }

            if ("success".equals(entity.getStatus())) {
                List<Details> list = entity.getDetailsList();
                if (list != null)
                    for (int i = 0; i < list.size(); i++) {
                        Details detail = list.get(i);
                        StockBalance stockBalance = new StockBalance();
                        stockBalance.setProductId(detail.getProductId());
                        stockBalance.setOrderId(entity.getId());
                        stockBalance.setAmount(detail.getAmount());
                        stockBalance.setPrice(detail.getPrice());
                        if (entity.getMode().equals("zarlaga"))
                            stockBalance.setQty(-detail.getQty());//zarlaga
                        else
                            stockBalance.setQty(+detail.getQty());//orlogo
                        stockBalance.setWareHouseId(entity.getWarehouseId());
                        stockBalance.setUserId(entity.getUserId());
                        stockBalance.setCustomerId(entity.getCustomerId());
                        session.saveOrUpdate(stockBalance);
                    }
            } else if ("alert".equals(entity.getStatus())) {

            }
            l++;
        }
        tx.commit();
        session.close();
    }

}

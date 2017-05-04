package mn.mxc.oss.dao;

import mn.mxc.oss.domain.Warehouse;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WarehouseDao extends GenericDao<Warehouse> {
    public Warehouse findOne(final int id) {
        return findOne(Warehouse.class, id);
    }
    public List<Warehouse> findByOwner(String owner){
        session = getSession();
        Transaction tx = session.beginTransaction();
        crit = session.createCriteria(Warehouse.class);
        crit.add(Restrictions.eq("owner", owner));
        List<Warehouse> ware = crit.list();
        tx.commit();
        return ware;
    }
    public List<Warehouse> findAll(int page, int size) {
        return findAll(Warehouse.class, page, size);
    }
}

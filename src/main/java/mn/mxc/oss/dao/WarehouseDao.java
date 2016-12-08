package mn.mxc.oss.dao;

import mn.mxc.oss.domain.Warehouse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WarehouseDao extends GenericDao<Warehouse> {
    public Warehouse findOne(final int id) {
        return findOne(Warehouse.class, id);
    }

    public List<Warehouse> findAll(int page, int size) {
        return findAll(Warehouse.class, page, size);
    }
}

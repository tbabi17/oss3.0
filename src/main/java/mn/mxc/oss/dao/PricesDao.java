package mn.mxc.oss.dao;

import mn.mxc.oss.domain.Prices;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PricesDao extends GenericDao<Prices> {
    public Prices findOne(final int id) {
        return findOne(Prices.class, id);
    }

    public List<Prices> findAll(int page, int size) {
        return findAll(Prices.class, page, size);
    }
}

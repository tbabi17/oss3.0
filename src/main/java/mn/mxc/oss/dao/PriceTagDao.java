package mn.mxc.oss.dao;

import mn.mxc.oss.domain.PriceTag;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PriceTagDao extends GenericDao<PriceTag> {
    public PriceTag findOne(final int id) {
        return findOne(PriceTag.class, id);
    }

    public List<PriceTag> findAll(int page, int size) {
        return findAll(PriceTag.class, page, size);
    }
}

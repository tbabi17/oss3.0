package mn.mxc.oss.dao;

import mn.mxc.oss.domain.Gps;

import java.util.List;

/**
 * Created by i7 on 12/09/2016.
 */
public class GpsDao extends GenericDao<Gps> {
    public Gps findOne(final int id) {
        return findOne(Gps.class, id);
    }
    public List<Gps> findAll(int page, int size) {
        return findAll(Gps.class, page, size);
    }
}

package mn.mxc.oss.services;

import mn.mxc.oss.dao.GpsDao;
import mn.mxc.oss.domain.Gps;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by i7 on 12/09/2016.
 */
public class GpsService {
    @Autowired
    GpsDao dao;

    public void save(Gps entity) {
        dao.save(entity);
    }
    public Gps findOne(int id) {
        return dao.findOne(id);
    }
    public List<Gps> findAll(int page, int size) {
        return dao.findAll(page, size);
    }
    public void update(Gps entity) {
        dao.update(entity);
    }
    public void delete(Gps entity) {
        dao.delete(entity);
    }
    public long total() { return dao.total(); }
}

package mn.mxc.oss.services;

import mn.mxc.oss.dao.RouteDao;
import mn.mxc.oss.domain.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RouteService {

	@Autowired
	RouteDao dao;

	public void save(Route entity) {
		dao.save(entity);
	}

	public Route findOne(int id) {
		return dao.findOne(id);
	}

	public List<Route> findAll(int page, int size) { return dao.findAll(page, size); }
	public List<Route> findByActive(int page, int size) { return dao.findByActive(page, size); }

	public void update(Route entity) {
		dao.update(entity);
	}

	public void delete(Route entity) {
		dao.delete(entity);
	}

	public long total() { return dao.total(); }
}

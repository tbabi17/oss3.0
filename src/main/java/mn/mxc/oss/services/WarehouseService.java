package mn.mxc.oss.services;

import mn.mxc.oss.dao.WarehouseDao;
import mn.mxc.oss.domain.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class WarehouseService {

	@Autowired
	WarehouseDao dao;

	public void save(Warehouse entity) {
		dao.save(entity);
	}

	public Warehouse findOne(int id) {
		return dao.findOne(id);
	}
	public List<Warehouse> findByOwner(String owner) {
		return dao.findByOwner(owner);
	}

	public List<Warehouse> findAll(int page, int size) { return dao.findAll(page, size); }

	public void update(Warehouse entity) {
		dao.update(entity);
	}

	public void delete(Warehouse entity) {
		dao.delete(entity);
	}

	public long total() { return dao.total(); }
}

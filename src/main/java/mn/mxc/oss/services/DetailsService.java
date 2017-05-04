package mn.mxc.oss.services;

import mn.mxc.oss.dao.DetailsDao;
import mn.mxc.oss.domain.Details;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DetailsService {

	@Autowired
	DetailsDao dao;

	public void save(Details entity) {
		dao.save(entity);
	}

	public Details findOne(int id) {
		return dao.findOne(id);
	}
	public List<Details> findAll(int page, int size) {
		return dao.findAll(page, size);
	}
	public List<Details> findByOrderId(int orderId, int page, int size) {
		return dao.findByOrderId(orderId, page, size);
	}
	public List<Details> recentProducts(List<String> orders){ return dao.recentProducts(orders);};
	public void update(Details entity) {
		dao.update(entity);
	}

	public void delete(Details entity) {
		dao.delete(entity);
	}

	public long total() { return dao.total(); }
}

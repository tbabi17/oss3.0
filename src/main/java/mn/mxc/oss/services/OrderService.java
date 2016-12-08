package mn.mxc.oss.services;

import mn.mxc.oss.dao.OrderDao;
import mn.mxc.oss.domain.Orders;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderService {

	@Autowired
	OrderDao dao;

	public void save(Orders entity) {
		dao.save(entity);
	}

	public Orders findOne(int id) {
		return dao.findOne(id);
	}
	public List<Orders> findByNewOrder(int page, int size) { return dao.findByNewOrder(page, size); }
	public List<Orders> findByNonNewOrder(int page, int size) { return dao.findByNonNewOrder(page, size); }
	public List<Orders> findAll(int page, int size) { return dao.findAll(page, size); }
	public List<Orders> findBySearch(int userId, String start, String end, int page, int size) { return dao.findBySearch(userId, start, end, page, size); }

	public void update(Orders entity) {
		dao.update(entity);
	}

	public void delete(Orders entity) {
		dao.delete(entity);
	}

	public long total() { return dao.total(); }
}

package mn.mxc.oss.services;

import mn.mxc.oss.dao.OrderDao;
import mn.mxc.oss.domain.Orders;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.List;

public class OrderService {

	@Autowired
	OrderDao dao;

	public void save(Orders entity) {
		dao.save(entity);
	}
	public void updateMany(List<Orders> orders){dao.updateMany(orders);};
	public Orders findOne(int id) {
		return dao.findOne(id);
	}
	public List<Orders> findByNewOrder(int page, int size) { return dao.findByNewOrder(page, size); }
	public List<Orders> findByStatus(int uid, String status,String date) { return dao.findByStatus(uid,status,date); }
	//public List<Orders> findBs(int uid, String status,String date){ return dao.findBs(uid,status,date); }
	public List<Orders> findByNonNewOrder(int page, int size) { return dao.findByNonNewOrder(page, size); }
	public List<Orders> findAll(int page, int size) { return dao.findAll(page, size); }
	public List<Orders> findBySearch(int userId, String start, String end, int page, int size) { return dao.findBySearch(userId, start, end, page, size); }
	public List<Orders> findByCoordinate(int userId, String start, String end, int page, int size) { return dao.findByCoordinate(userId, start, end, page, size); }
	public List<Orders> findByCustomerOrder(int customer_id,int page,int size){return dao.findByCustomerOrder(customer_id, page, size);};
	public List<Orders> recentOrders(int customer_id){ return dao.recentOrders(customer_id);}
	public void update(Orders entity) {
		dao.update(entity);
	}

	public void delete(Orders entity) {
		dao.delete(entity);
	}

	public long total() { return dao.total(); }
	public void close() { dao.close(); }
}

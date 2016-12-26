package mn.mxc.oss.services;

import mn.mxc.oss.dao.CustomerDao;
import mn.mxc.oss.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomerService {

	@Autowired
	CustomerDao dao;

	public void save(Customer entity) {
		dao.save(entity);
	}

	public Customer findOne(int id) {
		return dao.findOne(id);
	}

	public List<Customer> findByNonRoute() { return dao.findByNonRoute(); }
	public List<Customer> findBySearch(String value) { return dao.findBySearch(value); }
	public List<Customer> findAll(int page, int size) {
		return dao.findAll(page, size);
	}
	public List<Customer> findUserCustomer(int uid){
		return dao.findUserCustomer(uid);
	}

	public void update(Customer entity) {
		dao.update(entity);
	}

	public void delete(Customer entity) {
		dao.delete(entity);
	}

	public long total() { return dao.total(); }
}

package mn.mxc.oss.services;

import mn.mxc.oss.dao.ProductDao;
import mn.mxc.oss.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductService {

	@Autowired
	ProductDao dao;

	public void save(Product entity) {
		dao.save(entity);
	}

	public Product findOne(int id) {
		return dao.findOne(id);
	}
	public List<Product> findBySearch(String value) { return dao.findBySearch(value); }
	public List<Product> findAll(int page, int size) {
		return dao.findAll(page, size);
	}

	public void update(Product entity) {
		dao.update(entity);
	}

	public void delete(Product entity) {
		dao.delete(entity);
	}

	public long total() { return dao.total(); }
}

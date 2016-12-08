package mn.mxc.oss.services;

import mn.mxc.oss.dao.PriceTagDao;
import mn.mxc.oss.domain.PriceTag;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PriceTagService {

	@Autowired
	PriceTagDao dao;

	public void save(PriceTag entity) {
		dao.save(entity);
	}

	public PriceTag findOne(int id) {
		return dao.findOne(id);
	}

	public List<PriceTag> findAll(int page, int size) { return dao.findAll(page, size); }

	public void update(PriceTag entity) {
		dao.update(entity);
	}

	public void delete(PriceTag entity) {
		dao.delete(entity);
	}

	public long total() { return dao.total(); }
}

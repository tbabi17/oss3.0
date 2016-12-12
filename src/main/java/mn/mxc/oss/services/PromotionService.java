package mn.mxc.oss.services;

import mn.mxc.oss.dao.PromotionDao;
import mn.mxc.oss.domain.Promotion;
import mn.mxc.oss.domain.PromotionDetails;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PromotionService {

	@Autowired
	PromotionDao dao;

	public void save(Promotion entity) {
		dao.save(entity);
	}

	public Promotion findOne(int id) {
		return dao.findOne(id);
	}
	public List<Promotion> findAll(int page, int size) { return dao.findAll(page, size); }
	public List<Promotion> findByActive(int page, int size) { return dao.findByActive(page, size); }
	public List<Promotion> findByNonActive(int page, int size) { return dao.findByNonActive(page, size); }

	public void update(Promotion entity) {
		dao.update(entity);
	}

	public void delete(Promotion entity) {
		dao.delete(entity);
	}
	public void deleteDetails(PromotionDetails entity) { dao.deleteDetails(entity); }
	public long total() { return dao.total(); }
}

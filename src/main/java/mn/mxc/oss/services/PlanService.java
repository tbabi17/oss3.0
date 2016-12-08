package mn.mxc.oss.services;

import mn.mxc.oss.dao.PlanDao;
import mn.mxc.oss.domain.Plan;
import mn.mxc.oss.domain.PlanDetails;
import mn.mxc.oss.domain.PlanUsers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PlanService {

	@Autowired
	PlanDao dao;

	public void save(Plan entity) {
		dao.save(entity);
	}

	public Plan findOne(int id) {
		return dao.findOne(id);
	}
	public List<Plan> findAll(int page, int size) { return dao.findAll(page, size); }
	public List<Plan> findByActive(int page, int size) { return dao.findByActive(page, size); }
	public List<Plan> findByNonActive(int page, int size) { return dao.findByNonActive(page, size); }

	public void update(Plan entity) {
		dao.update(entity);
	}

	public void delete(Plan entity) {
		dao.delete(entity);
	}
	public void deleteDetails(PlanDetails entity) { dao.deleteDetails(entity); }
	public void deleteUsers(PlanUsers entity) { dao.deleteUsers(entity); }
	public long total() { return dao.total(); }
}

package mn.mxc.oss.services;

import mn.mxc.oss.dao.StockDao;
import mn.mxc.oss.domain.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StockEndService {

	@Autowired
	StockDao dao;

	public void save(StockCurrent entity) {
		dao.save(entity);
	}

	public StockCurrent findOne(int id) {
		return dao.findOne(id);
	}

	public List<StockCurrent> findAll(int page, int size) { return dao.findAll(page, size); }
	public List<StockCurrent> findAvailable(int page, int size) { return dao.findAvailable(page, size); }
	public List<BalanceByUser> balanceByUsers(String startDate, String endDate, int page, int size) { return dao.balanceByUsers(startDate, endDate); }
	public List<BalanceByProduct> balanceByProducts(String startDate, String endDate, int page, int size) { return dao.balanceByProduct(startDate, endDate); }
	public List<BalanceByDay> balanceByDay(String startDate, String endDate, int page, int size) { return dao.balanceByDay(startDate, endDate); }

	public int calc(int warehouseId) {
		return dao.calc(warehouseId);
	}

	public List<StockCurrent> balance(int warehouseId, String startDate, String endDate, int page, int size) {
		return dao.balance(warehouseId, startDate, endDate, page, size);
	}

	public long total() { return dao.total(); }
}

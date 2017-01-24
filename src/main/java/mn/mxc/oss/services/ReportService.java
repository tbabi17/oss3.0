package mn.mxc.oss.services;

import mn.mxc.oss.dao.ReportCustomerDao;
import mn.mxc.oss.dao.ReportProductDao;
import mn.mxc.oss.dao.ReportWeekDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReportService {

	@Autowired
	ReportWeekDao daoWeek;

	@Autowired
	ReportProductDao daoProduct;

	@Autowired
	ReportCustomerDao daoCustomer;

	public List report(String report, String startDate, String endDate, int page, int size) {
		switch (report) {
			case "weekDay": return daoWeek.report(startDate, endDate, page, size);
			case "productReport": return daoProduct.report(startDate, endDate, page, size);
			case "customerReport": return daoCustomer.report(startDate, endDate, page, size);
		}
		return null;
	}

	public long total(String report) {
		switch (report) {
			case "weekDay": return daoWeek.total();
			case "productReport": return daoProduct.total();
			case "customerReport": return daoCustomer.total();
		}
		return 0;
	}
}

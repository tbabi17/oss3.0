package mn.mxc.oss.services;

import mn.mxc.oss.dao.ReportDao;
import mn.mxc.oss.domain.ReportWeekDay;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReportService {

	@Autowired
	ReportDao dao;

	public List<ReportWeekDay> weekDay(String startDate, String endDate, int page, int size) {
		return dao.weekDay(startDate, endDate, page, size);
	}

	public long total() { return dao.total(); }
}

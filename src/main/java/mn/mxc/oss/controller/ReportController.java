package mn.mxc.oss.controller;

import mn.mxc.oss.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Hashtable;
import java.util.List;

@RestController
public class ReportController {
	
	@Autowired
	ReportService service;

	@RequestMapping(value = "report/view", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable report(@RequestParam String report, @RequestParam String startDate, @RequestParam String endDate, @RequestParam int page, @RequestParam int size,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		List list = service.report(report, startDate, endDate, page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total(report));
		pageable.put("data", list);

		return pageable;
	}
}

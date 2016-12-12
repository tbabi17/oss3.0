package mn.mxc.oss.controller;

import mn.mxc.oss.services.StockEndService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Hashtable;
import java.util.List;

@RestController
public class StockController {
	
	@Autowired
	StockEndService service;

	@RequestMapping(value = "stock/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findAll(@RequestParam int page, @RequestParam int size) {
		List list = service.findAll(page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		return pageable;
	}

	@RequestMapping(value = "stock/clear", method = RequestMethod.DELETE)
	public void delete(@RequestParam int warehouseId) {

	}

	@RequestMapping(value = "stock/balanceByUsers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable balanceByUsers(@RequestParam String startDate, @RequestParam String endDate,
									@RequestParam int page, @RequestParam int size) {
		List list = service.balanceByUsers(startDate, endDate, page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", list.size());
		pageable.put("data", list);
		return pageable;
	}

	@RequestMapping(value = "stock/balanceByProducts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable balanceByProducts(@RequestParam String startDate, @RequestParam String endDate, @RequestParam int page, @RequestParam int size) {
		List list = service.balanceByProducts(startDate, endDate, page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", list.size());
		pageable.put("data", list);
		return pageable;
	}

	@RequestMapping(value = "stock/balanceByDay", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable balanceByDay(@RequestParam String startDate, @RequestParam String endDate, @RequestParam int page, @RequestParam int size) {
		List list = service.balanceByDay(startDate, endDate, page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", list.size());
		pageable.put("data", list);
		return pageable;
	}

	@RequestMapping(value = "stock/calc", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public int calc(@RequestParam int warehouseId) {
		return service.calc(warehouseId);
	}

	@RequestMapping(value = "stock/balance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable balance(@RequestParam int warehouseId, @RequestParam String startDate, @RequestParam String endDate, @RequestParam int page, @RequestParam int size) {
		List list = service.balance(warehouseId, startDate, endDate, page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);

		return pageable;
	}
}

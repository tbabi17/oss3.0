package mn.mxc.oss.controller;

import mn.mxc.oss.domain.Orders;
import mn.mxc.oss.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Hashtable;
import java.util.List;

@RestController
public class OrderController {
	
	@Autowired
	OrderService service;

	@RequestMapping(value = "order/save", method = RequestMethod.POST, consumes="application/json", produces = "application/json; charset=utf-8", headers = "Accept=*/*")
	public Orders save(@RequestBody Orders entity) {
		service.save(entity);
		return entity;
	}

	@RequestMapping(value = "order/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findAll(@RequestParam int page, @RequestParam int size) {
		List list = service.findAll(page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		service.close();
		return pageable;
	}

	@RequestMapping(value = "order/findBySearch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findBySearch(@RequestParam int userId, @RequestParam String start, @RequestParam String end, @RequestParam int page, @RequestParam int size) {
		List list = service.findBySearch(userId, start, end, page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		service.close();
		return pageable;
	}

	@RequestMapping(value = "order/findByNewOrder", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findByNewOrder(@RequestParam int page, @RequestParam int size) {
		List list = service.findByNewOrder(page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		service.close();
		return pageable;
	}

	@RequestMapping(value = "order/findByNonNewOrder", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findByNonNewOrder(@RequestParam int page, @RequestParam int size) {
		List<Orders> list = service.findByNonNewOrder(page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		service.close();
		return pageable;
	}

	@RequestMapping(value = "order/findByCustomerOrder", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findByCustomerOrder(@RequestParam int customer_id,@RequestParam int page, @RequestParam int size,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		List<Orders> list = service.findByCustomerOrder(customer_id,page,size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		service.close();
		return pageable;
	}

	@RequestMapping(value = "order/delete", method = RequestMethod.DELETE)
	public Orders delete(@RequestParam int id) {
		Orders item = service.findOne(id);
		service.delete(item);
		return item;
	}

	@RequestMapping(value = "order/update", method = RequestMethod.PUT, consumes="application/json", produces = "application/json; charset=utf-8", headers = "Accept=*/*")
	public Orders update(@RequestBody Orders entity) {
		service.update(entity);
		return entity;
	}
}

package mn.mxc.oss.controller;

import mn.mxc.oss.domain.Customer;
import mn.mxc.oss.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;

@RestController
public class CustomerController {
	
	@Autowired
	CustomerService service;

	@RequestMapping(value = "customer/save", method = RequestMethod.POST, consumes="application/json", produces=MediaType.APPLICATION_JSON_VALUE)
	public Customer save(@RequestBody Customer entity) {
		service.save(entity);
		return entity;
	}

	@RequestMapping(value = "customer/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findAll(@RequestParam int page, @RequestParam int size) {
		List list = service.findAll(page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		return pageable;
	}

	@RequestMapping(value = "customer/findBySearch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findBySearch(@RequestParam String value) {
		List list = service.findBySearch(value);
		Hashtable pageable = new Hashtable();
		pageable.put("total", list.size());
		pageable.put("data", list);
		return pageable;
	}

	@RequestMapping(value = "customer/findByNonRoute", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findByNonRoute() {
		List list = service.findByNonRoute();
		Hashtable pageable = new Hashtable();
		pageable.put("total", list.size());
		pageable.put("data", list);
		return pageable;
	}

	@RequestMapping(value = "customer/delete", method = RequestMethod.DELETE)
	public Customer delete(@RequestParam int id) {
		Customer item = service.findOne(id);
		service.delete(item);
		return item;
	}

	@RequestMapping(value = "customer/update", method = RequestMethod.PUT, consumes="application/json", produces = "application/json; charset=utf-8", headers = "Accept=*/*")
	public Customer update(@RequestBody Customer entity) {
		service.update(entity);
		return entity;
	}
}

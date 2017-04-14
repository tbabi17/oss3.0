package mn.mxc.oss.controller;

import mn.mxc.oss.domain.Customer;
import mn.mxc.oss.domain.User;
import mn.mxc.oss.services.CustomerService;
import mn.mxc.oss.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

@RestController
public class CustomerController {
	
	@Autowired
	CustomerService service;/*Room44net*/
	@Autowired
	UserService uservice;

	@RequestMapping(value = "customer/save", method = RequestMethod.POST, consumes="application/json", produces=MediaType.APPLICATION_JSON_VALUE)
	public Customer save(@RequestBody Customer entity,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		service.save(entity);
		return entity;
	}

	@RequestMapping(value = "customer/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findAll(@RequestParam int page, @RequestParam int size,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
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
	@RequestMapping(value = "customer/findUserCustomer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findUserCustomer(@RequestParam int uid,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		User user = uservice.findOne(uid);
		List<Integer> directions = new ArrayList<Integer>(7);
		int mon = user.getMon();
		int tue = user.getTue();
		int wed = user.getWed();
		int thu = user.getThu();
		int fri = user.getFri();
		int sat = user.getSat();
		int sun = user.getSun();
		if(mon!=0){
			directions.add(mon);
		}
		if(tue!=0){
			directions.add(tue);
		}
		if(wed!=0){
			directions.add(wed);
		}
		if(thu!=0){
			directions.add(thu);
		}
		if(fri!=0){
			directions.add(fri);
		}
		if(sat!=0){
			directions.add(sat);
		}
		if(sun!=0){
			directions.add(sun);
		}
		List list = service.findUserCustomer(directions);
		System.out.println();
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
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
		//response.setHeader("Access-Control-Allow-Origin", "*");
		//response.setHeader("Access-Control-Allow-Headers", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		service.update(entity);
		return entity;
	}
}

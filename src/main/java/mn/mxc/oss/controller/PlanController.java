package mn.mxc.oss.controller;

import mn.mxc.oss.domain.Plan;
import mn.mxc.oss.domain.PlanDetails;
import mn.mxc.oss.domain.PlanUsers;
import mn.mxc.oss.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;

@RestController
public class PlanController {
	
	@Autowired
	PlanService service;

	@RequestMapping(value = "plan/save", method = RequestMethod.POST, consumes="application/json", produces = "application/json; charset=utf-8", headers = "Accept=*/*")
	public Plan save(@RequestBody Plan entity) {
		service.save(entity);
		return entity;
	}

	@RequestMapping(value = "plan/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findAll(@RequestParam int page, @RequestParam int size) {
		List list = service.findAll(page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		return pageable;
	}

	@RequestMapping(value = "plan/findByActive", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findByActive(@RequestParam int page, @RequestParam int size) {
		List list = service.findByActive(page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		return pageable;
	}

	@RequestMapping(value = "plan/findByNonActive", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findByNonNewOrder(@RequestParam int page, @RequestParam int size) {
		List list = service.findByNonActive(page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		return pageable;
	}

	@RequestMapping(value = "plan/delete", method = RequestMethod.DELETE)
	public Plan delete(@RequestParam int id) {
		Plan item = service.findOne(id);
		service.delete(item);
		return item;
	}

	@RequestMapping(value = "plandetail/delete", method = RequestMethod.PUT, consumes="application/json", produces = "application/json; charset=utf-8", headers = "Accept=*/*")
	public PlanDetails delete(@RequestBody PlanDetails item) {
		service.deleteDetails(item);
		return item;
	}

	@RequestMapping(value = "planuser/delete", method = RequestMethod.PUT, consumes="application/json", produces = "application/json; charset=utf-8", headers = "Accept=*/*")
	public PlanUsers delete(@RequestBody PlanUsers item) {
		service.deleteUsers(item);
		return item;
	}

	@RequestMapping(value = "plan/update", method = RequestMethod.PUT, consumes="application/json", produces = "application/json; charset=utf-8", headers = "Accept=*/*")
	public Plan update(@RequestBody Plan entity) {
		service.update(entity);
		return entity;
	}
}

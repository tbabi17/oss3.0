package mn.mxc.oss.controller;

import mn.mxc.oss.domain.Details;
import mn.mxc.oss.services.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;

@RestController
public class DetailsController {
	
	@Autowired
	DetailsService service;

	@RequestMapping(value = "details/save", method = RequestMethod.POST, consumes="application/json", produces=MediaType.APPLICATION_JSON_VALUE)
	public Details save(@RequestBody Details entity) {
		service.save(entity);
		return entity;
	}

	@RequestMapping(value = "details/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findAll(@RequestParam int page, @RequestParam int size) {
		List list = service.findAll(page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		return pageable;
	}

	@RequestMapping(value = "details/findByOrderId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findByOrderId(@RequestParam int orderId, @RequestParam int page, @RequestParam int size) {
		List list = service.findByOrderId(orderId, page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", list.size());
		pageable.put("data", list);
		return pageable;
	}
	
	@RequestMapping(value = "details/delete", method = RequestMethod.DELETE)
	public Details delete(@RequestParam int id) {
		Details item = service.findOne(id);
		service.delete(item);
		return item;
	}

	@RequestMapping(value = "details/update", method = RequestMethod.PUT, consumes="application/json", produces="text/plain;charset=UTF-8", headers = "Accept=*/*")
	public Details update(@RequestBody Details entity) {
		service.update(entity);
		return entity;
	}
}

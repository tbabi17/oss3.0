package mn.mxc.oss.controller;

import mn.mxc.oss.domain.Route;
import mn.mxc.oss.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Hashtable;
import java.util.List;

@RestController
public class RouteController {
	
	@Autowired
	RouteService service;

	@RequestMapping(value = "route/save", method = RequestMethod.POST, consumes="application/json", produces=MediaType.APPLICATION_JSON_VALUE)
	public Route save(@RequestBody Route entity) {
		service.save(entity);
		return entity;
	}

	@RequestMapping(value = "route/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findAll(@RequestParam int page, @RequestParam int size,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		List list = service.findAll(page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		return pageable;
	}

	@RequestMapping(value = "route/findByActive", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findByActive(@RequestParam int page, @RequestParam int size) {
		List list = service.findByActive(page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		return pageable;
	}

	@RequestMapping(value = "route/delete", method = RequestMethod.DELETE)
	public Route delete(@RequestParam int id) {
		Route item = service.findOne(id);
		service.delete(item);
		return item;
	}

	@RequestMapping(value = "route/update", method = RequestMethod.PUT, consumes="application/json", produces = "application/json; charset=utf-8", headers = "Accept=*/*")
	public Route update(@RequestBody Route entity) {
		service.update(entity);
		return entity;
	}

}

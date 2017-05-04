package mn.mxc.oss.controller;

import mn.mxc.oss.domain.Warehouse;
import mn.mxc.oss.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Hashtable;
import java.util.List;

@RestController
public class WarehouseController {
	
	@Autowired
	WarehouseService service;

	@RequestMapping(value = "warehouse/save", method = RequestMethod.POST, consumes="application/json", produces=MediaType.APPLICATION_JSON_VALUE)
	public Warehouse save(@RequestBody Warehouse entity) {
		service.save(entity);
		return entity;
	}

	@RequestMapping(value = "warehouse/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findAll(@RequestParam int page, @RequestParam int size,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		List list = service.findAll(page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		return pageable;
	}

	@RequestMapping(value = "warehouse/delete", method = RequestMethod.DELETE)
	public Warehouse delete(@RequestParam int id) {
		Warehouse item = service.findOne(id);
		service.delete(item);
		return item;
	}
	@RequestMapping(value = "warehouse/findByOwner", method = RequestMethod.GET)
	public Hashtable findByOwner(@RequestParam String owner, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		List<Warehouse> list = service.findByOwner(owner);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		return pageable;
	}
	@RequestMapping(value = "warehouse/update", method = RequestMethod.PUT, consumes="application/json", produces = "application/json; charset=utf-8", headers = "Accept=*/*")
	public Warehouse update(@RequestBody Warehouse entity,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		service.update(entity);
		return entity;
	}

}

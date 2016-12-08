package mn.mxc.oss.controller;

import mn.mxc.oss.domain.Prices;
import mn.mxc.oss.services.PricesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;

@RestController
public class PricesController {
	
	@Autowired
	PricesService service;

	@RequestMapping(value = "prices/save", method = RequestMethod.POST, consumes="application/json", produces=MediaType.APPLICATION_JSON_VALUE)
	public Prices save(@RequestBody Prices entity) {
		service.save(entity);
		return entity;
	}

	@RequestMapping(value = "prices/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findAll(@RequestParam int page, @RequestParam int size) {
		List list = service.findAll(page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		return pageable;
	}

	@RequestMapping(value = "prices/delete", method = RequestMethod.DELETE)
	public Prices delete(@RequestParam int id) {
		Prices item = service.findOne(id);
		service.delete(item);
		return item;
	}

	@RequestMapping(value = "prices/update", method = RequestMethod.PUT, consumes="application/json", produces = "application/json; charset=utf-8", headers = "Accept=*/*")
	public Prices update(@RequestBody Prices entity) {
		service.update(entity);
		return entity;
	}

}

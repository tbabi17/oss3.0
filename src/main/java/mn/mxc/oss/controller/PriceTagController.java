package mn.mxc.oss.controller;

import mn.mxc.oss.domain.PriceTag;
import mn.mxc.oss.services.PriceTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;

@RestController
public class PriceTagController {
	
	@Autowired
	PriceTagService service;

	@RequestMapping(value = "pricetag/save", method = RequestMethod.POST, consumes="application/json", produces=MediaType.APPLICATION_JSON_VALUE)
	public PriceTag save(@RequestBody PriceTag entity) {
		service.save(entity);
		return entity;
	}

	@RequestMapping(value = "pricetag/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findAll(@RequestParam int page, @RequestParam int size) {
		List list = service.findAll(page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		return pageable;
	}

	@RequestMapping(value = "pricetag/delete", method = RequestMethod.DELETE)
	public PriceTag delete(@RequestParam int id) {
		PriceTag item = service.findOne(id);
		service.delete(item);
		return item;
	}

	@RequestMapping(value = "pricetag/update", method = RequestMethod.PUT, consumes="application/json", produces = "application/json; charset=utf-8", headers = "Accept=*/*")
	public PriceTag update(@RequestBody PriceTag entity) {
		service.update(entity);
		return entity;
	}

}

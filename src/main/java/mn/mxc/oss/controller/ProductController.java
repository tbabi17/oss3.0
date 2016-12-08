package mn.mxc.oss.controller;

import mn.mxc.oss.domain.Product;
import mn.mxc.oss.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;

@RestController
public class ProductController {
	
	@Autowired
	ProductService service;

	@RequestMapping(value = "product/save", method = RequestMethod.POST, consumes="application/json", produces=MediaType.APPLICATION_JSON_VALUE)
	public Product save(@RequestBody Product entity) {
		service.save(entity);
		return entity;
	}

	@RequestMapping(value = "product/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findAll(@RequestParam int page, @RequestParam int size) {
		List list = service.findAll(page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		return pageable;
	}

	@RequestMapping(value = "product/findBySearch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findBySearch(@RequestParam String value) {
		List list = service.findBySearch(value);
		Hashtable pageable = new Hashtable();
		pageable.put("total", list.size());
		pageable.put("data", list);
		return pageable;
	}
	
	@RequestMapping(value = "product/delete", method = RequestMethod.DELETE)
	public Product delete(@RequestParam int id) {
		Product item = service.findOne(id);
		service.delete(item);
		return item;
	}

	@RequestMapping(value = "product/update", method = RequestMethod.PUT, consumes="application/json", produces="text/plain;charset=UTF-8", headers = "Accept=*/*")
	public Product update(@RequestBody Product entity) {
		service.update(entity);
		return entity;
	}
}

package mn.mxc.oss.controller;

import mn.mxc.oss.domain.Promotion;
import mn.mxc.oss.domain.PromotionDetails;
import mn.mxc.oss.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Hashtable;
import java.util.List;

@RestController
public class PromotionController {
	
	@Autowired
	PromotionService service;

	@RequestMapping(value = "promotion/save", method = RequestMethod.POST, consumes="application/json", produces = "application/json; charset=utf-8", headers = "Accept=*/*")
	public Promotion save(@RequestBody Promotion entity) {
		service.save(entity);
		return entity;
	}

	@RequestMapping(value = "promotion/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findAll(@RequestParam int page, @RequestParam int size) {
		List list = service.findAll(page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		return pageable;
	}

	@RequestMapping(value = "promotion/findByActive", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findByActive(@RequestParam int page, @RequestParam int size,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		List list = service.findByActive(page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		return pageable;
	}

	@RequestMapping(value = "promotion/findByNonActive", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findByNonNewOrder(@RequestParam int page, @RequestParam int size,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		List<Promotion> list = service.findByNonActive(page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		return pageable;
	}

	@RequestMapping(value = "promotion/delete", method = RequestMethod.DELETE)
	public Promotion delete(@RequestParam int id) {
		Promotion item = service.findOne(id);
		service.delete(item);
		return item;
	}

	@RequestMapping(value = "promotiondetail/delete", method = RequestMethod.PUT, consumes="application/json", produces = "application/json; charset=utf-8", headers = "Accept=*/*")
	public PromotionDetails delete(@RequestBody PromotionDetails item) {
		service.deleteDetails(item);
		return item;
	}

	@RequestMapping(value = "promotion/update", method = RequestMethod.PUT, consumes="application/json", produces = "application/json; charset=utf-8", headers = "Accept=*/*")
	public Promotion update(@RequestBody Promotion entity) {
		service.update(entity);
		return entity;
	}
}

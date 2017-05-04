package mn.mxc.oss.controller;
import mn.mxc.oss.dao.ProductDao;
import mn.mxc.oss.domain.Details;
import mn.mxc.oss.domain.Orders;
import mn.mxc.oss.domain.Product;
import mn.mxc.oss.services.DetailsService;
import mn.mxc.oss.services.OrderService;
import mn.mxc.oss.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
public class ProductController {

	@Autowired(required=true)
	private ProductService service;
	@Autowired(required=true)
	private OrderService oservice;
	@Autowired(required=true)
	private DetailsService dservice;

	@Qualifier(value="service")
	public void setProductService(ProductService ps){
		this.service = ps;
	}
	@Qualifier(value="oservice")
	public void setOservice(OrderService oservice) {this.oservice = oservice;}
	@Qualifier(value="dservice")
	public void setDservice(DetailsService dservice) {this.dservice = dservice;}

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

	@RequestMapping(value = "product/findAvailable", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findAvailable(@RequestParam int warehouseId,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		List list = service.findByAvailable(warehouseId);
		Hashtable pageable = new Hashtable();
		pageable.put("total", list.size());
		pageable.put("data", list);
		return pageable;
	}
	@RequestMapping(value = "product/findHistoryProducts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findHistoryProducts(@RequestParam int customer_id,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Hashtable pageable = new Hashtable();
		List<Orders> ro = oservice.recentOrders(customer_id);
		int i = 0;
		List<String> orders = new ArrayList<>();
		while(i < ro.size()){
			orders.add(ro.get(i).getOrderId());
			i++;
		}
		pageable.put("total", 0);
		if(orders.size()>0) {
			List<Details> list = dservice.recentProducts(orders);
			pageable.put("total", list.size());
			pageable.put("data", list);
		}
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
	@RequestMapping(value= "product/findBrands", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findBrands(HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		List list = service.findBrands();
		Hashtable pageable = new Hashtable();
		pageable.put("total", list.size());
		pageable.put("data", list);
		return pageable;
	}
	@RequestMapping(value= "product/findNyaravs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findNyaravs(HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		List list = service.findNyaravs();
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

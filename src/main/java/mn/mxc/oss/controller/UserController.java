package mn.mxc.oss.controller;

import mn.mxc.oss.domain.User;
import mn.mxc.oss.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
@RestController
public class UserController {
	
	@Autowired
	UserService service;

	@RequestMapping(value = "user/save", method = RequestMethod.POST, consumes="application/json", produces=MediaType.APPLICATION_JSON_VALUE)
	public User save(@RequestBody User entity) {
		service.save(entity);
		return entity;
	}

	@RequestMapping(value = "user/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable findAll(@RequestParam int page, @RequestParam int size) {
		List list = service.findAll(page, size);
		Hashtable pageable = new Hashtable();
		pageable.put("total", service.total());
		pageable.put("data", list);
		System.out.println(list);
		return pageable;
	}

	@RequestMapping(value = "user/findBySearch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable page(@RequestParam String value) {
		List list = service.findBySearch(value);
		Hashtable pageable = new Hashtable();
		pageable.put("total", list.size());
		pageable.put("data", list);
		return pageable;
	}
	
	@RequestMapping(value = "user/delete", method = RequestMethod.DELETE)
	public User delete(@RequestParam int id) {
		User item = service.findOne(id);
		service.delete(item);
		return item;
	}


	@RequestMapping(value = "user/update", method = RequestMethod.PUT, consumes="application/json", produces = "application/json; charset=utf-8", headers = "Accept=*/*")
	public User update(@RequestBody User entity) {
		service.update(entity);
		return entity;
	}
	@RequestMapping(value="user/login",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Hashtable PostService(@RequestParam(value="user", required=false) String username, @RequestParam(value="password",required=false) String password,HttpServletRequest request) throws ServletException, IOException {
		String object = "{\"correct\":\"correct\"}";
		List list = service.findLogin(username,password);
		Hashtable pageable = new Hashtable();
		pageable.put("total",  list.size());
		if (list.size()>0) {
			pageable.put("status",true);
			HttpSession session = request.getSession();
			String user = username;
			session.setAttribute("logged", username);
			System.out.println("logged user is "+session.getAttribute("logged"));
		}else{
			pageable.put("status",false);
		}
		System.out.println(list);
		return pageable;
	}
	@RequestMapping(value="user/checkSession",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public Hashtable checkSession(HttpServletRequest req){
		HttpSession session = req.getSession(true);
		String logged = (String) req.getAttribute("logged");
		Hashtable data = new Hashtable();
		if(req.getAttribute("logged")!=null){
			data.put("status",true);
		}else{
			data.put("status",false);
		}
		return data;
	}
}

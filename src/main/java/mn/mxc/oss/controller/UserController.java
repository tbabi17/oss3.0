package mn.mxc.oss.controller;

import mn.mxc.oss.domain.User;
import mn.mxc.oss.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	@RequestMapping(value="user/login",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public User login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) throws ServletException, IOException {
		User user = service.findLogin(username, password);
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("logged", user);
		}
		return user;
	}
	@RequestMapping(value="user/checkSession",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public User checkSession(HttpServletRequest req) {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("logged");
		return user;
	}

	@RequestMapping(value="user/logout",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public User logout(HttpServletRequest req, HttpServletResponse response) {
		HttpSession session = req.getSession();
		session.setAttribute("logged", null);
		try {
			response.sendRedirect("/");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new User();
	}
}

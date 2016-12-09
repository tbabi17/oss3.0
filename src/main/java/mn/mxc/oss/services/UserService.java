package mn.mxc.oss.services;

import mn.mxc.oss.dao.UserDao;
import mn.mxc.oss.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService {

	@Autowired
	UserDao dao;

	public void save(User entity) {
		dao.save(entity);
	}

	public User findOne(int id) {
		return dao.findOne(id);
	}

	public List<User> findBySearch(String value) { return dao.findBySearch(value); }
	public User findLogin(String user,String pass){return dao.findLogin(user,pass);}
	public List<User> findAll(int page, int size) { return dao.findAll(page, size); }

	public void update(User entity) {
		dao.update(entity);
	}

	public void delete(User entity) {
		dao.delete(entity);
	}

	public long total() { return dao.total(); }
}

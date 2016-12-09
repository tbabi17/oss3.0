package mn.mxc.oss.dao;

import mn.mxc.oss.domain.User;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao extends GenericDao<User> {
    public User findOne(final int id) {
        return findOne(User.class, id);
    }

    public List<User> findAll(int page, int size) {
        return findAll(User.class, page, size);
    }

    public List<User> findBySearch(String value) {
        Session session = getSession();
        crit = session.createCriteria(User.class);
        crit.setFirstResult(0);
        crit.setMaxResults(10);
        Criterion firstName = Restrictions.like("firstName", "%"+value+"%");
        Criterion lastName = Restrictions.like("lastName", "%"+value+"%");
        Criterion phone = Restrictions.like("phone", "%"+value+"%");
        Disjunction exp1 = Restrictions.or(firstName, lastName, phone);
        crit.add(exp1);
        List<User> list = crit.list();
        return list;
    }
    public User findLogin(String user,String password){
        Session session = getSession();
        crit = session.createCriteria(User.class);
        crit.setFirstResult(0);
        crit.setMaxResults(1);
        crit.add(Restrictions.eq("owner",user));
        crit.add(Restrictions.eq("password",password));
        List<User> list = crit.list();
        if (list.size() > 0)
            return list.get(0);
        return null;
    }
}

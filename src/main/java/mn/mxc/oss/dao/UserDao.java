package mn.mxc.oss.dao;

import mn.mxc.oss.domain.User;
import org.hibernate.Transaction;
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
        session = getSession();
        Transaction tx = session.beginTransaction();
        crit = session.createCriteria(User.class);
        crit.setFirstResult(0);
        crit.setMaxResults(10);
        Criterion firstName = Restrictions.like("firstName", "%"+value+"%");
        Criterion lastName = Restrictions.like("lastName", "%"+value+"%");
        Criterion phone = Restrictions.like("phone", "%"+value+"%");
        Disjunction exp1 = Restrictions.or(firstName, lastName, phone);
        crit.add(exp1);
        List<User> list = crit.list();
        total = totalUniq(crit);
        tx.commit();
        return list;
    }
    public User findLogin(String user,String password){
        session = getSession();
        Transaction tx = session.beginTransaction();
        crit = session.createCriteria(User.class);
        crit.setFirstResult(0);
        crit.setMaxResults(1);
        crit.add(Restrictions.eq("owner",user));
        crit.add(Restrictions.eq("password",password));
        List<User> list = crit.list();
        if (list.size() > 0) {
            tx.commit();
            return list.get(0);
        }
        tx.commit();
        return null;
    }
}

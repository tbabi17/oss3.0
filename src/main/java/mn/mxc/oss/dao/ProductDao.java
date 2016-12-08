package mn.mxc.oss.dao;

import mn.mxc.oss.domain.Product;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao extends GenericDao<Product> {
    public Product findOne(final int id) {
        return findOne(Product.class, id);
    }

    public List<Product> findAll(int page, int size) {
        return findAll(Product.class, page, size);
    }

    public List<Product> findBySearch(String value) {
        Session session = getSession();
        crit = session.createCriteria(Product.class);
        crit.setFirstResult(0);
        crit.setMaxResults(10);
        Criterion name = Restrictions.like("name", "%"+value+"%");
        Criterion brand = Restrictions.like("brand", "%"+value+"%");
        LogicalExpression exp1 = Restrictions.or(name, brand);
        crit.add(exp1);
        List<Product> list = crit.list();
        return list;
    }
}

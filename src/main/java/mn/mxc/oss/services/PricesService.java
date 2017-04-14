package mn.mxc.oss.services;

import mn.mxc.oss.dao.PricesDao;
import mn.mxc.oss.domain.Prices;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PricesService {

	@Autowired
	PricesDao dao;

	public void save(Prices entity) {
		dao.save(entity);
	}
	public void save_or_update(Prices entity){
		Prices price = this.findByProductId(entity.getProductId());
		if(price!=null){
			this.save(entity);
		}else{
			Prices pr = new Prices();
			pr.setId(entity.getId());
			pr.setProductId(entity.getProductId());
			pr.setPriceTagId(entity.getPriceTagId());
			pr.setPrice(entity.getPrice());
			this.save(pr);
		}
	}
	public Prices findByProductId(int pid){ return dao.findByProductId(pid);}
	public Prices findOne(int id) {
		return dao.findOne(id);
	}

	public List<Prices> findAll(int page, int size) { return dao.findAll(page, size); }

	public void update(Prices entity) {
		dao.update(entity);
	}

	public void delete(Prices entity) {
		dao.delete(entity);
	}

	public long total() { return dao.total(); }
}

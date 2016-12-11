package mn.mxc.oss.domain;

import javax.persistence.*;
import java.util.List;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "productsAvailable",
                query = "select * from product where id in (select productId from stockend where qty>0 and warehouseId=:warehouseId)",
                resultClass = Product.class
        )
})
@Entity
public class Product implements java.io.Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column
    private String code;
    @Column
    private String name;
    @Column
    private String brand;
    @Column
    private String createdDate;
    @Column String status;

    @OneToMany(mappedBy="product", cascade = CascadeType.ALL)
    private List<Prices> priceList;

    public List<Prices> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<Prices> priceList) {
        this.priceList = priceList;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

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
    @Column String img;
    @Column String type;
    @Column double size;
    @Column double discount;
    @Column String descr;

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}

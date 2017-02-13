package mn.mxc.oss.domain;

import javax.persistence.*;
import java.util.List;

@Table(name="Product")
@Entity
public class ProductOnly {
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
    private String img;
    @Column String type;
    @Column int size;
    @Column double discount;

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @OneToMany(mappedBy="product", cascade = CascadeType.ALL)
    private List<Prices> priceList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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

    public List<Prices> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<Prices> priceList) {
        this.priceList = priceList;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

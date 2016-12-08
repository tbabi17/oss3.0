package mn.mxc.oss.domain;

import javax.persistence.*;

@Entity
public class Prices {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column
    private int productId;
    @Column
    private double price;
    @Column
    private int priceTagId;

    @OneToOne
    @JoinColumn(name="priceTagId", insertable=false, updatable=false)
    private PriceTag priceTag;

    public PriceTag getPriceTag() {
        return priceTag;
    }

    public void setPriceTag(PriceTag priceTag) {
        this.priceTag = priceTag;
    }

    public int getPriceTagId() {
        return priceTagId;
    }

    public void setPriceTagId(int priceTagId) {
        this.priceTagId = priceTagId;
    }

    @ManyToOne
    @JoinColumn(name="productId", insertable=false, updatable=false)
    private Product product;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

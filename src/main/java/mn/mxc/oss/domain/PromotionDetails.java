package mn.mxc.oss.domain;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
public class PromotionDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column
    private String promotionId;
    @Column
    private int productId;
    @Column
    private double qty;
    @Column
    private double price;
    @Column
    private double amount;

    @ManyToOne
    @JoinColumn(name="promotionId", referencedColumnName = "promotionId", insertable=false, updatable=false)
    private Promotion details;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="productId", insertable=false, updatable=false)
    private ProductOnly product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ProductOnly getProduct() {
        return product;
    }

    public void setProduct(ProductOnly product) {
        this.product = product;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }
}

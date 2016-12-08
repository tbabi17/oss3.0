package mn.mxc.oss.domain;

import javax.persistence.*;

@Entity
public class PlanDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column
    private String planId;
    @Column
    private int productId;
    @Column
    private double qty;
    @Column
    private double price;
    @Column
    private double amount;

    @ManyToOne
    @JoinColumn(name="planId", referencedColumnName = "planId", insertable=false, updatable=false)
    private Plan details;

    @OneToOne
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

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }


}

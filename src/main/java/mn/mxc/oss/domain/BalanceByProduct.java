package mn.mxc.oss.domain;

import javax.persistence.*;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "balanceByProduct",
                query = "select floor(rand()*10000000) as id, productId, abs(sum(qty)) as qty,sum(amount) as amount from stockbalance s where s.qty<0 and createdDate between :startDate and :endDate  group by productId order by sum(amount) desc",
                resultClass = BalanceByProduct.class
        )
})
@Entity
public class BalanceByProduct implements java.io.Serializable {
    @Id
    @Column
    @GeneratedValue
    private int id;

    @Column
    private int productId;
    @Column
    private double qty;
    @Column
    private double amount;
    @OneToOne
    @JoinColumn(name="productId", insertable=false, updatable=false)
    private ProductOnly product;

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductOnly getProduct() {
        return product;
    }

    public void setProduct(ProductOnly product) {
        this.product = product;
    }
}

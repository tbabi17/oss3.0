package mn.mxc.oss.domain;

import javax.persistence.*;

@Table(name="StockCurrent")
@Entity
public class StockCurrent {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Column
    private int productId;
    @Column
    private double startBalance;
    @Column
    private double orlogo;
    @Column
    private double zarlaga;
    @Column
    private double lastBalance;
    @Column
    private int wareHouseId;

    @OneToOne
    @JoinColumn(name="productId", insertable=false, updatable=false)
    private ProductOnly product;

    public ProductOnly getProduct() {
        return product;
    }

    public void setProduct(ProductOnly product) {
        this.product = product;
    }

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

    public double getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(double startBalance) {
        this.startBalance = startBalance;
    }

    public double getOrlogo() {
        return orlogo;
    }

    public void setOrlogo(double orlogo) {
        this.orlogo = orlogo;
    }

    public double getZarlaga() {
        return zarlaga;
    }

    public void setZarlaga(double zarlaga) {
        this.zarlaga = zarlaga;
    }

    public double getLastBalance() {
        return lastBalance;
    }

    public void setLastBalance(double lastBalance) {
        this.lastBalance = lastBalance;
    }

    public int getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(int wareHouseId) {
        this.wareHouseId = wareHouseId;
    }
}

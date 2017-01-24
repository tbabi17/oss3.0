package mn.mxc.oss.domain;

import javax.persistence.*;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "reportProduct",
                query = "insert into reportProduct (productId,firstCount,firstAmt,inCount,inAmt,outCount,outAmt,lastCount,lastAmt,entry,owners) select p.id,ifnull((select sum(qty) from stockend where productId=p.id and endDate=(select endDate from stockend where endDate<:startDate limit 1)),0) as firstCount,ifnull((select sum(amount) from stockend where productId=p.id and endDate=(select endDate from stockend where endDate<:startDate limit 1)),0) as firstAmt,ifnull((select sum(qty) from stockbalance where productId=p.id and qty>=0 and createdDate between :startDate and :endDate),0) as inCount,ifnull((select sum(amount) from stockbalance where productId=p.id and qty>=0 and createdDate between :startDate and :endDate),0) as inAmt,ifnull((select sum(qty) from stockbalance where productId=p.id and qty<=0 and createdDate between :startDate and :endDate),0) as outCount,ifnull((select sum(amount) from stockbalance where productId=p.id and qty<=0 and createdDate between :startDate and :endDate),0) as outAmt,0,0,(select count(distinct customerId)*count(distinct orderId) from stockbalance where productId=p.id and createdDate between :startDate and :endDate),(select count(distinct userId)*count(distinct orderId) from stockbalance where productId=p.id and createdDate between :startDate and :endDate) as owners from product as p"
        )
})
@Table(name="ReportProduct")
@Entity
public class ReportProduct {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int productId;

    @Column
    private double firstCount;
    @Column
    private double firstAmt;

    @Column
    private double inCount;
    @Column
    private double inAmt;

    @Column
    private double outCount;
    @Column
    private double outAmt;

    @Column
    private double lastCount;
    @Column
    private double lastAmt;

    @Column
    private int entry;
    @Column
    private int owners;

    @OneToOne
    @JoinColumn(name="productId", insertable=false, updatable=false)
    private Product product;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getFirstCount() {
        return firstCount;
    }

    public void setFirstCount(double firstCount) {
        this.firstCount = firstCount;
    }

    public double getFirstAmt() {
        return firstAmt;
    }

    public void setFirstAmt(double firstAmt) {
        this.firstAmt = firstAmt;
    }

    public double getInCount() {
        return inCount;
    }

    public void setInCount(double inCount) {
        this.inCount = inCount;
    }

    public double getInAmt() {
        return inAmt;
    }

    public void setInAmt(double inAmt) {
        this.inAmt = inAmt;
    }

    public double getOutCount() {
        return outCount;
    }

    public void setOutCount(double outCount) {
        this.outCount = outCount;
    }

    public double getOutAmt() {
        return outAmt;
    }

    public void setOutAmt(double outAmt) {
        this.outAmt = outAmt;
    }

    public double getLastCount() {
        return lastCount;
    }

    public void setLastCount(double lastCount) {
        this.lastCount = lastCount;
    }

    public double getLastAmt() {
        return lastAmt;
    }

    public void setLastAmt(double lastAmt) {
        this.lastAmt = lastAmt;
    }

    public int getEntry() {
        return entry;
    }

    public void setEntry(int entry) {
        this.entry = entry;
    }

    public int getOwners() {
        return owners;
    }

    public void setOwners(int owners) {
        this.owners = owners;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

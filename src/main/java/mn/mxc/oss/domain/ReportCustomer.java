package mn.mxc.oss.domain;

import javax.persistence.*;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "reportCustomer",
                query = "insert into reportcustomer (customerId,entryM,entryE,owners,products,qty,amount,lastDate) select id,0,ifnull((select count(DISTINCT customerId) from stockbalance where customerId=c.id and createdDate between :startDate and :endDate group by orderId),0),ifnull((select count(DISTINCT userId) from stockbalance where customerId=c.id and createdDate between :startDate and :endDate),0),ifnull((select count(DISTINCT productId) from stockbalance where customerId=c.id and createdDate between :startDate and :endDate),0),ifnull((select abs(sum(qty)) from stockbalance where customerId=c.id and createdDate between :startDate and :endDate),0),ifnull((select sum(amount) from stockbalance where customerId=c.id and createdDate between :startDate and :endDate),0),(select ifnull(max(createdDate),'1980-01-01 00:00:00') from orders where customerId=c.id and createdDate between :startDate and :endDate) from customer as c where id in (select customerId from orders where createdDate between :startDate and :endDate)"
        )
})
@Table(name="ReportCustomer")
@Entity
public class ReportCustomer {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int customerId;

    @Column
    private int entryM;
    @Column
    private int entryE;

    @Column
    private int owners;
    @Column
    private int products;

    @Column
    private double qty;
    @Column
    private double amount;

    @Column
    private String lastDate;

    @OneToOne
    @JoinColumn(name="customerId", insertable=false, updatable=false)
    private CustomerOnly customer;


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getEntryM() {
        return entryM;
    }

    public void setEntryM(int entryM) {
        this.entryM = entryM;
    }

    public int getEntryE() {
        return entryE;
    }

    public void setEntryE(int entryE) {
        this.entryE = entryE;
    }

    public int getOwners() {
        return owners;
    }

    public void setOwners(int owners) {
        this.owners = owners;
    }

    public int getProducts() {
        return products;
    }

    public void setProducts(int products) {
        this.products = products;
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

    public CustomerOnly getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerOnly customer) {
        this.customer = customer;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }
}

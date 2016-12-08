package mn.mxc.oss.domain;

import javax.persistence.*;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "balanceByDay",
                query = "select floor(rand()*10000000) as id, date(createdDate) as date,count(DISTINCT userId) as userCount,count(DISTINCT customerId) as customerCount,count(DISTINCT productId) as productCount,count(DISTINCT orderId) as orderCount,abs(sum(qty)) as qty,sum(amount) as amount from stockbalance s where s.qty<0 group by date(createdDate) order by date(createdDate) desc",
                resultClass = BalanceByDay.class
        )
})
@Entity
public class BalanceByDay implements java.io.Serializable {
    @Id
    @Column
    @GeneratedValue
    private int id;

    @Column
    private String date;
    @Column
    private int userCount;
    @Column
    private int productCount;
    @Column
    private int customerCount;
    @Column
    private int orderCount;
    @Column
    private double qty;
    @Column
    private double amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
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

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
}

package mn.mxc.oss.domain;

import javax.persistence.*;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "balanceByUser",
                query = "select floor(rand()*10000000) as id,userId,abs(sum(qty)) as qty,sum(amount) as amount from stockbalance s where s.qty<0 and createdDate between :startDate and :endDate group by userId order by sum(amount) desc",
                resultClass = BalanceByUser.class
        )
})
@Entity
public class BalanceByUser implements java.io.Serializable {
    @Id
    @Column
    @GeneratedValue
    private int id;

    @Column
    private int userId;
    @Column
    private double qty;
    @Column
    private double amount;
    @OneToOne
    @JoinColumn(name="userId", insertable=false, updatable=false)
    private User user;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package mn.mxc.oss.domain;

import javax.persistence.*;
import java.util.List;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "planExecute",
                query = "update plan p set p.percent=floor((select sum(s.amount) from stockbalance s where s.qty<0 and s.createdDate between p.startDate and p.endDate)*100/p.amount) where p.status='active'",
                resultClass = Plan.class
        )
})
@Entity
public class Plan implements java.io.Serializable{
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
    private String planId;
    @Column
    private String name;
    @Column
    private String startDate;
    @Column
    private String endDate;
    @Column
    private String createdDate;
    @Column
    private int userId;
    @Column
    private String status;
    @Column
    private double amount;
    @Column
    private double percent;

    @OneToOne
    @JoinColumn(name="userId", insertable=false, updatable=false)
    private User user;

    @OneToMany(mappedBy="details", cascade = CascadeType.ALL)
    private List<PlanDetails> detailsList;

    @OneToMany(mappedBy="users", cascade = CascadeType.ALL)
    private List<PlanUsers> usersList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<PlanDetails> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<PlanDetails> detailsList) {
        this.detailsList = detailsList;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public List<PlanUsers> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<PlanUsers> usersList) {
        this.usersList = usersList;
    }
}

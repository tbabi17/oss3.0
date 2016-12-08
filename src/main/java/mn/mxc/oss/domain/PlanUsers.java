package mn.mxc.oss.domain;

import javax.persistence.*;

@Entity
public class PlanUsers {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column
    private String planId;
    @Column
    private int userId;

    @ManyToOne
    @JoinColumn(name="planId", referencedColumnName = "planId", insertable=false, updatable=false)
    private Plan users;

    @OneToOne
    @JoinColumn(name="userId", insertable=false, updatable=false)
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package mn.mxc.oss.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Route {
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
    private String routeName;

    @OneToMany(mappedBy="routeName", cascade = CascadeType.ALL)
    private List<CustomerOnly> customerList;

    public List<CustomerOnly> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<CustomerOnly> customerList) {
        this.customerList = customerList;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }
}

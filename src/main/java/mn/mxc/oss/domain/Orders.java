package mn.mxc.oss.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.JsonMappingException;
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Orders implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column
    private String orderId;
    @Column
    private int userId;
    @Column
    private int customerId;
    @Column
    private double qty;
    @Column
    private double amount;
    @Column
    private String createdDate;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    @Column

    private String confirmDate;
    @Column
    private String status;
    @Column
    private int warehouseId;
    @Column
    private float lat;
    @Column
    private float lng;
    @Column
    private String mode;

    @OneToMany(mappedBy="details", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties("details")
    private List<Details> detailsList = new ArrayList<Details>();

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="warehouseId", insertable=false, updatable=false)
    private Warehouse warehouse;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="customerId", insertable=false, updatable=false)
    private Customer customer;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="userId", insertable=false, updatable=false)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }
    //@JsonIgnore
    public List<Details> getDetailsList() {
        System.out.println(detailsList.toString());
        if(detailsList!=null){
            detailsList = detailsList;
        }else{
            detailsList = new ArrayList<Details>();
        }
        return detailsList;
    }

    public void setDetailsList(List<Details> detailsList) {
        this.detailsList = detailsList;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}

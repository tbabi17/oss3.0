package mn.mxc.oss.domain;

import javax.persistence.*;

/**
 * Created by i7 on 12/09/2016.
 */
@Entity
public class Gps {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Column(name="userId")
    private int UserId;
    @Column
    private Double lat;
    @Column
    private Double lng;
    @Column(name="createdDate")
    private String Date;
    @Column(name="customer_id")
    private int CustomerId;
    @Column
    private float Battery;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }
}

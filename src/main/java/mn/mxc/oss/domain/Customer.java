package mn.mxc.oss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column
    private String name;
    @Column
    private String phone;
    @Column
    private String address;
    @Column
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Column
    private int route;
    @Column
    private String createdDate;
    @Column
    private int userId;
    @Column
    private double lat;
    @Column
    private double lng;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @OneToOne
    @JoinColumn(name="price", insertable=false, updatable=false)
    private PriceTag priceTag;

    @OneToOne
    @JoinColumn(name="userId", insertable=false, updatable=false)
    private User user;

    @OneToOne
    @JoinColumn(name="route", insertable=false, updatable=false)
    private Route routeInfo;

    @ManyToOne
    @JoinColumn(name="route", insertable=false, updatable=false)
    private Route routes;

    public int getId() {
        return id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

//    public Route getRouteInfo() {
//        return routeInfo;
//    }
//
//    public void setRouteInfo(Route routeName) {
//        this.routeInfo = routeName;
//    }

    public PriceTag getPriceTag() {
        return priceTag;
    }

    public void setPriceTag(PriceTag priceTag) {
        this.priceTag = priceTag;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

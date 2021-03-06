package mn.mxc.oss.domain;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column
    private String owner;
    @Column
    private String password;
    @Column
    private String phone;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String status;
    @Column
    private String userType;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="mon", insertable=false, updatable=false)
    private RouteOnly monRoute;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="tue", insertable=false, updatable=false)
    private RouteOnly tueRoute;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="wed", insertable=false, updatable=false)
    private RouteOnly wedRoute;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="thu", insertable=false, updatable=false)
    private RouteOnly thuRoute;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="fri", insertable=false, updatable=false)
    private RouteOnly friRoute;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="sat", insertable=false, updatable=false)
    private RouteOnly satRoute;

    @Column
    private int mon;
    @Column
    private int tue;
    @Column
    private int wed;
    @Column
    private int thu;
    @Column
    private int fri;
    @Column
    private int sat;
    @Column
    private int sun;
    @Column
    private String createdDate;
    @Column
    private String user_image;

    public String getUser_image() {
        return user_image;
    }

    public RouteOnly getMonRoute() {
        return monRoute;
    }

    public RouteOnly getTueRoute() {
        return tueRoute;
    }

    public void setTueRoute(RouteOnly tueRoute) {
        this.tueRoute = tueRoute;
    }

    public RouteOnly getWedRoute() {
        return wedRoute;
    }

    public void setWedRoute(RouteOnly wedRoute) {
        this.wedRoute = wedRoute;
    }

    public RouteOnly getThuRoute() {
        return thuRoute;
    }

    public void setThuRoute(RouteOnly thuRoute) {
        this.thuRoute = thuRoute;
    }

    public RouteOnly getFriRoute() {
        return friRoute;
    }

    public void setFriRoute(RouteOnly friRoute) {
        this.friRoute = friRoute;
    }

    public RouteOnly getSatRoute() {
        return satRoute;
    }

    public void setSatRoute(RouteOnly satRoute) {
        this.satRoute = satRoute;
    }

    public void setMonRoute(RouteOnly monRoute) {
        this.monRoute = monRoute;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMon() {
        return mon;
    }

    public void setMon(int mon) {
        this.mon = mon;
    }

    public int getTue() {
        return tue;
    }

    public void setTue(int tue) {
        this.tue = tue;
    }

    public int getWed() {
        return wed;
    }

    public void setWed(int wed) {
        this.wed = wed;
    }

    public int getThu() {
        return thu;
    }

    public void setThu(int thu) {
        this.thu = thu;
    }

    public int getFri() {
        return fri;
    }

    public void setFri(int fri) {
        this.fri = fri;
    }

    public int getSat() {
        return sat;
    }

    public void setSat(int sat) {
        this.sat = sat;
    }

    public int getSun() {
        return sun;
    }

    public void setSun(int sun) {
        this.sun = sun;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}

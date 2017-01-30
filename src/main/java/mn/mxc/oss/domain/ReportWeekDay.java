package mn.mxc.oss.domain;

import javax.persistence.*;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "weekDay",
                //query = "INSERT INTO ReportWeekDay (userId,monMCount,monECount,monAmt,tueMCount,tueECount,tueAmt,wedMCount,wedECount,wedAmt,thuMCount,thuECount,thuAmt,friMCount,friECount,friAmt,satMCount,satECount,satAmt,sunMCount,sunECount,sunAmt,totalMCount,totalECount,totalAmt) select id,(select count(*) from customer where route=mon) as monMCount,(select count(*) from orders where mode='zarlaga' and weekDay(createdDate) = 0 and userId=p.id and customerId in (select customerId from customer where route=mon)) as monECount,(select ifnull(sum(amount),0) from orders where mode='zarlaga' and weekDay(createdDate) = 0 and userId=p.id) as monAmt,(select count(*) from customer where route=tue) as tueMCount,(select count(*) from orders where mode='zarlaga' and weekDay(createdDate) = 1 and userId=p.id and customerId in (select customerId from customer where route=tue)) as tueECount,(select ifnull(sum(amount),0) from orders where mode='zarlaga' and weekDay(createdDate) = 1 and userId=p.id) as tueAmt,(select count(*) from customer where route=wed) as wedMCount,(select count(*) from orders where mode='zarlaga' and weekDay(createdDate) = 2 and userId=p.id and customerId in (select customerId from customer where route=wed)) as wedECount,(select ifnull(sum(amount),0) from orders where mode='zarlaga' and weekDay(createdDate) = 2 and userId=p.id) as wedAmt,(select count(*) from customer where route=thu) as thuMCount,(select count(*) from orders where mode='zarlaga' and weekDay(createdDate) = 3 and userId=p.id and customerId in (select customerId from customer where route=thu)) as thuECount, (select ifnull(sum(amount),0) from orders where mode='zarlaga' and weekDay(createdDate) = 3 and userId=p.id) as thuAmt,(select count(*) from customer where route=fri) as friMCount,(select count(*) from orders where mode='zarlaga' and weekDay(createdDate) = 4 and userId=p.id and customerId in (select customerId from customer where route=fri)) as friECount, (select ifnull(sum(amount),0) from orders where mode='zarlaga' and weekDay(createdDate) = 4 and userId=p.id) as friAmt,(select count(*) from customer where route=sat) as satMCount,(select count(*) from orders where mode='zarlaga' and weekDay(createdDate) = 5 and userId=p.id and customerId in (select customerId from customer where route=sat)) as satECount,(select ifnull(sum(amount),0) from orders where mode='zarlaga' and weekDay(createdDate) = 5 and userId=p.id) as satAmt,(select count(*) from customer where route=sun) as sunMCount,(select count(*) from orders where mode='zarlaga' and weekDay(createdDate) = 6 and userId=p.id and customerId in (select customerId from customer where route=sun)) as sunECount,(select ifnull(sum(amount),0) from orders where mode='zarlaga' and weekDay(createdDate) = 6 and userId=p.id) as sunAmt,0,0,0 from user as p where userType=:userType"
                query = "INSERT INTO ReportWeekDay (userId,monMCount,monECount,monAmt,tueMCount,tueECount,tueAmt,wedMCount,wedECount,wedAmt,thuMCount,thuECount,thuAmt,friMCount,friECount,friAmt,satMCount,satECount,satAmt,sunMCount,sunECount,sunAmt,totalMCount,totalECount,totalAmt) select id,(select count(*) from customer where route=mon) as monMCount,(select count(*) from orders where mode='zarlaga' and weekDay(createdDate) = 0 and createdDate between :startDate and :endDate and userId=p.id and customerId in (select customerId from customer where route=mon)) as monECount,(select ifnull(sum(amount),0) from orders where mode='zarlaga' and weekDay(createdDate) = 0 and userId=p.id) as monAmt,(select count(*) from customer where route=tue) as tueMCount,(select count(*) from orders where mode='zarlaga' and weekDay(createdDate) = 1 and createdDate between :startDate and :endDate and userId=p.id and customerId in (select customerId from customer where route=tue)) as tueECount,(select ifnull(sum(amount),0) from orders where mode='zarlaga' and weekDay(createdDate) = 1 and userId=p.id and createdDate between :startDate and :endDate) as tueAmt,(select count(*) from customer where route=wed) as wedMCount,(select count(*) from orders where mode='zarlaga' and weekDay(createdDate) = 2 and createdDate between :startDate and :endDate and userId=p.id and customerId in (select customerId from customer where route=wed)) as wedECount,(select ifnull(sum(amount),0) from orders where mode='zarlaga' and weekDay(createdDate) = 2 and userId=p.id and createdDate between :startDate and :endDate) as wedAmt,(select count(*) from customer where route=thu) as thuMCount,(select count(*) from orders where mode='zarlaga' and weekDay(createdDate) = 3 and createdDate between :startDate and :endDate and userId=p.id and customerId in (select customerId from customer where route=thu)) as thuECount, (select ifnull(sum(amount),0) from orders where mode='zarlaga' and weekDay(createdDate) = 3 and userId=p.id and createdDate between :startDate and :endDate) as thuAmt,(select count(*) from customer where route=fri) as friMCount,(select count(*) from orders where mode='zarlaga' and weekDay(createdDate) = 4 and createdDate between :startDate and :endDate and userId=p.id and customerId in (select customerId from customer where route=fri)) as friECount, (select ifnull(sum(amount),0) from orders where mode='zarlaga' and weekDay(createdDate) = 4 and userId=p.id and createdDate between :startDate and :endDate) as friAmt,(select count(*) from customer where route=sat) as satMCount,(select count(*) from orders where mode='zarlaga' and weekDay(createdDate) = 5 and createdDate between :startDate and :endDate and userId=p.id and customerId in (select customerId from customer where route=sat)) as satECount,(select ifnull(sum(amount),0) from orders where mode='zarlaga' and weekDay(createdDate) = 5 and userId=p.id and createdDate between :startDate and :endDate) as satAmt,(select count(*) from customer where route=sun) as sunMCount,(select count(*) from orders where mode='zarlaga' and weekDay(createdDate) = 6 and createdDate between :startDate and :endDate and userId=p.id and customerId in (select customerId from customer where route=sun)) as sunECount,(select ifnull(sum(amount),0) from orders where mode='zarlaga' and weekDay(createdDate) = 6 and userId=p.id and createdDate between :startDate and :endDate) as sunAmt,0,0,0 from user as p where userType=:userType"
        )
})
@Table(name="ReportWeekDay")
@Entity
public class ReportWeekDay {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int userId;
    @Column
    private int monMCount;
    @Column
    private int monECount;
    @Column
    private double monAmt;

    @Column
    private int tueMCount;
    @Column
    private int tueECount;
    @Column
    private double tueAmt;

    @Column
    private int wedMCount;
    @Column
    private int wedECount;
    @Column
    private double wedAmt;

    @Column
    private int thuMCount;
    @Column
    private int thuECount;
    @Column
    private double thuAmt;

    @Column
    private int friMCount;
    @Column
    private int friECount;
    @Column
    private double friAmt;

    @Column
    private int satMCount;
    @Column
    private int satECount;
    @Column
    private double satAmt;

    @Column
    private int sunMCount;
    @Column
    private int sunECount;
    @Column
    private double sunAmt;

    @Column
    private int totalMCount;
    @Column
    private int totalECount;
    @Column
    private double totalAmt;

    @OneToOne
    @JoinColumn(name="userId", insertable=false, updatable=false)
    private User user;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMonMCount() {
        return monMCount;
    }

    public void setMonMCount(int monMCount) {
        this.monMCount = monMCount;
    }

    public int getMonECount() {
        return monECount;
    }

    public void setMonECount(int monECount) {
        this.monECount = monECount;
    }

    public double getMonAmt() {
        return monAmt;
    }

    public void setMonAmt(double monAmt) {
        this.monAmt = monAmt;
    }

    public int getTueMCount() {
        return tueMCount;
    }

    public void setTueMCount(int tueMCount) {
        this.tueMCount = tueMCount;
    }

    public int getTueECount() {
        return tueECount;
    }

    public void setTueECount(int tueECount) {
        this.tueECount = tueECount;
    }

    public double getTueAmt() {
        return tueAmt;
    }

    public void setTueAmt(double tueAmt) {
        this.tueAmt = tueAmt;
    }

    public int getWedMCount() {
        return wedMCount;
    }

    public void setWedMCount(int wedMCount) {
        this.wedMCount = wedMCount;
    }

    public int getWedECount() {
        return wedECount;
    }

    public void setWedECount(int wedECount) {
        this.wedECount = wedECount;
    }

    public double getWedAmt() {
        return wedAmt;
    }

    public void setWedAmt(double wedAmt) {
        this.wedAmt = wedAmt;
    }

    public int getThuMCount() {
        return thuMCount;
    }

    public void setThuMCount(int thuMCount) {
        this.thuMCount = thuMCount;
    }

    public int getThuECount() {
        return thuECount;
    }

    public void setThuECount(int thuECount) {
        this.thuECount = thuECount;
    }

    public double getThuAmt() {
        return thuAmt;
    }

    public void setThuAmt(double thuAmt) {
        this.thuAmt = thuAmt;
    }

    public int getFriMCount() {
        return friMCount;
    }

    public void setFriMCount(int friMCount) {
        this.friMCount = friMCount;
    }

    public int getFriECount() {
        return friECount;
    }

    public void setFriECount(int friECount) {
        this.friECount = friECount;
    }

    public double getFriAmt() {
        return friAmt;
    }

    public void setFriAmt(double friAmt) {
        this.friAmt = friAmt;
    }

    public int getSatMCount() {
        return satMCount;
    }

    public void setSatMCount(int satMCount) {
        this.satMCount = satMCount;
    }

    public int getSatECount() {
        return satECount;
    }

    public void setSatECount(int satECount) {
        this.satECount = satECount;
    }

    public double getSatAmt() {
        return satAmt;
    }

    public void setSatAmt(double satAmt) {
        this.satAmt = satAmt;
    }

    public int getSunMCount() {
        return sunMCount;
    }

    public void setSunMCount(int sunMCount) {
        this.sunMCount = sunMCount;
    }

    public int getSunECount() {
        return sunECount;
    }

    public void setSunECount(int sunECount) {
        this.sunECount = sunECount;
    }

    public double getSunAmt() {
        return sunAmt;
    }

    public void setSunAmt(double sunAmt) {
        this.sunAmt = sunAmt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTotalMCount() {
        return totalMCount;
    }

    public void setTotalMCount(int totalMCount) {
        this.totalMCount = totalMCount;
    }

    public int getTotalECount() {
        return totalECount;
    }

    public void setTotalECount(int totalECount) {
        this.totalECount = totalECount;
    }

    public double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(double totalAmt) {
        this.totalAmt = totalAmt;
    }
}

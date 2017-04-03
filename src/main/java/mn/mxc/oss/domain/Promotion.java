package mn.mxc.oss.domain;

import javax.persistence.*;
import java.util.List;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "promotionExecute",
                query = "update promotion p set p.usage=0 where p.status='active'",
                resultClass = Promotion.class
        )
})
@Entity
public class Promotion implements java.io.Serializable{
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
    private String promotionId;
    @Column
    private String name;
    @Column(name="promo_type")
    private String promoType;
    @Column(name="promo_brand")
    private String promoBrand;
    @Column(name="promo_discount")
    private double promoDiscount;
    @Column(name="total_qty")
    private double totalQty;
    @Column(name="total_amount")
    private double totalAmount;
    @Column(name="max_amount")
    private double maxAmount;

    public String getPromoType() {
        return promoType;
    }

    public void setPromoType(String promoType) {
        this.promoType = promoType;
    }

    public String getPromoBrand() {
        return promoBrand;
    }

    public void setPromoBrand(String promoBrand) {
        this.promoBrand = promoBrand;
    }

    public double getPromoDiscount() {
        return promoDiscount;
    }

    public void setPromoDiscount(double promoDiscount) {
        this.promoDiscount = promoDiscount;
    }

    public double getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(double totalQty) {
        this.totalQty = totalQty;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

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
    private int used;
    @OneToMany(mappedBy="details", cascade = CascadeType.ALL)
    private List<PromotionDetails> detailsList;

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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


    public List<PromotionDetails> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<PromotionDetails> detailsList) {
        this.detailsList = detailsList;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }
}

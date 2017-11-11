package lk.edu.ijse.ThogaKadeHibernate.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by oshan on 30-Oct-17.
 */
@Entity
public class Item extends SuperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemCode;
    private String description;
    private int qtyOnHand;
    private double unitPrice;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetailList;

    public Item() {
    }

    public Item(String description, int qtyOnHand, double unitPrice) {
        this.description = description;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
    }

    public Item(int itemCode, String description, int qtyOnHand, double unitPrice, List<OrderDetail> orderDetailList) {
        this.itemCode=itemCode;
        this.description = description;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
        this.orderDetailList = orderDetailList;
    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    @Override
    public String toString() {
        return "ItemDAO{" +
                "itemCode=" + itemCode +
                ", description='" + description + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", unitPrice=" + unitPrice +
                ", orderDetailList=" + orderDetailList +
                '}';
    }
}

package lk.edu.ijse.ThogaKadeHibernate.dto;

import java.util.List;

/**
 * Created by Owner on 11/4/2017.
 */
public class ItemDTO {
    private int itemCode;
    private String description;
    private int qtyOnHand;
    private double unitPrice;
    private List<OrderDetailDTO> orderDetailList;

    public ItemDTO() {
    }

    public ItemDTO(String description, int qtyOnHand, double unitPrice) {
        this.description = description;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
    }

    public ItemDTO(int itemCode, String description, int qtyOnHand, double unitPrice, List<OrderDetailDTO> orderDetailList) {
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

    public List<OrderDetailDTO> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetailDTO> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

}

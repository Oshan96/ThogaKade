package lk.edu.ijse.ThogaKadeHibernate.dto;

import lk.edu.ijse.ThogaKadeHibernate.entity.OrderDetail_PK;

/**
 * Created by Owner on 11/4/2017.
 */
public class OrderDetailDTO {
    private OrderDetail_PK orderDetail_pk;
    private int qty;
    private double price;
    private ItemDTO item;
    private OrdersDTO orders;

    public OrderDetailDTO(){

    }

    public OrderDetailDTO(int itemCode, int oid, int qty, double price) {
        this.orderDetail_pk=new OrderDetail_PK(itemCode,oid);
        this.qty = qty;
        this.price = price;
    }

    public OrderDetailDTO(int qty, double price, ItemDTO item, OrdersDTO orders) {
        this.orderDetail_pk = new OrderDetail_PK(item.getItemCode(),orders.getOid());
        this.qty = qty;
        this.price = price;
        this.item = item;
        this.orders = orders;
    }

    public OrderDetailDTO(OrderDetail_PK orderDetail_pk, int qty, double price, ItemDTO item, OrdersDTO orders) {
        this.orderDetail_pk = orderDetail_pk;
        this.qty = qty;
        this.price = price;
        this.item = item;
        this.orders = orders;
    }

    public OrderDetail_PK getOrderDetail_pk() {
        return orderDetail_pk;
    }

    public void setOrderDetail_pk(OrderDetail_PK orderDetail_pk) {
        this.orderDetail_pk = orderDetail_pk;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    public OrdersDTO getOrders() {
        return orders;
    }

    public void setOrders(OrdersDTO orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDetailDTO that = (OrderDetailDTO) o;

        return orderDetail_pk.equals(that.orderDetail_pk);
    }

    @Override
    public int hashCode() {
        return orderDetail_pk.hashCode();
    }
}

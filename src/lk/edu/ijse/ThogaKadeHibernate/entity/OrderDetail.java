package lk.edu.ijse.ThogaKadeHibernate.entity;

import javax.persistence.*;

/**
 * Created by oshan on 30-Oct-17.
 */
@Entity
public class OrderDetail extends SuperEntity {
    @EmbeddedId
    private OrderDetail_PK orderDetail_pk;
    private int qty;
    private double price;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns(@JoinColumn(name = "itemCode", referencedColumnName = "itemCode", insertable = false, updatable = false))
    private Item item;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns(@JoinColumn(name = "oid", referencedColumnName = "oid", insertable = false, updatable = false))
    private Orders orders;

    public OrderDetail(){

    }

    public OrderDetail(int itemCode, int oid, int qty, double price) {
        this.orderDetail_pk=new OrderDetail_PK(itemCode,oid);
        this.qty = qty;
        this.price = price;
    }

    public OrderDetail(int qty, double price, Item item, Orders orders) {
        this.orderDetail_pk = new OrderDetail_PK(item.getItemCode(),orders.getOid());
        this.qty = qty;
        this.price = price;
        this.item = item;
        this.orders = orders;
    }

    public OrderDetail(OrderDetail_PK orderDetail_pk, int qty, double price, Item item, Orders orders) {
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDetail that = (OrderDetail) o;

        return (orderDetail_pk.getItemCode()==that.orderDetail_pk.getItemCode() &&
                orderDetail_pk.getOid()==that.orderDetail_pk.getOid());
    }


    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetail_pk=" + orderDetail_pk +
                ", qty=" + qty +
                ", price=" + price +
                ", item=" + item +
                ", orders=" + orders +
                '}';
    }
}

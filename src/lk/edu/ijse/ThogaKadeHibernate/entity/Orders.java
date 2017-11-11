package lk.edu.ijse.ThogaKadeHibernate.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by oshan on 30-Oct-17.
 */
@Entity
public class Orders extends SuperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oid;
    private String oDate;
    private double total;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetailList=new ArrayList<>();
    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;

    public Orders() {
    }

    public Orders(String oDate, double total, List<OrderDetail> orderDetailList, Customer customer) {
        this.oDate = oDate;
        this.total = total;
        this.orderDetailList = orderDetailList;
        this.customer = customer;
    }

    public Orders(int oid, String oDate, double total, List<OrderDetail> orderDetailList, Customer customer) {
        this.oid=oid;
        this.oDate = oDate;
        this.total = total;
        this.orderDetailList = orderDetailList;
        this.customer = customer;
    }

    public void addOrderDetail(OrderDetail orderDetail){
        orderDetailList.add(orderDetail);
        orderDetail.setOrders(this);
    }

    public void removeOrderDetail(OrderDetail orderDetail){
        orderDetailList.remove(orderDetail);
        orderDetail.setOrders(null);
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getoDate() {
        return oDate;
    }

    public void setoDate(String oDate) {
        this.oDate = oDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "oid=" + oid +
                ", oDate='" + oDate + '\'' +
                ", total=" + total +
                ", orderDetailList=" + orderDetailList +
                ", customer=" + customer +
                '}';
    }
}

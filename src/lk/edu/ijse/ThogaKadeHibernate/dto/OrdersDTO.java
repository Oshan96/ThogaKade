package lk.edu.ijse.ThogaKadeHibernate.dto;

import java.util.List;

/**
 * Created by Owner on 11/4/2017.
 */
public class OrdersDTO {
    private int oid;
    private String oDate;
    private double total;
    private List<OrderDetailDTO> orderDetailList;
    private CustomerDTO customer;

    public OrdersDTO() {
    }

    public OrdersDTO(String oDate, double total, List<OrderDetailDTO> orderDetailList, CustomerDTO customer) {
        this.oDate = oDate;
        this.total = total;
        this.orderDetailList = orderDetailList;
        this.customer = customer;
    }

    public OrdersDTO(int oid, String oDate, double total, List<OrderDetailDTO> orderDetailList, CustomerDTO customer) {
        this.oid=oid;
        this.oDate = oDate;
        this.total = total;
        this.orderDetailList = orderDetailList;
        this.customer = customer;
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

    public List<OrderDetailDTO> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetailDTO> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }
}

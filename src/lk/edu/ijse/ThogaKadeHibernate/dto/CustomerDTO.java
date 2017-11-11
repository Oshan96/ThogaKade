package lk.edu.ijse.ThogaKadeHibernate.dto;

import org.hibernate.criterion.Order;

import java.util.List;

/**
 * Created by Owner on 11/4/2017.
 */
public class CustomerDTO {
    private int cid;
    private String name;
    private String address;
    private String contact;
    private String nic;
    List<OrdersDTO> ordersList;

    public CustomerDTO() {
    }

    public CustomerDTO(String name, String address, String contact, String nic) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.nic = nic;
    }

    public CustomerDTO(int cid, String name, String address, String contact, String nic, List<OrdersDTO> ordersList) {
        this.cid=cid;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.nic = nic;
        this.ordersList = ordersList;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public List<OrdersDTO> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<OrdersDTO> ordersList) {
        this.ordersList = ordersList;
    }
}

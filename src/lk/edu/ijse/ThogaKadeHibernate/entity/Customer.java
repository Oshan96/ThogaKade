package lk.edu.ijse.ThogaKadeHibernate.entity;


import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;

/**
 * Created by oshan on 01-Nov-17.
 */
@Entity
public class Customer extends SuperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cid;
    private String name;
    private String address;
    private String contact;
    private String nic;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "customer")
    private List<Orders> ordersList;

    public Customer() {
    }

    public Customer(String name, String address, String contact, String nic) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.nic = nic;
    }

    public Customer(int cid, String name, String address, String contact, String nic) {
        this.cid=cid;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.nic = nic;
    }

    public Customer(int cid, String name, String address, String contact, String nic, List<Orders> ordersList) {
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

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cid=" + cid +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", nic='" + nic + '\'' +
                ", ordersList=" + ordersList +
                '}';
    }
}

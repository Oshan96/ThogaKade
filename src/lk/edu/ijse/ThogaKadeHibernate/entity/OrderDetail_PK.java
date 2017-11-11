package lk.edu.ijse.ThogaKadeHibernate.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by oshan on 01-Nov-17.
 */
@Embeddable
public class OrderDetail_PK implements Serializable {
    private int itemCode;
    private int oid;

    public OrderDetail_PK() {
    }

    public OrderDetail_PK(int itemCode, int oid) {
        this.itemCode = itemCode;
        this.oid = oid;
    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDetail_PK that = (OrderDetail_PK) o;

        if (itemCode != that.itemCode) return false;
        return oid == that.oid;
    }

    @Override
    public int hashCode() {
        int result = itemCode;
        result = 31 * result + oid;
        return result;
    }
}

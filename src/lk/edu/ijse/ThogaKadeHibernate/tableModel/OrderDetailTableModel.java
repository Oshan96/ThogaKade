package lk.edu.ijse.ThogaKadeHibernate.tableModel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by oshan on 27-Oct-17.
 */
public class OrderDetailTableModel {
    private SimpleIntegerProperty itemCode=new SimpleIntegerProperty(0);
    private SimpleStringProperty description=new SimpleStringProperty("");
    private SimpleDoubleProperty unitPrice=new SimpleDoubleProperty(0.0);
    private SimpleIntegerProperty qty=new SimpleIntegerProperty(0);
    private SimpleDoubleProperty subTotal=new SimpleDoubleProperty(0.0);

    public OrderDetailTableModel() {
    }

    public OrderDetailTableModel(int itemCode, String description, double unitPrice, int qty, double subTotal) {
        this.itemCode.set(itemCode);
        this.description.set(description);
        this.unitPrice.set(unitPrice);
        this.qty.set(qty);
        this.subTotal.set(subTotal);
    }

    public int getItemCode() {
        return itemCode.get();
    }

    public SimpleIntegerProperty itemCodeProperty() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode.set(itemCode);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public double getUnitPrice() {
        return unitPrice.get();
    }

    public SimpleDoubleProperty unitPriceProperty() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice.set(unitPrice);
    }

    public int getQty() {
        return qty.get();
    }

    public SimpleIntegerProperty qtyProperty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty.set(qty);
    }

    public double getSubTotal() {
        return subTotal.get();
    }

    public SimpleDoubleProperty subTotalProperty() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal.set(subTotal);
    }
}

package lk.edu.ijse.ThogaKadeHibernate.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.edu.ijse.ThogaKadeHibernate.controller.CashierController;
import lk.edu.ijse.ThogaKadeHibernate.controller.factory.ControllerFactory;
import lk.edu.ijse.ThogaKadeHibernate.controller.factory.ControllerType;
import lk.edu.ijse.ThogaKadeHibernate.dto.CustomerDTO;
import lk.edu.ijse.ThogaKadeHibernate.dto.ItemDTO;
import lk.edu.ijse.ThogaKadeHibernate.dto.OrderDetailDTO;
import lk.edu.ijse.ThogaKadeHibernate.dto.OrdersDTO;
import lk.edu.ijse.ThogaKadeHibernate.entity.OrderDetail_PK;
import lk.edu.ijse.ThogaKadeHibernate.tableModel.OrderDetailTableModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by oshan on 26-Oct-17.
 */
public class ManageOrders implements Initializable{
    @FXML
    private ComboBox<String> cmbCusName;
    @FXML
    private ComboBox<String> cmbItemDesc;
    @FXML
    private ComboBox<String> cmbPriority;
    @FXML
    private ComboBox<Integer> cmbOId;
    @FXML
    private TextField txtODate;
    @FXML
    private Button btnAdd;
    @FXML
    private TableView<OrderDetailTableModel> tblView;
    @FXML
    private TableColumn colItemCode;
    @FXML
    private TableColumn colDesc;
    @FXML
    private TableColumn colUnitPrice;
    @FXML
    private TableColumn colQty;
    @FXML
    private TableColumn colSubTotal;
    @FXML
    private TextField txtUnitPrice;
    @FXML
    private TextField txtQtyOnHand;
    @FXML
    private TextField txtQty;
    @FXML
    private TextField txtTotal;

    private int currentQty;

    private List<CustomerDTO> customerDTOList;
    private List<ItemDTO> itemDTOList;
    private List<OrdersDTO> ordersDTOList=new ArrayList<>();


    private CashierController controller;

    private double total;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<OrderDetailTableModel,String>("itemCode"));
        colDesc.setCellValueFactory(new PropertyValueFactory<OrderDetailTableModel,String>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<OrderDetailTableModel,Double>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<OrderDetailTableModel,Integer>("qty"));
        colSubTotal.setCellValueFactory(new PropertyValueFactory<OrderDetailTableModel,Double>("subTotal"));

        controller= (CashierController) ControllerFactory.getInstance().getController(ControllerType.CASHIER);
        cmbPriority.getItems().addAll("Low","Medium","High");

        loadCustomers();
        loadItems();
    }

    @FXML
    private void intRestriction(KeyEvent key){
        if(!"0123456789".contains(key.getCharacter())&&(key.getCode()!= KeyCode.BACK_SPACE)){
            key.consume();
        }
    }

    @FXML
    private void itemSelection(ActionEvent evt){

        //Set details here
        String desc=cmbItemDesc.getValue();
        setCurrentQty(desc);
        int index=cmbItemDesc.getItems().indexOf(desc);
        ItemDTO itemDTO=null;
        try{
            itemDTO=itemDTOList.get(index);
        }catch (ArrayIndexOutOfBoundsException ex){
            return;
        }

        txtUnitPrice.setText(Double.toString(itemDTO.getUnitPrice()));
        txtQtyOnHand.setText(Integer.toString(itemDTO.getQtyOnHand()-currentQty));

    }

    @FXML
    private void addItemAction(ActionEvent evt){
        int index=cmbItemDesc.getItems().indexOf(cmbItemDesc.getValue());
        ItemDTO itemDTO=itemDTOList.get(index);


        double unitPrice=Double.parseDouble(txtUnitPrice.getText());
        int qty=0;
        try{
            qty=Integer.parseInt(txtQty.getText());
        }catch(NumberFormatException  | NullPointerException ex){
            return;
        }


        if(qty>Integer.parseInt(txtQtyOnHand.getText())){
            txtQty.requestFocus();
            return;
        }

        try {
            int isAddedItem=checkItem(cmbItemDesc.getValue());
            if(isAddedItem>-1){
                int aQty = tblView.getItems().get(isAddedItem).getQty()+qty;
                tblView.getItems().get(isAddedItem).setQty(aQty);
                tblView.getItems().get(isAddedItem).setSubTotal(unitPrice*aQty);

            }else {
                OrderDetailTableModel orderDetail = new OrderDetailTableModel(
                        itemDTO.getItemCode(), cmbItemDesc.getValue(), unitPrice, qty, unitPrice * qty
                );
                tblView.getItems().add(orderDetail);
            }
            OrdersDTO dto = ordersDTOList.get(cmbOId.getItems().indexOf(cmbOId.getValue()));
            dto.getOrderDetailList().add(
              new OrderDetailDTO(
                      new OrderDetail_PK(itemDTO.getItemCode(),dto.getOid()),qty,unitPrice * qty,itemDTO,dto
              )
            );
            tblView.refresh();
        }catch (NullPointerException ex){
//            ex.getCause().
            ex.printStackTrace();
        }

        calculateTotal();
        setCurrentQty(cmbItemDesc.getValue());
//        clearFields();
        setTotal();

    }

    @FXML
    private void removeAction(ActionEvent evt){
        OrderDetailTableModel row = tblView.getSelectionModel().getSelectedItem();
        OrdersDTO order=ordersDTOList.get(cmbOId.getItems().indexOf(cmbOId.getValue()));
        OrderDetailDTO orderDetailDTO=new OrderDetailDTO(
                new OrderDetail_PK(row.getItemCode(),cmbOId.getValue()),
                row.getQty(),
                row.getSubTotal(),
                null,
                null
        );
        for(OrderDetailDTO od:order.getOrderDetailList()){
            if(od.equals(orderDetailDTO)){
                od.setOrders(null);
            }
        }
        total-=row.getSubTotal();
        tblView.getItems().remove(row);
        tblView.refresh();

    }

    @FXML
    private void customerSelection(ActionEvent evt){
        int index=cmbCusName.getItems().indexOf(cmbCusName.getValue());
        CustomerDTO customerDTO=customerDTOList.get(index);
        ordersDTOList=customerDTO.getOrdersList();
        loadOrderIds();
    }

    @FXML
    private void orderSelection(ActionEvent evt){
        tblView.getItems().clear();
        tblView.refresh();
        loadDataToTable(cmbOId.getItems().indexOf(cmbOId.getValue()));
        setTotal();
    }

    @FXML
    private void modifyOrder(ActionEvent evt){
        boolean val=controller.modifyOrder(ordersDTOList.get(cmbOId.getItems().indexOf(cmbOId.getValue())));
        if(val){
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Manage Order");
            alert.setHeaderText("Manage Order");
            alert.setContentText("Order modified successfully");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.getDialogPane().getStylesheets().add(getClass().getResource("../util/css/thogaKade.css").toExternalForm());
            alert.showAndWait();
        }else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Manage Order");
            alert.setHeaderText("Manage Order");
            alert.setContentText("Error Occurred while modifying order");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.getDialogPane().getStylesheets().add(getClass().getResource("../util/css/thogaKade.css").toExternalForm());
            alert.showAndWait();
        }
    }


    private void loadDataToTable(int index){
        OrdersDTO ordersDTO=ordersDTOList.get(index);
        for(OrderDetailDTO orderDetailDTO : ordersDTO.getOrderDetailList()){
            tblView.getItems().add(new OrderDetailTableModel(
                    orderDetailDTO.getOrderDetail_pk().getItemCode(),
                    orderDetailDTO.getItem().getDescription(),
                    orderDetailDTO.getItem().getUnitPrice(),
                    orderDetailDTO.getQty(),
                    orderDetailDTO.getPrice()
            ));
        }
        txtODate.setText(ordersDTO.getoDate());
        tblView.refresh();
    }

    private void loadOrderIds(){
        cmbOId.getItems().clear();
        for(OrdersDTO ordersDTO : ordersDTOList){
            cmbOId.getItems().add(ordersDTO.getOid());
        }
    }

    private void calculateTotal(){
        total+=(Double.parseDouble(txtUnitPrice.getText())*Double.parseDouble(txtQty.getText()));
    }

    private int checkItem(String desc){
        int index=-1;
        for(OrderDetailTableModel d:tblView.getItems()){

            if(desc.equals(d.getDescription())){

                index = tblView.getItems().indexOf(d);
            }
        }
        return index;
    }

    public void loadCustomers(){
        cmbCusName.getItems().clear();
        customerDTOList=controller.getAllCustomers();
        for(CustomerDTO customerDTO:customerDTOList){
            cmbCusName.getItems().add(customerDTO.getName());
        }
    }

    private void loadItems(){
        cmbItemDesc.getItems().clear();
        itemDTOList=controller.getAllItems();
        for(ItemDTO itemDTO:itemDTOList){
            cmbItemDesc.getItems().add(itemDTO.getDescription());
        }
    }

    private void setCurrentQty(String desc){
        for(OrderDetailTableModel d:tblView.getItems()){
            if(desc.equals(d.getDescription())){
                currentQty=d.getQty();
            }
        }
    }

    private void setTotal(){
        double total=0;
        for(OrderDetailTableModel od: tblView.getItems()){
            total+=od.getSubTotal();
        }
        txtTotal.setText(""+total);
    }

}

//For removed orderDetails - > make order = null and pass the Order w/o removing orderDetail
// Then, at service remove those orderDetails with order=null from it's list

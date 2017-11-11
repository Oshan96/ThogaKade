package lk.edu.ijse.ThogaKadeHibernate.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.edu.ijse.ThogaKadeHibernate.controller.CashierController;
import lk.edu.ijse.ThogaKadeHibernate.controller.factory.ControllerFactory;
import lk.edu.ijse.ThogaKadeHibernate.controller.factory.ControllerType;
import lk.edu.ijse.ThogaKadeHibernate.dto.CustomerDTO;
import lk.edu.ijse.ThogaKadeHibernate.dto.ItemDTO;
import lk.edu.ijse.ThogaKadeHibernate.dto.OrderDetailDTO;
import lk.edu.ijse.ThogaKadeHibernate.dto.OrdersDTO;
import lk.edu.ijse.ThogaKadeHibernate.entity.OrderDetail;
import lk.edu.ijse.ThogaKadeHibernate.tableModel.OrderDetailTableModel;
import org.hibernate.criterion.Order;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by oshan on 26-Oct-17.
 */
public class PlaceOrder implements Initializable {

    @FXML
    private ComboBox<String> cmbCusName;
    @FXML
    private ComboBox<String> cmbItemDesc;
    @FXML
    private ComboBox<String> cmbPriority;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnPlaceOrder;
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
    private TextField txtCusContact;
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

    private CashierController controller;

    private double total;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller= (CashierController) ControllerFactory.getInstance().getController(ControllerType.CASHIER);
        cmbPriority.getItems().addAll("Low","Medium","High");

        //loadCustomers;
        loadCustomers();
        //loadItems
        loadItems();


        colItemCode.setCellValueFactory(new PropertyValueFactory<OrderDetailTableModel,String>("itemCode"));
        colDesc.setCellValueFactory(new PropertyValueFactory<OrderDetailTableModel,String>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<OrderDetailTableModel,Double>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<OrderDetailTableModel,Integer>("qty"));
        colSubTotal.setCellValueFactory(new PropertyValueFactory<OrderDetailTableModel,Double>("subTotal"));

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

    @FXML
    private void intRestriction(KeyEvent key){
        if(!"0123456789".contains(key.getCharacter())&&(key.getCode()!= KeyCode.BACK_SPACE)){
            key.consume();
        }
    }

    @FXML
    private void removeAction(ActionEvent evt){
        OrderDetailTableModel row = tblView.getSelectionModel().getSelectedItem();
        total-=row.getSubTotal();
        tblView.getItems().remove(row);
        tblView.refresh();

    }

    @FXML
    private void customerSelection(ActionEvent evt){
        int index=cmbCusName.getItems().indexOf(cmbCusName.getValue());
        txtCusContact.setText(customerDTOList.get(index).getContact());
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

    private void setTotal(){
        double total=0;
        for(OrderDetailTableModel od: tblView.getItems()){
            total+=od.getSubTotal();
        }
        txtTotal.setText(""+total);
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

    private void calculateTotal(){
        total+=(Double.parseDouble(txtUnitPrice.getText())*Double.parseDouble(txtQty.getText()));
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

    private void setCurrentQty(String desc){
        for(OrderDetailTableModel d:tblView.getItems()){
            if(desc.equals(d.getDescription())){
                currentQty=d.getQty();
            }
        }
    }


    @FXML
    private void placeOrder(){
        CustomerDTO customer=customerDTOList.get(cmbCusName.getItems().indexOf(cmbCusName.getValue()));
        OrdersDTO order=new OrdersDTO(
                LocalDate.now().toString(),
                total,
                null,
                customer
        );
        List<OrderDetailDTO> odList=new ArrayList<>();
        for(OrderDetailTableModel row : tblView.getItems()){
            ItemDTO item=itemDTOList.get(cmbItemDesc.getItems().indexOf(row.getDescription()));
            OrderDetailDTO orderDetailDTO=new OrderDetailDTO(
                    row.getQty(),
                    row.getQty()*row.getUnitPrice(),
                    item,
                    order
            );
            odList.add(orderDetailDTO);
        }

        order.setOrderDetailList(odList);  //For changes


        boolean isAdded=controller.placeNewOrder(order, odList);
        if(isAdded){
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Place New Order");
            alert.setHeaderText("Placing Order");
            alert.setContentText("Successfully placed the order!");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.getDialogPane().getStylesheets().add(getClass().getResource("../util/css/thogaKade.css").toExternalForm());
            alert.showAndWait();
        }else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Place New Order");
            alert.setHeaderText("Placing Order");
            alert.setContentText("Error Occurred while placing order");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.getDialogPane().getStylesheets().add(getClass().getResource("../util/css/thogaKade.css").toExternalForm());
            alert.showAndWait();
        }

        tblView.getItems().clear();
        tblView.refresh();
        loadItems();
    }

    @FXML
    private void addCustomerAction(ActionEvent evt){

        final double[] xOffSet = {0.0};
        final double[] yOffset = {0.0};

        Stage customerStage=new Stage();

        VBox box=new VBox();
        box.setSpacing(20);

        Scene scene=new Scene(box,400,350);

        scene.getStylesheets().add(getClass().getResource("../util/css/thogaKade.css").toExternalForm());
        scene.setFill(Color.TRANSPARENT);

        box.setId("rootBox");

        box.setAlignment(Pos.TOP_CENTER);
        box.setMaxWidth(600);
        HBox title=new HBox();

        title.setAlignment(Pos.CENTER);
        Label lblTitle=new Label("Add New Customer");
        lblTitle.setPrefWidth(370);
        lblTitle.getStyleClass().add("header-tag");

        lblTitle.setAlignment(Pos.CENTER);
        title.getChildren().add(lblTitle);
        box.getChildren().add(title);

        HBox details=new HBox();
        //Labels
        Label lblName=new Label("Customer Name ");
        lblName.getStyleClass().add("naming-tags");
        Label lblAddress=new Label("Address ");
        lblAddress.getStyleClass().add("naming-tags");
        Label lblContact=new Label("Contact No. ");
        lblContact.getStyleClass().add("naming-tags");
        Label lblNIC=new Label("NIC No. ");
        lblNIC.getStyleClass().add("naming-tags");
        VBox boxlbl=new VBox();


        boxlbl.getChildren().addAll(lblName,lblAddress,lblContact,lblNIC);
        details.getChildren().add(boxlbl);

        //TextFields
        TextField txtName=new TextField();
        TextField txtAddress=new TextField();
        TextField txtContact=new TextField();
        TextField txtNIC=new TextField();

        VBox boxTxt=new VBox();
        boxTxt.setPrefWidth(230);
        boxTxt.setSpacing(20);
        boxTxt.getChildren().addAll(txtName,txtAddress,txtContact,txtNIC);

        details.getChildren().add(boxTxt);


        box.getChildren().add(details);

        //Buttons
        HBox boxBtn=new HBox();
        boxBtn.setAlignment(Pos.CENTER_RIGHT);
        boxBtn.setSpacing(10);
        boxBtn.setPadding(new Insets(0,27,0,0));
        Button btnAdd=new Button("Add Customer");
        btnAdd.getStyleClass().add("add-btn");
        Button btnCancel=new Button("Cancel");
        btnCancel.getStyleClass().add("cancel-btn");

        boxBtn.getChildren().addAll(btnAdd,btnCancel);

        box.getChildren().add(boxBtn);

        customerStage.setScene(scene);

        customerStage.initStyle(StageStyle.TRANSPARENT);

        box.setOnMousePressed(e->{
            xOffSet[0] =e.getSceneX();
            yOffset[0] =e.getSceneY();
        });
        box.setOnMouseDragged(e->{
            ((VBox)e.getSource()).getScene().getWindow().setX(e.getScreenX()-xOffSet[0]);
            ((VBox)e.getSource()).getScene().getWindow().setY(e.getScreenY()-yOffset[0]);
        });

        btnAdd.setOnAction(event -> {
            try{
                String name=txtName.getText();
                String address=txtAddress.getText();
                String contact=txtContact.getText();
                String nic=txtNIC.getText();

                if(!nic.matches("^[0-9]{9}[vV]$")){
                    txtNIC.requestFocus();
                }

                if(!name.equals("") && !address.equals("") && !contact.equals("") && !nic.equals("")){
                    boolean success = controller.addCustomer(new CustomerDTO(
                            name,address,contact,nic
                    ));

                    if(success){
                        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Add New Customer");
                        alert.setHeaderText("Add New Customer");
                        alert.setContentText("Customer added successfully");
                        alert.getButtonTypes().setAll(ButtonType.OK);
                        alert.getDialogPane().getStylesheets().add(getClass().getResource("../util/css/thogaKade.css").toExternalForm());
                        alert.showAndWait();
                    }else {
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Add New Customer");
                        alert.setHeaderText("Add New Customer");
                        alert.setContentText("Error Occurred while Adding Customer");
                        alert.getButtonTypes().setAll(ButtonType.OK);
                        alert.getDialogPane().getStylesheets().add(getClass().getResource("../util/css/thogaKade.css").toExternalForm());
                        alert.showAndWait();
                    }
                }
            }catch (NullPointerException ex){
                ex.printStackTrace();
                return;
            }

            txtName.setText("");
            txtAddress.setText("");
            txtContact.setText("");
            txtNIC.setText("");

        });

        btnCancel.setOnAction(event -> {
            btnCancel.getScene().getWindow().hide();
        });

        txtContact.setOnKeyTyped(event -> {
            intRestriction(event);
        });

        txtName.setOnKeyTyped(event -> {
            if(!event.getCharacter().matches("[a-z]|[A-Z]+")&&(event.getCode()!= KeyCode.BACK_SPACE)&&!(event.getCharacter().matches("\\s"))){
                event.consume();
            }
        });

        customerStage.showAndWait();
        loadCustomers();
    }

    private void clearFields(){
        txtQty.setText("");
        txtTotal.setText("");
    }



}

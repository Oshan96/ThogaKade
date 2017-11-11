package lk.edu.ijse.ThogaKadeHibernate.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.edu.ijse.ThogaKadeHibernate.controller.ManagerController;
import lk.edu.ijse.ThogaKadeHibernate.controller.factory.ControllerFactory;
import lk.edu.ijse.ThogaKadeHibernate.controller.factory.ControllerType;
import lk.edu.ijse.ThogaKadeHibernate.dto.ItemDTO;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by oshan on 25-Oct-17.
 */
public class ThogaKade implements Initializable {
    private double xOffset;
    private double yOffset;

    private AnchorPane dynamic;

    @FXML
    private AnchorPane titleBar;
    @FXML
    private AnchorPane changePane;
    @FXML
    private Button btnNewOrder;
    @FXML
    private Button btnMngOrders;
    @FXML
    private Button btnAddNewItem;
    @FXML
    private TableView<String> tblView;


//    private ObservableList<String> data = FXCollections.observableArrayList("Item1","Item2","Item3");

    private ManagerController controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        rootPane.setPrefHeight(changePane.getPrefHeight());
//        rootPane.setPrefWidth(changePane.getPrefWidth());
        controller=(ManagerController) ControllerFactory.getInstance().getController(ControllerType.MANAGER);

        try {
            dynamic= FXMLLoader.load(getClass().getResource("../fxml/PlaceOrder.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //changePane=dynamic;
        setChangePane(dynamic);

        titleBar.setOnMousePressed(event -> {
            xOffset=event.getSceneX();
            yOffset=event.getSceneY();
        });

        titleBar.setOnMouseDragged(event -> {
            titleBar.getScene().getWindow().setX(event.getScreenX()-xOffset);
            titleBar.getScene().getWindow().setY(event.getScreenY()-yOffset);
        });

//        cmb.setItems(data);

//        tblView.getItems().addAll("Hello","Hello");

    }

    private void setChangePane(AnchorPane changePane) {
        this.changePane.getChildren().clear();
        this.changePane.getChildren().add(changePane);

    }

    @FXML
    private void placeOrderAction(ActionEvent evt){
        try {
            setChangePane(FXMLLoader.load(getClass().getResource("../fxml/PlaceOrder.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void mngOrderAction(ActionEvent evt){
        try {
            setChangePane(FXMLLoader.load(getClass().getResource("../fxml/ManageOrders.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addItemAction(ActionEvent evt){
        final double[] xOffSet = {0.0};
        final double[] yOffset = {0.0};

        Stage itemStage=new Stage();

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
        Label lblTitle=new Label("Add New Item");
        lblTitle.setPrefWidth(370);
        lblTitle.getStyleClass().add("header-tag");

        lblTitle.setAlignment(Pos.CENTER);
        title.getChildren().add(lblTitle);
        box.getChildren().add(title);

        HBox details=new HBox();
        //Labels
        Label lblDesc=new Label("Item Description ");
        lblDesc.getStyleClass().add("naming-tags");
        Label lblQtyOnHand=new Label("Qty On Hand ");
        lblQtyOnHand.getStyleClass().add("naming-tags");
        Label lblUnitPrice=new Label("UnitPrice ");
        lblUnitPrice.getStyleClass().add("naming-tags");

        VBox boxlbl=new VBox();


        boxlbl.getChildren().addAll(lblDesc,lblQtyOnHand,lblUnitPrice);
        details.getChildren().add(boxlbl);

        //TextFields
        TextField txtDesc=new TextField();
        TextField txtQtyOnHand=new TextField();
        TextField txtUnitPrice=new TextField();


        VBox boxTxt=new VBox();
        boxTxt.setPrefWidth(230);
        boxTxt.setSpacing(20);
        boxTxt.getChildren().addAll(txtDesc,txtQtyOnHand,txtUnitPrice);

        details.getChildren().add(boxTxt);


        box.getChildren().add(details);

        //Buttons
        HBox boxBtn=new HBox();
        boxBtn.setAlignment(Pos.CENTER_RIGHT);
        boxBtn.setSpacing(10);
        boxBtn.setPadding(new Insets(0,27,0,0));
        Button btnAdd=new Button("Add Item");
        btnAdd.getStyleClass().add("add-btn");
        Button btnCancel=new Button("Cancel");
        btnCancel.getStyleClass().add("cancel-btn");

        boxBtn.getChildren().addAll(btnAdd,btnCancel);

        box.getChildren().add(boxBtn);

        itemStage.setScene(scene);

        itemStage.initStyle(StageStyle.TRANSPARENT);

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
                String desc=txtDesc.getText();
                String qtyOnHand=txtQtyOnHand.getText();
                String unitPrice=txtUnitPrice.getText();

                if(!desc.equals("") && !qtyOnHand.equals("") && !unitPrice.equals("")){
                    boolean success=false;
                    try{
                        success = controller.addNewItem(

                                new ItemDTO(
                                        desc,
                                        Integer.parseInt(qtyOnHand),
                                        Double.parseDouble(unitPrice)
                                )
                        );
                    }catch (NumberFormatException ex){
                        ex.printStackTrace();
                    }

                    if(success){
                        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Add New Item");
                        alert.setHeaderText("Add New Item");
                        alert.setContentText("Item added successfully");
                        alert.getButtonTypes().setAll(ButtonType.OK);
                        alert.getDialogPane().getStylesheets().add(getClass().getResource("../util/css/thogaKade.css").toExternalForm());
                        alert.showAndWait();
                    }else {
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Add New Item");
                        alert.setHeaderText("Add New Item");
                        alert.setContentText("Error Occurred while Adding Item");
                        alert.getButtonTypes().setAll(ButtonType.OK);
                        alert.getDialogPane().getStylesheets().add(getClass().getResource("../util/css/thogaKade.css").toExternalForm());
                        alert.showAndWait();
                    }
                }
            }catch (NullPointerException ex){
                ex.printStackTrace();
                return;
            }

            txtDesc.setText("");
            txtQtyOnHand.setText("");
            txtUnitPrice.setText("");

        });

        btnCancel.setOnAction(event -> {
            btnCancel.getScene().getWindow().hide();
        });

        txtQtyOnHand.setOnKeyTyped(event -> {
            if(!event.getCharacter().matches("[0-9]+")&&(event.getCode()!= KeyCode.BACK_SPACE)){
                event.consume();
            }
        });

        txtUnitPrice.setOnKeyTyped(event -> {
            if(!event.getCharacter().matches("[0-9]+")&&(event.getCode()!= KeyCode.BACK_SPACE)&& !event.getCharacter().equals(".")){
                if(txtUnitPrice.getText().contains(".")){
                    event.consume();
                    return;
                }
                event.consume();
            }
        });

        txtDesc.setOnKeyTyped(event -> {
            if(!event.getCharacter().matches("[0-9]+")&&!event.getCharacter().matches("[a-z]|[A-Z]+")&&(event.getCode()!= KeyCode.BACK_SPACE)&&!(event.getCharacter().matches("\\s"))){
                event.consume();
            }
        });

        itemStage.showAndWait();

    }

    @FXML
    private void closeAction(MouseEvent evt){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Exit");
        alert.setContentText("Are you sure you want to exit?");
        alert.getButtonTypes().setAll(ButtonType.YES,ButtonType.NO);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("../util/css/thogaKade.css").toExternalForm());
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.YES){
            System.exit(0);
        }
    }

}

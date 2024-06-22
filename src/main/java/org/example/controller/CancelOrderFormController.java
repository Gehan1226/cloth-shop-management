package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import org.example.bo.BoFactory;
import org.example.bo.custom.OrderBo;
import org.example.dto.Order;
import org.example.util.BoType;

import java.net.URL;
import java.util.ResourceBundle;

public class CancelOrderFormController implements Initializable {
    public Text txtEmailValidation;
    public Text txtMobileNumberValidation;
    public Text txtPayementType;
    public Text txtOrderDate;
    public Text txtCustomername;
    public Text txtCustomerEmail;
    public Text txtCustomerMobileNumber;
    public Text txtFullPrice;
    public JFXTextField txtOrderID;
    public TableView<String[]> tblItem= new TableView<>();
    public TableColumn<String[], String> colItemID;
    public TableColumn<String[], String> colItemName;
    public TableColumn<String[], String> colSize;
    public TableColumn<String[], String> colQuantity;
    public TableColumn<String[], String> colPrice;
    public JFXButton btnCancelOrder;
    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        colItemName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        colSize.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
        colQuantity.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));
        colPrice.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4]));
    }

    public void btnMainmenuOnAction(ActionEvent actionEvent) {
    }

    public void btncancelorderOnAction(ActionEvent actionEvent) {
        if (orderBo.deleteOrder(txtOrderID.getText())){
            new Alert(Alert.AlertType.INFORMATION, "Order Delete Successfully !").show();
            return;
        }
        new Alert(Alert.AlertType.ERROR, "Order Delete Failed !").show();

    }

    public void btnDashboardOnAction(ActionEvent actionEvent) {
    }

    public void btnSearch(ActionEvent actionEvent) {
        Order order = orderBo.getOrder(txtOrderID.getText());
        if (order != null){
            System.out.println(order.getFullPrice());
            txtCustomername.setText(order.getCustomer().getName());
            txtCustomerEmail.setText(order.getCustomer().getEmail());
            txtCustomerMobileNumber.setText(order.getCustomer().getMobileNumber());
            //txtFullPrice.setText(order.getFullPrice().toString());
            //txtPayementType.setText(order.getordert);
            txtOrderDate.setText(order.getOrderDate().toString());
            btnCancelOrder.setDisable(false);
        }

    }

    public void btnSearchUserOnAction(ActionEvent actionEvent) {
    }

    public void btnUserListOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateRemoveOnAction(ActionEvent actionEvent) {
    }


}

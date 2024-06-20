package org.example.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import org.example.bo.BoFactory;
import org.example.bo.custom.OrderBo;
import org.example.util.BoType;

import java.net.URL;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {
    public JFXTextField txtCustomerEmail;
    public JFXComboBox cmbItem;
    public Text txtEmailValidation;
    public Text txtMobileNumberValidation;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerMobileNumber;
    public JFXRadioButton btnCreditCard;
    public JFXRadioButton btnCash;
    public TableView tblItem;
    public TableColumn colItemID;
    public TableColumn colItemName;
    public TableColumn colSize;
    public TableColumn colPrice;
    public Text txtOrderID;
    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtOrderID.setText(orderBo.genarateOrderID());
    }

    public void btnMainmenuOnAction(ActionEvent actionEvent) {
    }

    public void txtMobileNoOnKeyReleased(KeyEvent keyEvent) {
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrder(ActionEvent actionEvent) {
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) {
    }

    public void btnSearchUserOnAction(ActionEvent actionEvent) {
    }

    public void btnUserListOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateRemoveOnAction(ActionEvent actionEvent) {
    }


}

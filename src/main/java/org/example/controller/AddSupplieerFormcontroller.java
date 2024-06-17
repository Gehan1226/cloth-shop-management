package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.DataValidationBo;
import org.example.bo.custom.ItemBo;
import org.example.bo.custom.SupplierBo;
import org.example.util.BoType;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSupplieerFormcontroller implements Initializable {
    public static Stage primaryStage;
    public JFXTextField txtFirstName;
    public JFXTextField txtCompany;
    public Text txtEmailValidation;
    public Text txtMobileNumberValidation;
    public JFXTextField txtLastName;
    public JFXTextField txtEmail;
    public JFXTextField txtMobileNumber;
    public JFXButton btnResetPassword;
    public TableView tblItems;
    public TableColumn colItemId;
    public TableColumn colItemName;
    public Text txtSupplierID;
    public JFXComboBox cmbItem;
    private SupplierBo supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private DataValidationBo dataValidationBo = BoFactory.getInstance().getBo(BoType.VALIDATE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtSupplierID.setText(supplierBo.genarateSupplierID());
        cmbItem.getItems().addAll(itemBo.getAllIDSAndNames());
    }
    public void btnMainmenuOnAction(ActionEvent actionEvent) {
    }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
    }

    public void txtMobileNoOnKeyReleased(KeyEvent keyEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) {
    }

    public void btnSearchSupplierOnAction(ActionEvent actionEvent) {
    }

    public void btnSupplierListOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateRemoveOnAction(ActionEvent actionEvent) {
    }


}

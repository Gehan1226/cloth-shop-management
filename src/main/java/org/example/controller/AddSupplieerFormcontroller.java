package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.DataValidationBo;
import org.example.bo.custom.ItemBo;
import org.example.bo.custom.SupplierBo;
import org.example.dto.Supplier;
import org.example.util.BoType;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    public TableView<String[]> tblItems = new TableView<>();
    public TableColumn <String[], String> colItemId;
    public TableColumn <String[], String> colItemName;
    public Text txtSupplierID;
    public JFXComboBox cmbItem;
    private SupplierBo supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private DataValidationBo dataValidationBo = BoFactory.getInstance().getBo(BoType.VALIDATE);
    private List<String> allIDSAndNames ;
    private boolean isValidEmail;
    private boolean isValidMobileNo;
    private List<String> itemIDS = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        colItemName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
//        String stringOne = "First String";
//        String stringTwo = "Second String";
//        String[] row = { stringOne, stringTwo };
//        tblItems.getItems().add(row);
        txtSupplierID.setText(supplierBo.genarateSupplierID());
        allIDSAndNames = itemBo.getAllIDSAndNames();
        cmbItem.getItems().addAll(allIDSAndNames);
    }
    public void btnMainmenuOnAction(ActionEvent actionEvent) {
    }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        if (! dataValidationBo.isValidEmail(txtEmail.getText())){
            txtEmailValidation.setVisible(true);
            isValidEmail=false;
            return;
        }
        txtEmailValidation.setVisible(false);
        isValidEmail=true;
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        if (cmbItem.getValue() != null){
            Object selectedItem = cmbItem.getSelectionModel().getSelectedItem();
            String cmbValue = selectedItem.toString();
            String[] arr = cmbValue.split(" - ");
            tblItems.getItems().add(arr);
            cmbItem.getItems().remove(selectedItem);
            cmbItem.getSelectionModel().clearSelection();
            itemIDS.add(arr[0]);
        }
    }

    public void txtMobileNoOnKeyReleased(KeyEvent keyEvent) {
        if (! dataValidationBo.isValidMobileNumber(txtMobileNumber.getText())){
            txtMobileNumberValidation.setVisible(true);
            isValidMobileNo=false;
            return;
        }
        txtMobileNumberValidation.setVisible(false);
        isValidMobileNo=true;
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        boolean allFieldsNotEmpty = dataValidationBo.isAllFieldsNotEmpty(
                txtFirstName.getText(),
                txtLastName.getText(),
                txtEmail.getText(),
                txtCompany.getText(),
                txtMobileNumber.getText()
        );
        if (allFieldsNotEmpty && isValidEmail && isValidMobileNo && !itemIDS.isEmpty()){
            Supplier supplier = new Supplier(
                    txtSupplierID.getText(),
                    txtFirstName.getText(),
                    txtLastName.getText(),
                    txtCompany.getText(),
                    txtEmail.getText(),
                    txtMobileNumber.getText(),
                    new ArrayList<>()
            );
            if(supplierBo.saveSupplier(supplier,itemIDS)){
                new Alert(Alert.AlertType.INFORMATION, "✅ Item Saved !").show();
                return;
            }
            new Alert(Alert.AlertType.ERROR, "❌ Item Save Failed !").show();
            return;
        }
        new Alert(Alert.AlertType.INFORMATION, "Please fill every field!").show();
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) {
    }

    public void btnSearchSupplierOnAction(ActionEvent actionEvent) {
    }

    public void btnSupplierListOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateRemoveOnAction(ActionEvent actionEvent) {
    }
    public void btnClearOnAction(ActionEvent actionEvent) {
        tblItems.getItems().clear();
        cmbItem.getItems().clear();
        cmbItem.getItems().addAll(allIDSAndNames);
        itemIDS.clear();
    }
}

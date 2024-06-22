package org.example.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.DataValidationBo;
import org.example.bo.custom.ItemBo;
import org.example.bo.custom.SupplierBo;
import org.example.dto.Item;
import org.example.dto.Supplier;
import org.example.util.BoType;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateRemoveSupplierFormController implements Initializable {
    public static Stage primaryStage;
    public JFXTextField txtFisrstName;
    public Text txtEmailValidation;
    public Text txtMobileNumberValidation;
    public JFXTextField txtLastName;
    public JFXTextField txtCompany;
    public JFXComboBox cmbItemIDS;
    public JFXTextField txtEmail;
    public JFXTextField txtMobileNumber;
    public JFXTextField txtSupplierID;
    public TableView<String[]> tblItem = new TableView<>();
    public TableColumn<String[], String> colItemID;
    public TableColumn<String[], String> colItemName;
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private DataValidationBo dataValidationBo = BoFactory.getInstance().getBo(BoType.VALIDATE);
    private SupplierBo supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);
    private List<String> allItemIDSNames;
    private List<String> selectedItemIDS = new ArrayList<>();
    private List<String> cmbValues;
    private String currentSupplierID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        colItemName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        cmbValues = itemBo.getAllIDSAndNames();
        allItemIDSNames = itemBo.getAllIDSAndNames();
    }

    public void btnMainmenuOnAction(ActionEvent actionEvent) {
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        if (cmbItemIDS.getValue() != null) {
            Object selectedItem = cmbItemIDS.getSelectionModel().getSelectedItem();
            String temp = selectedItem.toString();
            String[] arr = temp.split(" - ");
            tblItem.getItems().add(arr);
            cmbItemIDS.getItems().remove(selectedItem);
            cmbItemIDS.getSelectionModel().clearSelection();
            selectedItemIDS.add(arr[0]);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        boolean isAllFieldsNotEmpty = dataValidationBo.isAllFieldsNotEmpty(
                currentSupplierID,
                txtFisrstName.getText(),
                txtLastName.getText(),
                txtCompany.getText(),
                txtEmail.getText(),
                txtMobileNumber.getText()
        );
        if (isAllFieldsNotEmpty) {
            Supplier supplier = new Supplier(
                    txtSupplierID.getText(),
                    txtFisrstName.getText(),
                    txtLastName.getText(),
                    txtCompany.getText(),
                    txtEmail.getText(),
                    txtMobileNumber.getText(),
                    null
            );
            if (supplierBo.updateSupplier(supplier,selectedItemIDS)){
                new Alert(Alert.AlertType.INFORMATION, "✅ Supplier Update Successfully !").show();
                return;
            }
            new Alert(Alert.AlertType.ERROR, "❌ Supplier Update Failed!").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        if(supplierBo.deleteSupplier(currentSupplierID)){
            new Alert(Alert.AlertType.INFORMATION, "✅ Supplier Delete Successfully !").show();
            return;
        }
        new Alert(Alert.AlertType.ERROR, "❌ Supplier Delete Failed!").show();
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) {
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        Supplier supplier = supplierBo.retrieveById(txtSupplierID.getText());
        if (supplier != null) {
            currentSupplierID = supplier.getSupID();
            txtFisrstName.setText(supplier.getFirstName());
            txtLastName.setText(supplier.getLastName());
            txtCompany.setText(supplier.getCompany());
            txtEmail.setText(supplier.getEmail());
            txtMobileNumber.setText(supplier.getMobileNumber());

            List<Item> items = supplier.getItemList();
            for (Item item : items) {
                String[] temp = {item.getItemId(), item.getItemName()};
                tblItem.getItems().add(temp);
                selectedItemIDS.add(item.getItemId());
                cmbValues.remove(temp[0] + " - " + temp[1]);
            }
            cmbItemIDS.getItems().addAll(cmbValues);
        }
    }

    public void btnSearchSupplierOnAction(ActionEvent actionEvent) {
    }

    public void btnSupplierListOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateRemoveOnAction(ActionEvent actionEvent) {
    }

}

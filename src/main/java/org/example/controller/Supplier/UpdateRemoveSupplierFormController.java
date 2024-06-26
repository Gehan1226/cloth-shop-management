package org.example.controller.Supplier;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.DataValidationBo;
import org.example.bo.custom.ItemBo;
import org.example.bo.custom.SupplierBo;
import org.example.controller.Admin.AdminDashboardFormController;
import org.example.controller.HomePageFormController;
import org.example.controller.Supplier.AddSupplieerFormcontroller;
import org.example.controller.User.UserDashboardFormController;
import org.example.dto.Item;
import org.example.dto.Supplier;
import org.example.util.BoType;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateRemoveSupplierFormController implements Initializable {
    public static Stage primaryStage;
    public static boolean isAdmin;
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
                    new ArrayList<>()
            );
            if (supplierBo.updateSupplier(supplier,selectedItemIDS)){
                clearFields();
                new Alert(Alert.AlertType.INFORMATION, "✅ Supplier Update Successfully !").show();
                return;
            }
            new Alert(Alert.AlertType.ERROR, "❌ Supplier Update Failed!").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        if(supplierBo.deleteSupplier(currentSupplierID)){
            clearFields();
            new Alert(Alert.AlertType.INFORMATION, "✅ Supplier Delete Successfully !").show();
            return;
        }
        new Alert(Alert.AlertType.ERROR, "❌ Supplier Delete Failed!").show();
    }

    private void clearFields(){
        txtSupplierID.setText("");
        txtFisrstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtCompany.setText("");
        txtMobileNumber.setText("");
        cmbItemIDS.getItems().removeAll();
        cmbItemIDS.getItems().addAll(allItemIDSNames);
        tblItem.getItems().clear();
        selectedItemIDS.clear();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        Supplier supplier = supplierBo.retrieveById(txtSupplierID.getText());
        if (supplier != null) {
            new Alert(Alert.AlertType.INFORMATION, "✅ Supplier Found!").show();
            txtSupplierID.setText(supplier.getSupID());
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
            return;
        }
        clearFields();
        new Alert(Alert.AlertType.INFORMATION, "❌ Supplier Not Found!").show();
    }
    public void btnDashboardOnAction(ActionEvent actionEvent) {
        String path = isAdmin ? "view/adminDashboard.fxml": "view/userDashboardForm.fxml";
        try {
            primaryStage.close();
            URL fxmlLocation = getClass().getClassLoader().getResource(path);
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            if (isAdmin){
                AdminDashboardFormController.primaryStage = stage;
                return;
            }
            UserDashboardFormController.primaryStage = stage;

        } catch (IOException e) {
        }
    }
    public void btnMainmenuOnAction(ActionEvent actionEvent) {
        try {
            primaryStage.close();
            URL fxmlLocation = getClass().getClassLoader().getResource("view/home_page_from.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            HomePageFormController.primaryStage = stage;
        } catch (IOException e) {
        }
    }

    public void btnAddSupplierOnAction(ActionEvent actionEvent) {
        AddSupplieerFormcontroller.isAdmin = isAdmin;
        try {
            primaryStage.close();
            URL fxmlLocation = getClass().getClassLoader().getResource("view/addSupplierForm.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            AddSupplieerFormcontroller.primaryStage = stage;
        } catch (IOException e) {
        }
    }
}

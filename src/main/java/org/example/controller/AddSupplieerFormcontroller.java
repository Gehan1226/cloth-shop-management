package org.example.controller;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.DataValidationBo;
import org.example.bo.custom.ItemBo;
import org.example.bo.custom.SupplierBo;
import org.example.dto.Supplier;
import org.example.util.BoType;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddSupplieerFormcontroller implements Initializable {
    public static Stage primaryStage;
    public static boolean isAdmin;
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
        txtSupplierID.setText(supplierBo.genarateSupplierID());
        allIDSAndNames = itemBo.getAllIDSAndNames();
        cmbItem.getItems().addAll(allIDSAndNames);
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
                txtSupplierID.setText(supplierBo.genarateSupplierID());
                txtFirstName.setText("");
                txtEmail.setText("");
                txtLastName.setText("");
                txtCompany.setText("");
                txtMobileNumber.setText("");
                cmbItem.getItems().removeAll();
                cmbItem.getItems().addAll(allIDSAndNames);
                tblItems.getItems().clear();

                new Alert(Alert.AlertType.INFORMATION, "✅ Item Saved !").show();
                return;
            }
            new Alert(Alert.AlertType.ERROR, "❌ Item Save Failed !").show();
            return;
        }
        new Alert(Alert.AlertType.INFORMATION, "Please fill every field!").show();
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        tblItems.getItems().clear();
        cmbItem.getItems().clear();
        cmbItem.getItems().addAll(allIDSAndNames);
        itemIDS.clear();
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
    public void btnSupplierUpdateRemoveOnAction(ActionEvent actionEvent) {
        UpdateRemoveSupplierFormController.isAdmin = isAdmin;
        try {
            primaryStage.close();
            URL fxmlLocation = getClass().getClassLoader().getResource("view/updateSupplierForm.fxml");
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


}

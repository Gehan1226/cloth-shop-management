package org.example.controller.Item;

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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.DataValidationBo;
import org.example.bo.custom.ItemBo;
import org.example.bo.custom.SupplierBo;
import org.example.controller.Admin.AdminDashboardFormController;
import org.example.controller.HomePageFormController;
import org.example.controller.User.UserDashboardFormController;
import org.example.dto.Item;
import org.example.util.BoType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddItemFormController implements Initializable {
    public static Stage primaryStage;
    public static boolean isAdmin;
    public JFXTextField txtItemName;
    public JFXTextField txtQTY;
    public JFXTextField txtPrice;
    public JFXComboBox cmbSize;
    public JFXComboBox cmbSupplierID;
    public Text txtItemID;
    public JFXButton btnSave;
    public TableView<String[]> tblSuppliers = new TableView<>();
    public TableColumn<String[], String> colSupplierID;
    public TableColumn<String[], String> colSupplierName;
    public JFXComboBox cmbCategorie;
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private DataValidationBo dataValidationBo = BoFactory.getInstance().getBo(BoType.VALIDATE);
    private SupplierBo supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);
    private List<String> suplierIDS = new ArrayList<>();
    private List<String> allIDSAndNames;
    private String imagePath;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSupplierID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        colSupplierName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));

        txtItemID.setText(itemBo.genarateItemID());
        String[] sizesArr = {"XSMALL","SMALL","MEDIUM","LARGE","X LARGE","2X LARGE","3X LARGE","4X LARGE"};
        cmbSize.getItems().addAll(sizesArr);
        allIDSAndNames = supplierBo.getAllIDSAndNames();
        cmbSupplierID.getItems().addAll(allIDSAndNames);
        String[] categoriesArr = {"Ladies","Gents","Kids"};
        cmbCategorie.getItems().addAll(categoriesArr);

        addListenerTxtPrice();
        addListenerTxtQTY();
    }
    private void addListenerTxtPrice(){
        txtPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*([.]\\d*)?")) {
                txtPrice.setText(oldValue);
            }
        });
    }
    private void addListenerTxtQTY(){
        txtQTY.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtQTY.setText(oldValue);
            }
        });
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        boolean isAllFieldsNotEmpty = dataValidationBo.isAllFieldsNotEmpty(
                txtItemName.getText(),
                txtPrice.getText(),
                txtQTY.getText(),
                imagePath
        );
        if (isAllFieldsNotEmpty && !cmbSize.getSelectionModel().isEmpty()){
            Item item = new Item(
                    txtItemID.getText(),
                    txtItemName.getText(),
                    cmbSize.getSelectionModel().getSelectedItem().toString(),
                    Double.parseDouble(txtPrice.getText()),
                    Integer.parseInt(txtQTY.getText()),
                    cmbCategorie.getSelectionModel().getSelectedItem().toString(),
                    imagePath,
                    new ArrayList<>(),
                    new ArrayList<>()
            );
           if(itemBo.saveItem(item, suplierIDS)){
               txtItemID.setText(itemBo.genarateItemID());
               txtItemName.setText("");
               txtPrice.setText("");
               txtQTY.setText("");
               cmbCategorie.getSelectionModel().clearSelection();
               cmbSupplierID.getSelectionModel().clearSelection();
               cmbSize.getSelectionModel().clearSelection();
               imagePath = null;
               suplierIDS.clear();
               tblSuppliers.getItems().clear();
               new Alert(Alert.AlertType.INFORMATION, "✅ Item Saved!").show();
                return;
           }
            new Alert(Alert.AlertType.ERROR, "❌ Item Save Failed!").show();
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        if (cmbSupplierID.getValue() != null){
            Object selectedItem = cmbSupplierID.getSelectionModel().getSelectedItem();
            String cmbValue = selectedItem.toString();
            String[] arr = cmbValue.split(" - ");
            tblSuppliers.getItems().add(arr);
            cmbSupplierID.getItems().remove(selectedItem);
            cmbSupplierID.getSelectionModel().clearSelection();
            suplierIDS.add(arr[0]);
        }
    }
    public void btnClearOnAction(ActionEvent actionEvent) {
        tblSuppliers.getItems().clear();
        cmbSupplierID.getItems().clear();
        cmbSupplierID.getItems().addAll(allIDSAndNames);
        suplierIDS.clear();
    }

    public void btnAddImageOnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            imagePath = file.toURI().toString();
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

    public void btnUpdateRemoveItemOnAction(ActionEvent actionEvent) {
        UpdateRemoveItemFormController.isAdmin = isAdmin;
        try {
            primaryStage.close();
            URL fxmlLocation = getClass().getClassLoader().getResource("view/updateItemForm.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            UpdateRemoveItemFormController.primaryStage = stage;
        } catch (IOException e) {
        }
    }
}
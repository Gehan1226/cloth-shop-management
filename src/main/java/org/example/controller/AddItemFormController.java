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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.DataValidationBo;
import org.example.bo.custom.ItemBo;
import org.example.bo.custom.SupplierBo;
import org.example.dto.Item;
import org.example.dto.Supplier;
import org.example.util.BoType;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddItemFormController implements Initializable {
    public static Stage primaryStage;
    public JFXTextField txtItemName;
    public JFXTextField txtQTY;
    public JFXTextField txtPrice;
    public JFXComboBox cmbSize;
    public JFXComboBox cmbSupplierID;
    public Text txtItemID;
    public JFXButton btnSave;
    public TableView<String[]> tblSuppliers = new TableView<>();;
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
            if (!newValue.matches("\\d*([\\.]\\d*)?")) {
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
    public void btnMainmenuOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        boolean isAllFieldsNotEmpty = dataValidationBo.isAllFieldsNotEmpty(
                txtItemName.getText(),
                txtPrice.getText(),
                txtQTY.getText()
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
               new Alert(Alert.AlertType.INFORMATION, "✅ Item Saved!").show();
                return;
           }
            new Alert(Alert.AlertType.ERROR, "❌ Item Save Failed!").show();
        }
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) {
    }

    public void btnSearchSupplierOnAction(ActionEvent actionEvent) {
    }

    public void btnSupplierListOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateRemoveOnAction(ActionEvent actionEvent) {
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
}

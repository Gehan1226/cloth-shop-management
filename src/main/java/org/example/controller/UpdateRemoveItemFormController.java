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

public class UpdateRemoveItemFormController implements Initializable {
    public static Stage primaryStage;
    public JFXTextField txtItemName;
    public Text txtEmailValidation;
    public Text txtMobileNumberValidation;
    public ImageView imgCloth;
    public JFXTextField txtPrice;
    public JFXTextField txtQTY;
    public JFXComboBox cmbSize;
    public JFXComboBox cmbCategorie;
    public JFXComboBox cmbSupplierID;
    public JFXButton btnSave;
    public JFXTextField txtItemID;
    public TableView<String[]> tblSupplier = new TableView<>();
    public TableColumn<String[], String> colSupplierID;
    public TableColumn<String[], String> colSupplierName;
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private DataValidationBo dataValidationBo = BoFactory.getInstance().getBo(BoType.VALIDATE);
    private SupplierBo supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);
    private List<String> allIDS;
    private List<String> cmbValues;
    private List<String> selectedSuplierIDS = new ArrayList<>();
    private String currentItemID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSupplierID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        colSupplierName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));

        String[] sizesArr = {"XSMALL","SMALL","MEDIUM","LARGE","X LARGE","2X LARGE","3X LARGE","4X LARGE"};
        cmbSize.getItems().addAll(sizesArr);
        cmbValues = supplierBo.getAllIDSAndNames();
        allIDS = supplierBo.getAllIDSAndNames();
        String[] categoriesArr = {"Ladies","Gents","Kids"};
        cmbCategorie.getItems().addAll(categoriesArr);
    }
    public void btnMainmenuOnAction(ActionEvent actionEvent) {
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        if (cmbSupplierID.getValue() != null){
            Object selectedItem = cmbSupplierID.getSelectionModel().getSelectedItem();
            String temp = selectedItem.toString();
            String[] arr = temp.split(" - ");
            tblSupplier.getItems().add(arr);
            cmbSupplierID.getItems().remove(selectedItem);
            cmbSupplierID.getSelectionModel().clearSelection();
            selectedSuplierIDS.add(arr[0]);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        boolean isAllFieldsNotEmpty = dataValidationBo.isAllFieldsNotEmpty(
                currentItemID,
                txtItemName.getText(),
                txtPrice.getText(),
                txtQTY.getText()
        );
        boolean isComboboxSelected = !cmbSize.getSelectionModel().isEmpty() && !cmbCategorie.getSelectionModel().isEmpty();
        if (isAllFieldsNotEmpty && isComboboxSelected){
            Item item = new Item(
                    currentItemID,
                    txtItemName.getText(),
                    cmbSize.getSelectionModel().getSelectedItem().toString(),
                    Double.parseDouble(txtPrice.getText()),
                    Integer.parseInt(txtQTY.getText()),
                    cmbCategorie.getSelectionModel().getSelectedItem().toString(),
                    imgCloth.getImage().getUrl().toString(),
                    null
            );
            if (itemBo.updateItem(item,selectedSuplierIDS)){
                new Alert(Alert.AlertType.INFORMATION, "✅ Item Update Successfully !").show();
                return;
            }
            new Alert(Alert.AlertType.ERROR, "❌ Item Update Failed!").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        if(itemBo.deleteItem(currentItemID)){
            new Alert(Alert.AlertType.INFORMATION, "✅ Item Delete Successfully !").show();
            return;
        }
        new Alert(Alert.AlertType.ERROR, "❌ Item Delete Failed!").show();
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) {
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        Item item = itemBo.retrieveById(txtItemID.getText());
        if (item != null){
            currentItemID = item.getItemId();
            txtItemName.setText(item.getItemName());
            txtPrice.setText(item.getPrice()+"");
            txtQTY.setText(item.getQty()+"");
            cmbSize.getSelectionModel().select(item.getSize());
            cmbCategorie.getSelectionModel().select(item.getCategorie());

            if (item.getItemImagePath() != null){
                Image image = new Image(item.getItemImagePath());
                imgCloth.setImage(image);
            }

            List<Supplier> supplier = item.getSupplierList();
            for (Supplier sup : supplier){
                String[] temp = {sup.getSupID(),sup.getFirstName()+" "+sup.getLastName()};
                tblSupplier.getItems().add(temp);
                selectedSuplierIDS.add(sup.getSupID());

                if (cmbValues.contains(temp[0]+" - "+temp[1])){
                    cmbValues.remove(temp[0]+" - "+temp[1]);
                }
            }
            cmbSupplierID.getItems().addAll(cmbValues);
        }
    }
    public void btnSearchSupplierOnAction(ActionEvent actionEvent) {

    }

    public void btnSupplierListOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateRemoveOnAction(ActionEvent actionEvent) {
    }

    public void btnChangeImgOnAction(ActionEvent actionEvent) {
    }
}

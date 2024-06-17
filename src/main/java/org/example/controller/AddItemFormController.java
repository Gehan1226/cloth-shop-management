package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
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

public class AddItemFormController implements Initializable {
    public static Stage primaryStage;
    public JFXTextField txtItemName;
    public JFXTextField txtQTY;
    public JFXTextField txtPrice;
    public JFXComboBox cmbSize;
    public JFXComboBox cmbSupplierID;
    public Text txtItemID;
    public JFXButton btnSave;
    public TableView tblSuppliers;
    public TableColumn colSupplierID;
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private DataValidationBo dataValidationBo = BoFactory.getInstance().getBo(BoType.VALIDATE);
    private SupplierBo supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);
    private List<String> suplierIDS = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtItemID.setText(itemBo.genarateItemID());
        String[] sizesArr = {"XSMALL","SMALL","MEDIUM","LARGE","X LARGE","2X LARGE","3X LARGE","4X LARGE"};
        cmbSize.getItems().addAll(sizesArr);
        cmbSupplierID.getItems().add("Select Supplier ID");
        cmbSupplierID.getItems().addAll(supplierBo.getAllSupplierIDS());

        txtPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*([\\.]\\d*)?")) {
                txtPrice.setText(oldValue);
            }
        });

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
                    null
            );
            itemBo.saveItem(item,suplierIDS);

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
        suplierIDS.add(cmbSupplierID.getSelectionModel().getSelectedItem().toString());
    }
}

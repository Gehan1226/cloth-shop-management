package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.ItemBo;
import org.example.util.BoType;

import java.net.URL;
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
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    //private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtItemID.setText(itemBo.genarateItemID());
        String[] sizesArr = {"XSMALL","SMALL","MEDIUM","LARGE","X LARGE","2X LARGE","3X LARGE","4X LARGE"};
        cmbSize.getItems().addAll(sizesArr);
    }
    public void btnMainmenuOnAction(ActionEvent actionEvent) {
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

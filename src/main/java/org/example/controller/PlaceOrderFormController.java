package org.example.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import org.example.bo.BoFactory;
import org.example.bo.custom.ItemBo;
import org.example.bo.custom.OrderBo;
import org.example.dto.Item;
import org.example.util.BoType;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {
    public JFXTextField txtCustomerEmail;
    public JFXComboBox cmbItem;
    public Text txtEmailValidation;
    public Text txtMobileNumberValidation;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerMobileNumber;
    public JFXRadioButton btnCreditCard;
    public JFXRadioButton btnCash;
    public TableView<String[]> tblItem = new TableView<>();;
    public TableColumn<String[], String> colItemID;
    public TableColumn<String[], String> colItemName;
    public TableColumn<String[], String> colSize;
    public TableColumn<String[], String> colPrice;
    public Text txtOrderID;
    public Text txtDate;
    public Text txtPrice;
    public JFXComboBox cmbQuantity;
    private List<Item> itemList;
    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtOrderID.setText(orderBo.genarateOrderID());
        itemList = itemBo.getAllItems();
        for(Item item : itemList){
            String itemData = item.getItemId()+" - "+item.getItemName()+" - "+item.getSize();
            cmbItem.getItems().add(itemData);
        }
    }

    public void btnMainmenuOnAction(ActionEvent actionEvent) {
    }

    public void txtMobileNoOnKeyReleased(KeyEvent keyEvent) {
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        if (!cmbItem.getSelectionModel().isEmpty() && !cmbQuantity.getSelectionModel().isEmpty()){

        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrder(ActionEvent actionEvent) {
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) {
    }

    public void btnSearchUserOnAction(ActionEvent actionEvent) {
    }

    public void btnUserListOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateRemoveOnAction(ActionEvent actionEvent) {
    }

    public void btnCmbSelectItemOnAction(ActionEvent actionEvent) {
        cmbQuantity.getItems().clear();
        int selectedIndex = cmbItem.getSelectionModel().getSelectedIndex();
        if(itemList.get(selectedIndex).getQty() <= 0){
            new Alert(Alert.AlertType.INFORMATION, "Sorry this item is not in stock.").show();
            return;
        }
        for (int i=0;i< itemList.get(selectedIndex).getQty();i++){
            cmbQuantity.getItems().add(i+1);
        }
    }
}

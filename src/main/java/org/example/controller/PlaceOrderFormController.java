package org.example.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.example.bo.BoFactory;
import org.example.bo.custom.DataValidationBo;
import org.example.bo.custom.ItemBo;
import org.example.bo.custom.OrderBo;
import org.example.dto.Customer;
import org.example.dto.Employee;
import org.example.dto.Item;
import org.example.dto.Order;
import org.example.util.BoType;
import java.net.URL;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {
    public static String employeeID = "E1";
    public JFXTextField txtCustomerEmail;
    public JFXComboBox cmbItem;
    public Text txtEmailValidation;
    public Text txtMobileNumberValidation;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerMobileNumber;
    public JFXRadioButton btnCreditCard;
    public JFXRadioButton btnCash;
    public TableView<String[]> tblItem = new TableView<>();
    public TableColumn<String[], String> colItemID;
    public TableColumn<String[], String> colItemName;
    public TableColumn<String[], String> colSize;
    public TableColumn<String[], String> colPrice;
    public TableColumn<String[], String> colQuantity;
    public Text txtOrderID;
    public Text txtDate;
    public Text txtPrice;
    public JFXComboBox cmbQuantity;
    private List<Item> itemList;
    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private DataValidationBo dataValidationBo = BoFactory.getInstance().getBo(BoType.VALIDATE);
    private boolean isValidEmail;
    private boolean isValidMobileNo;
    private List<String> selectedItemIDs = new ArrayList<>();
    private DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
    private double fullPrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        colItemName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        colSize.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
        colQuantity.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));
        colPrice.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4]));

        ToggleGroup toggleGroup = new ToggleGroup();
        btnCash.setToggleGroup(toggleGroup);
        btnCreditCard.setToggleGroup(toggleGroup);

        txtOrderID.setText(orderBo.genarateOrderID());
        itemList = itemBo.getAllItems();
        for(Item item : itemList){
            String itemData = item.getItemId()+" - "+item.getItemName()+" - "+item.getSize()+" - "+item.getPrice();
            cmbItem.getItems().add(itemData);
        }
    }

    public void btnMainmenuOnAction(ActionEvent actionEvent) {
    }

    public void txtMobileNoOnKeyReleased(KeyEvent keyEvent) {
        if (! dataValidationBo.isValidMobileNumber(txtCustomerMobileNumber.getText())){
            txtMobileNumberValidation.setVisible(true);
            isValidMobileNo=false;
            return;
        }
        txtMobileNumberValidation.setVisible(false);
        isValidMobileNo=true;
    }
    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        if (! dataValidationBo.isValidEmail(txtCustomerEmail.getText())){
            txtEmailValidation.setVisible(true);
            isValidEmail=false;
            return;
        }
        txtEmailValidation.setVisible(false);
        isValidEmail=true;
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        if (!cmbItem.getSelectionModel().isEmpty() && !cmbQuantity.getSelectionModel().isEmpty()){
            int selectedIndex = cmbItem.getSelectionModel().getSelectedIndex();
            Item item = itemList.get(selectedIndex);
            double price = Integer.parseInt(cmbQuantity.getSelectionModel().getSelectedItem().toString()) * item.getPrice();
            String formattedNumber = decimalFormat.format(price);

            String[] rowData = {
                    item.getItemId(),
                    item.getItemName(),
                    item.getSize(),
                    cmbQuantity.getSelectionModel().getSelectedItem().toString(),
                    formattedNumber
            };
            tblItem.getItems().add(rowData);
            selectedItemIDs.add(item.getItemId());
            cmbItem.getSelectionModel().clearSelection();
            cmbQuantity.getSelectionModel().clearSelection();

            fullPrice += price;
            String fomatedFullPrice = decimalFormat.format(fullPrice);
            txtPrice.setText(fomatedFullPrice);
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        tblItem.getItems().clear();
        selectedItemIDs.clear();
    }

    public void btnPlaceOrder(ActionEvent actionEvent) {
        boolean isAllFieldsNotEmpty = dataValidationBo.isAllFieldsNotEmpty(
                txtCustomerName.getText(),
                txtCustomerEmail.getText(),
                txtCustomerMobileNumber.getText()
        );
        //boolean isSelecteType = btnCreditCard.isSelected() || btnCreditCard.isSelected();
        if (isAllFieldsNotEmpty && !selectedItemIDs.isEmpty()){
            Customer customer = new Customer(
                    txtCustomerName.getText(),
                    txtCustomerEmail.getText(),
                    txtCustomerMobileNumber.getText(),
                    new ArrayList<>()
            );
            Order order = new Order(
                    txtOrderID.getText(),
                    LocalDate.now(),
                    customer,
                    new ArrayList<>(),
                    null
            );
            System.out.println(orderBo.saveOrder(order,selectedItemIDs,employeeID));


        }

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
        cmbQuantity.setDisable(false);
        int selectedIndex = cmbItem.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1)return;
        if (!selectedItemIDs.contains(itemList.get(selectedIndex).getItemId())){
            cmbQuantity.getItems().clear();
            if(itemList.get(selectedIndex).getQty() <= 0){
                new Alert(Alert.AlertType.INFORMATION, "Sorry this item is not in stock.").show();
                return;
            }
            for (int i=0;i< itemList.get(selectedIndex).getQty();i++){
                cmbQuantity.getItems().add(i+1);
            }
            return;
        }
        new Alert(Alert.AlertType.INFORMATION, "This Item is already selected.").show();
        cmbQuantity.setDisable(true);
    }

}

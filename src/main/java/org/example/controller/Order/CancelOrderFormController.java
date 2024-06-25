package org.example.controller.Order;

import com.jfoenix.controls.JFXButton;
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
import org.example.bo.custom.OrderBo;
import org.example.controller.Admin.AdminDashboardFormController;
import org.example.controller.HomePageFormController;
import org.example.controller.User.UserDashboardFormController;
import org.example.dto.Order;
import org.example.util.BoType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CancelOrderFormController implements Initializable {
    public static Stage primaryStage;
    public static boolean isAdmin;
    public Text txtPayementType;
    public Text txtOrderDate;
    public Text txtCustomername;
    public Text txtCustomerEmail;
    public Text txtCustomerMobileNumber;
    public Text txtFullPrice;
    public JFXTextField txtOrderID;
    public TableView<String[]> tblItem= new TableView<>();
    public TableColumn<String[], String> colItemID;
    public TableColumn<String[], String> colItemName;
    public TableColumn<String[], String> colSize;
    public TableColumn<String[], String> colQuantity;
    public TableColumn<String[], String> colPrice;
    public JFXButton btnCancelOrder;
    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        colItemName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        colSize.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
        colQuantity.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));
        colPrice.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4]));
    }

    public void btncancelorderOnAction(ActionEvent actionEvent) {
        if (orderBo.deleteOrder(txtOrderID.getText())){
            clearFields();
            new Alert(Alert.AlertType.INFORMATION, "Order Delete Successfully !").show();
            return;
        }
        new Alert(Alert.AlertType.ERROR, "Order Delete Failed !").show();
    }

    public void btnSearch(ActionEvent actionEvent) {
        Order order = orderBo.getOrder(txtOrderID.getText());

        if (order != null){
            txtCustomername.setText(order.getData().get(0));
            txtCustomerEmail.setText(order.getData().get(1));
            txtCustomerMobileNumber.setText(order.getData().get(2));
            txtFullPrice.setText(order.getFullPrice().toString());
            txtPayementType.setText(order.getPayementType());
            txtOrderDate.setText(order.getOrderDate().toString());
            btnCancelOrder.setDisable(false);

            new Alert(Alert.AlertType.INFORMATION, "Order found !").show();
            return;
        }
        clearFields();
        new Alert(Alert.AlertType.INFORMATION, "Order Not found !").show();
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
    private void clearFields(){
        txtOrderDate.setText("");
        txtCustomername.setText("");
        txtFullPrice.setText("");
        txtPayementType.setText("");
        txtCustomerEmail.setText("");
        txtOrderID.setText("");
        tblItem.getItems().clear();
        txtCustomerMobileNumber.setText("");
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

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        if (!isAdmin){
            try {
                primaryStage.close();
                URL fxmlLocation = getClass().getClassLoader().getResource("view/placeOrderForm.fxml");
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                Parent parent = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.show();
                PlaceOrderFormController.primaryStage = stage;
            } catch (IOException e) {
            }
        }
    }
}

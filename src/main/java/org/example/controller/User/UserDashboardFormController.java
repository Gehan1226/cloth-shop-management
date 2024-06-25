package org.example.controller.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.EmployeeBo;
import org.example.bo.custom.ItemBo;
import org.example.bo.custom.SupplierBo;
import org.example.controller.Supplier.AddSupplieerFormcontroller;
import org.example.controller.Employee.EmployeeProfilePageFormController;
import org.example.controller.HomePageFormController;
import org.example.controller.Item.AddItemFormController;
import org.example.controller.Item.UpdateRemoveItemFormController;
import org.example.controller.Order.CancelOrderFormController;
import org.example.controller.Order.PlaceOrderFormController;
import org.example.controller.Supplier.UpdateRemoveSupplierFormController;
import org.example.util.BoType;

import java.io.IOException;
import java.net.URL;

public class UserDashboardFormController {
    public static String employeeUserEmail;
    public static Stage primaryStage;

    public Stage loadScreen(String path){
        Stage stage = null;
        try{
            primaryStage.close();
            URL fxmlLocation = getClass().getClassLoader().getResource(path);
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent parent = loader.load();
            stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
        }catch (IOException e){}
        return stage;
    }
    public void btnMainmenuOnAction(ActionEvent actionEvent) {
        Stage stage = loadScreen("view/home_page_from.fxml");
        HomePageFormController.primaryStage = stage;
    }

    public void btnProfileOnAction(ActionEvent actionEvent) {
        EmployeeProfilePageFormController.employeeUserEmail = employeeUserEmail;
        EmployeeProfilePageFormController.primaryStage = loadScreen("view/employeeProfilePageForm.fxml");
    }

    public void btnAddItemOnAction(ActionEvent actionEvent) {
        AddItemFormController.isAdmin = false;
        Stage stage = loadScreen("view/addItemForm.fxml");
        AddItemFormController.primaryStage = stage;
    }

    public void btnItemUpdateRemoveOnAction(ActionEvent actionEvent) {
        UpdateRemoveItemFormController.isAdmin = false;
        Stage stage = loadScreen("view/updateItemForm.fxml");
        UpdateRemoveItemFormController.primaryStage = stage;
    }

    public void btnAddSupplierOnAction(ActionEvent actionEvent) {
        AddSupplieerFormcontroller.isAdmin = false;
        Stage stage = loadScreen("view/addSupplierForm.fxml");
        AddSupplieerFormcontroller.primaryStage = stage;
    }

    public void btnSupplierUpdateRemoveOnAction(ActionEvent actionEvent) {
        UpdateRemoveSupplierFormController.isAdmin = false;
        Stage stage = loadScreen("view/updateSupplierForm.fxml");
        UpdateRemoveSupplierFormController.primaryStage = stage;
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        Stage stage = loadScreen("view/placeOrderForm.fxml");
        PlaceOrderFormController.primaryStage = stage;
    }

    public void btnCancelorderOnAction(ActionEvent actionEvent) {
        CancelOrderFormController.isAdmin = false;
        Stage stage = loadScreen("view/cancelOrderForm.fxml");
        CancelOrderFormController.primaryStage = stage;
    }

    public void btnPrintEmployeeReport(ActionEvent actionEvent) {
        EmployeeBo emploeeBo = BoFactory.getInstance().getBo(BoType.EMPLOYEE);
        if (!emploeeBo.genarateEmployeeReport()){
            new Alert(Alert.AlertType.ERROR,"System Error in EmployeeReport!").show();
        }
    }

    public void btnInventoryReportOnAction(ActionEvent actionEvent) {
        ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
        if (!itemBo.genarateInventoryReport()){
            new Alert(Alert.AlertType.ERROR,"System Error in InventoryReport!").show();
        }
    }

    public void btnSupplierReportOnAction(ActionEvent actionEvent) {
        SupplierBo supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);
        if (!supplierBo.genarateSupplierReport()){
            new Alert(Alert.AlertType.ERROR,"System Error in SupplierReport!").show();
        }
    }
}

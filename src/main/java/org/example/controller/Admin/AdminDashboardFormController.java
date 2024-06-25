package org.example.controller.Admin;

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
import org.example.controller.Employee.EmployeeUpdateRemoveFormController;
import org.example.controller.Employee.UserRegistrationFormController;
import org.example.controller.HomePageFormController;
import org.example.controller.Item.AddItemFormController;
import org.example.controller.Item.UpdateRemoveItemFormController;
import org.example.controller.Order.CancelOrderFormController;
import org.example.controller.Supplier.UpdateRemoveSupplierFormController;
import org.example.util.BoType;

import java.io.IOException;
import java.net.URL;

public class AdminDashboardFormController {
    public static Stage primaryStage;
    public static String adminUserEmail;
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
    public void btnUserRegistrationOnAction(ActionEvent actionEvent) {
        Stage stage = loadScreen("view/userRegistrationForm.fxml");
        UserRegistrationFormController.primaryStage = stage;
    }

    public void btnProfileOnAction(ActionEvent actionEvent) {
        AdminProfilePageFormcontroller.adminEmail = adminUserEmail;
        AdminProfilePageFormcontroller.primaryStage = loadScreen("view/adminProfilePageForm.fxml");
    }

    public void btnEmployeeUpdateRemoveOnAction(ActionEvent actionEvent) {
        Stage stage = loadScreen("view/employeeUpdateRemovePageForm.fxml");
        EmployeeUpdateRemoveFormController.primaryStage = stage;
    }

    public void btnAddItemOnAction(ActionEvent actionEvent) {
        AddItemFormController.isAdmin = true;
        Stage stage = loadScreen("view/addItemForm.fxml");
        AddItemFormController.primaryStage = stage;
    }

    public void btnItemUpdateRemoveOnAction(ActionEvent actionEvent) {
        UpdateRemoveItemFormController.isAdmin = true;
        Stage stage = loadScreen("view/updateItemForm.fxml");
        UpdateRemoveItemFormController.primaryStage = stage;
    }

    public void btnAddSupplierOnAction(ActionEvent actionEvent) {
        AddSupplieerFormcontroller.isAdmin = true;
        Stage stage = loadScreen("view/addSupplierForm.fxml");
        AddSupplieerFormcontroller.primaryStage = stage;
    }

    public void btnCancelOrderOnAction(ActionEvent actionEvent) {
        CancelOrderFormController.isAdmin = true;
        Stage stage = loadScreen("view/cancelOrderForm.fxml");
        CancelOrderFormController.primaryStage = stage;
    }

    public void btnSpplierUpdateRemoveOnAction(ActionEvent actionEvent) {
        UpdateRemoveSupplierFormController.isAdmin = true;
        Stage stage = loadScreen("view/updateSupplierForm.fxml");
        UpdateRemoveSupplierFormController.primaryStage = stage;
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

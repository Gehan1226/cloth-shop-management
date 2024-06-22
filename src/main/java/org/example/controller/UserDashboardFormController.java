package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
        Stage stage = loadScreen("view/addItemForm.fxml");
        AddItemFormController.primaryStage = stage;
    }

    public void btnItemUpdateRemoveOnAction(ActionEvent actionEvent) {
        Stage stage = loadScreen("view/updateItemForm.fxml");
        UpdateRemoveItemFormController.primaryStage = stage;
    }

    public void btnAddSupplierOnAction(ActionEvent actionEvent) {
        Stage stage = loadScreen("view/addSupplierForm.fxml");
        AddSupplieerFormcontroller.primaryStage = stage;
    }

    public void btnSupplierUpdateRemoveOnAction(ActionEvent actionEvent) {
        Stage stage = loadScreen("view/updateSupplierForm.fxml");
        UpdateRemoveSupplierFormController.primaryStage = stage;
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        Stage stage = loadScreen("view/placeOrderForm.fxml");
        PlaceOrderFormController.primaryStage = stage;
    }

    public void btnCancelorderOnAction(ActionEvent actionEvent) {
        Stage stage = loadScreen("view/cancelOrderForm.fxml");
        CancelOrderFormController.primaryStage = stage;
    }
}

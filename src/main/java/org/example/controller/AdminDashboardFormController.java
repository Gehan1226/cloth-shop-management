package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

    public void btnUpdateRemoveOnAction(ActionEvent actionEvent) {
        Stage stage = loadScreen("view/employeeUpdateRemovePageForm.fxml");
        HomePageFormController.primaryStage = stage;
    }

}

package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    }

    public void btnProfileOnAction(ActionEvent actionEvent) {
        ProfilePageFormController.employeeUserEmail = employeeUserEmail;
        ProfilePageFormController.primaryStage = loadScreen("view/profilePageForm.fxml");;

    }
}

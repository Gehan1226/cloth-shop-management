package org.example.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageFormController{
    public static Stage primaryStage;
    public void createButtonAction(ActionEvent actionEvent) {

    }

    public void loginButtonOnAction(ActionEvent actionEvent)  {
        try{
            primaryStage.close();
            Parent fxmlLoader = new FXMLLoader(getClass().getResource("../../../../resources/view/login_page_form.fxml")).load();
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader));
            stage.show();
            LoginPageController.primaryStage = stage;
        }catch (IOException e){
            System.out.println("JJJJ");
            throw new RuntimeException(e);
        }
    }

}

package org.example.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.AccountManage.CreateAccountFormController;
import org.example.controller.AccountManage.LoginPageFormController;

import java.io.IOException;
import java.net.URL;

public class HomePageFormController{
    public static Stage primaryStage;
    public JFXButton btnCreateaccount;
    public JFXButton btnLogin;

    public void createButtonAction(ActionEvent actionEvent) {
        try{
            primaryStage.close();
            URL fxmlLocation = getClass().getClassLoader().getResource("view/createAccountForm.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            CreateAccountFormController.primaryStage = stage;
        }catch (IOException e){}
    }

    public void loginButtonOnAction(ActionEvent actionEvent) throws IOException {
        try{
            primaryStage.close();
            URL fxmlLocation = getClass().getClassLoader().getResource("view/loginPageForm.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            LoginPageFormController.primaryStage = stage;
        }catch (IOException e){}
    }

}

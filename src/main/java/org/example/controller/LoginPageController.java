package org.example.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.dto.User;

import java.io.IOException;
import java.net.URL;

public class LoginPageController {
    public static Stage primaryStage;
    public JFXTextField txtEmailAddress;
    public JFXPasswordField txtPasssword;

    public void btnMainmenuOnAction(ActionEvent actionEvent) {
        try{
            primaryStage.close();
            URL fxmlLocation = getClass().getClassLoader().getResource("view/home_page_from.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            HomePageFormController.primaryStage = stage;
        }catch (IOException e){}

    }

    public void btnForgetpwOnAction(ActionEvent actionEvent) {
    }

    public void btnLogInOnAction(ActionEvent actionEvent) {
        if (isValidEmail(txtEmailAddress.getText())){
            User user = new User(txtEmailAddress.getText(),txtPasssword.getText(),false);
        }


    }

    public void btnCreateNewAccountOnAction(ActionEvent actionEvent) {
    }

    public boolean isValidEmail(String email){
        String regex = "^[A-Za-z0-9+_.-]+@gmail(.+)$";
        return email.matches(regex);
    }
}

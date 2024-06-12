package org.example.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.UserBo;
import org.example.dto.User;
import org.example.util.BoType;

import java.io.IOException;
import java.net.URL;

public class LoginPageFormController {
    public static Stage primaryStage;
    public JFXTextField txtEmailAddress;
    public JFXPasswordField txtPasssword;
    public JFXToggleButton btnUserType;
    private UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);
    private Boolean type = false;
    public void btnMainmenuOnAction(ActionEvent actionEvent) {
        try {
            primaryStage.close();
            URL fxmlLocation = getClass().getClassLoader().getResource("view/home_page_from.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            HomePageFormController.primaryStage = stage;
            stage.show();
        } catch (IOException e) {
        }
    }

    public void btnForgetpwOnAction(ActionEvent actionEvent) {
        if(!txtEmailAddress.getText().isEmpty()){
            if (userBo.isUser(txtEmailAddress.getText())){
                try {
                    primaryStage.close();
                    ResetPasswordFormController.currentEmail = txtEmailAddress.getText();
                    URL fxmlLocation = getClass().getClassLoader().getResource("view/resetPasswordForm.fxml");
                    FXMLLoader loader = new FXMLLoader(fxmlLocation);
                    Parent parent = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(parent));
                    stage.show();
                    ResetPasswordFormController.primaryStage = stage;
                } catch (IOException e) {
                }
            }
            else {
                new Alert(Alert.AlertType.ERROR, "❌ This Email is Not Registered !").show();
            }
        }
    }

    public void btnLogInOnAction(ActionEvent actionEvent) {
        User user = new User(txtEmailAddress.getText(), txtPasssword.getText(), type);
        if(userBo.loginRequest(user)){

        }else {
            new Alert(Alert.AlertType.ERROR, "❌ Login Failed!\n Please Select correct data!").show();
        }

    }


    public void btnCreateAccountOnAction(ActionEvent actionEvent) {
        try {
            primaryStage.close();
            URL fxmlLocation = getClass().getClassLoader().getResource("view/createAccountForm.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            CreateAccountFormController.primaryStage = stage;
        } catch (IOException e) {
        }
    }

    public void btnToogleUserType(ActionEvent actionEvent) {
        type = !type;
        btnUserType.setText(type?"Admin":"Employee");
    }
}

package org.example.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.UserBo;
import org.example.util.BoType;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResetPasswordFormController implements Initializable {
    public static Stage primaryStage;
    public static String currentEmail;
    public JFXPasswordField txtConfirmPassword;
    public JFXTextField txtOTPCode;
    public JFXPasswordField txtPassword;
    public Text txtEmail;
    private UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtEmail.setText("Email : "+currentEmail);
        sendOTP();
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
        } catch (IOException e) {}

    }


    public void btnLoginOnAction(ActionEvent actionEvent) {
        try {
            primaryStage.close();
            URL fxmlLocation = getClass().getClassLoader().getResource("view/loginPageForm.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            LoginPageFormController.primaryStage = stage;
        } catch (IOException e) {}
    }

    public void btnResetPasswordOnAction(ActionEvent actionEvent) {
    }

    public void btnConfirmOnAction(ActionEvent actionEvent) {

    }
    private void sendOTP(){
        userBo.sendOTPTo(currentEmail);
    }


}

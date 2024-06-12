package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.UserBo;
import org.example.dto.User;
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
    public JFXButton btnConfirmOtp;
    public JFXButton btnSendOtp;
    public JFXButton btnResetPassword;
    private UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtEmail.setText(currentEmail);

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
        if (!txtPassword.getText().isEmpty() && !txtConfirmPassword.getText().isEmpty()){
            if(txtPassword.getText().equals(txtConfirmPassword.getText())){
                new Alert(Alert.AlertType.INFORMATION, userBo.updatePassword(txtEmail.getText(), txtPassword.getText())).show();
                txtPassword.setText("");
                txtConfirmPassword.setText("");
            }else {
                new Alert(Alert.AlertType.ERROR, "Please Enter Same password !").show();
            }
        }
    }

    public void btnConfirmOnAction(ActionEvent actionEvent) {
        if (userBo.isEqualsOTP(Integer.parseInt(txtOTPCode.getText()))){
            btnConfirmOtp.setDisable(true);
            btnSendOtp.setDisable(true);
            txtOTPCode.setDisable(true);
            txtPassword.setDisable(false);
            txtConfirmPassword.setDisable(false);
            btnResetPassword.setDisable(false);
            new Alert(Alert.AlertType.INFORMATION, "✅ OTP Verified ! \n Now you can change the passsword.").show();
            return;
        }
        new Alert(Alert.AlertType.ERROR, "❌ OTP Verification Failed !").show();
    }
    private boolean sendOTP(){
        return userBo.sendOTPTo(currentEmail);
    }


    public void btnSendOTPOnAction(ActionEvent actionEvent) {
        if(sendOTP()){
            new Alert(Alert.AlertType.INFORMATION, "✅ OTP Send Successfully ! Check your email address").show();
            return;
        }
        new Alert(Alert.AlertType.ERROR, "❌ OTP Send Failed ! Retry after countdown.").show();
    }
}

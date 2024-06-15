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
import org.example.bo.custom.DataValidationBo;
import org.example.bo.custom.UserBo;
import org.example.util.BoType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangePasswordFormController implements Initializable {
    public static Stage primaryStage;
    public JFXPasswordField txtConfirmPassword;
    public JFXTextField txtCurrentPassword;
    public JFXPasswordField txtPassword;
    public JFXButton btnConfirm;
    public Text txtEmail;
    public static String employeeUserEmail;
    public static String adminUserEmail;
    public UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);
    public DataValidationBo dataValidationBo =BoFactory.getInstance().getBo(BoType.VALIDATE);
    public JFXButton btnUpdatePassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtEmail.setText(employeeUserEmail);

    }
    public void btnConfirmOnAction(ActionEvent actionEvent) {
        boolean isValidPassword = dataValidationBo.isValidPassword(txtCurrentPassword.getText());
        boolean isConfirmed = userBo.confirmPassword(
                employeeUserEmail!=null ? employeeUserEmail:adminUserEmail,
                txtCurrentPassword.getText()
        );
        if (isValidPassword ){
            if (isConfirmed){
                txtPassword.setDisable(false);
                txtConfirmPassword.setDisable(false);
                btnConfirm.setDisable(true);
                btnUpdatePassword.setDisable(false);
                new Alert(Alert.AlertType.INFORMATION, "Password confirmed.Now you can update password.").show();
                return;
            }
            new Alert(Alert.AlertType.ERROR, "❌ Wrong Password!").show();
            return;
        }
        new Alert(Alert.AlertType.ERROR, "❌ Wrong password pattern!").show();
    }
    public void btnResetPasswordOnAction(ActionEvent actionEvent) {
        try {
            primaryStage.close();
            ResetPasswordFormController.currentEmail = employeeUserEmail;
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
    public void btndashboardOnAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        if(employeeUserEmail != null){
            try {
                primaryStage.close();
                URL fxmlLocation = getClass().getClassLoader().getResource("view/userDashboardForm.fxml");
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                Parent parent = loader.load();
                stage.setScene(new Scene(parent));
                stage.show();
                UserDashboardFormController.primaryStage = stage;
            } catch (IOException e) {
            }
        }else {
            try {
                primaryStage.close();
                URL fxmlLocation = getClass().getClassLoader().getResource("view/adminDashboard.fxml");
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                Parent parent = loader.load();
                stage.setScene(new Scene(parent));
                stage.show();
                AdminDashboardFormController.primaryStage = stage;
            } catch (IOException e) {
            }
        }
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
        } catch (IOException e) {
        }
    }
    public void btnUpdatePasswordOnAction(ActionEvent actionEvent) {
        boolean allFieldsNotEmpty = dataValidationBo.isAllFieldsNotEmpty(txtPassword.getText(), txtConfirmPassword.getText());
        boolean validPassword = dataValidationBo.isValidPassword(txtPassword.getText());
        if (allFieldsNotEmpty && validPassword){
            String result = userBo.updatePassword(
                    employeeUserEmail != null ? employeeUserEmail : adminUserEmail,
                    txtPassword.getText()
            );
            btnConfirm.setDisable(false);
            txtCurrentPassword.setText("");
            txtConfirmPassword.setText("");
            txtPassword.setText("");
            txtConfirmPassword.setDisable(true);
            txtPassword.setDisable(true);
            new Alert(Alert.AlertType.INFORMATION, result).show();
            return;
        }
        new Alert(Alert.AlertType.INFORMATION, "Enter correct password pattern !").show();
    }
}

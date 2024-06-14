package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.SuperBo;
import org.example.bo.custom.DataValidationBo;
import org.example.util.BoType;

import java.io.IOException;
import java.net.URL;

public class UserRegistrationFormController {
    public static Stage primaryStage;
    public JFXTextField txtFirstName;
    public JFXPasswordField txtLastName;
    public JFXPasswordField txtNicNo;
    public JFXPasswordField txtMobileNumber;
    public JFXComboBox cmbProvince;
    public JFXComboBox cmbDistrict;
    public JFXTextField txtEmail;
    public JFXButton btnResetPassword;
    public Text txtUserId;
    public Text txtEmailValidation;
    public Text txtMobileNumberValidation;
    private DataValidationBo dataValidationBo = BoFactory.getInstance().getBo(BoType.VALIDATE);
    private boolean isValidEmail;
    private boolean isValidMobileNo;

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

    public void btnRegisterOnAction(ActionEvent actionEvent) {
        boolean allFieldsNotEmpty = dataValidationBo.isAllFieldsNotEmpty(
                txtFirstName.getText(),
                txtLastName.getText(),
                txtNicNo.getText()
        );

    }

    public void btnDashboardOnAction(ActionEvent actionEvent) {
    }

    public void btnSearchUserOnAction(ActionEvent actionEvent) {
    }

    public void btnUserListOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateRemoveOnAction(ActionEvent actionEvent) {
    }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        if (! dataValidationBo.isValidEmail(txtEmail.getText())){
            txtEmailValidation.setVisible(true);
            isValidEmail=false;
            return;
        }
        txtEmailValidation.setVisible(false);
        isValidEmail=true;
    }

    public void txtMobileNoOnKeyReleased(KeyEvent keyEvent) {
        if (! dataValidationBo.isValidMobileNumber(txtMobileNumber.getText())){
            txtMobileNumberValidation.setVisible(true);
            isValidMobileNo=false;
            return;
        }
        txtMobileNumberValidation.setVisible(false);
        isValidMobileNo=true;
    }

}

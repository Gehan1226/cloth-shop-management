package org.example.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.UserBo;
import org.example.controller.AccountManage.ChangePasswordFormController;
import org.example.controller.HomePageFormController;
import org.example.util.BoType;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminProfilePageFormcontroller implements Initializable {
    public static Stage primaryStage;
    public static String adminEmail;
    public JFXTextField txtEmail;
    public JFXButton btnSaveChanges;
    public UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtEmail.setText(adminEmail);
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

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        btnSaveChanges.setDisable(false);
    }

    public void btnSaveChangesOnAction(ActionEvent actionEvent) {
        String result = userBo.updateEmail(adminEmail, txtEmail.getText());
        new Alert(Alert.AlertType.INFORMATION, result).show();
        if (result.endsWith("Successfully !")){
            adminEmail = txtEmail.getText();
            txtEmail.setText(adminEmail);
        }
    }

    public void btnChangePasswordOnAction(ActionEvent actionEvent) {
        try {
            primaryStage.close();
            ChangePasswordFormController.employeeUserEmail = adminEmail;
            URL fxmlLocation = getClass().getClassLoader().getResource("view/changePasswordForm.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            ChangePasswordFormController.primaryStage = stage;
        } catch (IOException e) {
        }
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) {
        try {
            primaryStage.close();
            URL fxmlLocation = getClass().getClassLoader().getResource("view/adminDashboard.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            AdminDashboardFormController.primaryStage = stage;
        } catch (IOException e) {
        }
    }
}

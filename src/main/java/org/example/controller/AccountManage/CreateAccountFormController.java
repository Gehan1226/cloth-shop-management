package org.example.controller.AccountManage;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.DataValidationBo;
import org.example.bo.custom.UserBo;
import org.example.controller.HomePageFormController;
import org.example.dto.User;
import org.example.util.BoType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateAccountFormController implements Initializable {
    public JFXTextField txtEmailAddress;
    public JFXPasswordField txtPassword;
    public JFXPasswordField txtConfirmPassword;
    public RadioButton btnIsAdmin;
    public RadioButton btnIsEmployee;
    public static Stage primaryStage;
    private static final UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);
    private DataValidationBo dataValidationBo = BoFactory.getInstance().getBo(BoType.VALIDATE);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        btnIsAdmin.setToggleGroup(toggleGroup);
        btnIsEmployee.setToggleGroup(toggleGroup);
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

    public void btnCreateAccountOnAction(ActionEvent actionEvent) {
        boolean allFieldsNotEmpty = dataValidationBo.isAllFieldsNotEmpty(
                txtConfirmPassword.getText(),
                txtPassword.getText(),
                txtEmailAddress.getText());
        boolean isSamePassword = txtPassword.getText().equals(txtConfirmPassword.getText());
        boolean isValidPassword = dataValidationBo.isValidPassword(txtPassword.getText());

        if (allFieldsNotEmpty){
            if(isSamePassword && isValidPassword){
                String result = userBo.saveUser(new User(txtEmailAddress.getText(),txtPassword.getText(),(btnIsAdmin.isSelected())));
                if (result.endsWith("Successfully!")){
                    txtEmailAddress.setText("");
                    txtPassword.setText("");
                    txtConfirmPassword.setText("");
                    new Alert(Alert.AlertType.INFORMATION, "✅ "+result).show();
                }
                else {
                    new Alert(Alert.AlertType.ERROR, "❌ "+result).show();
                }
            }
            else {
                new Alert(Alert.AlertType.ERROR, "Please Enter Correct password !").show();
            }
        }
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
}

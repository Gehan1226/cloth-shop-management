package org.example.controller;

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
import org.example.bo.custom.EmployeeBo;
import org.example.bo.custom.UserBo;
import org.example.dao.custom.EmployeeDao;
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
    private static EmployeeBo employeeBo = BoFactory.getInstance().getBo(BoType.EMPLOYEE);
    private static UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

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
        if (!txtEmailAddress.getText().isEmpty() && !txtPassword.getText().isEmpty() && !txtConfirmPassword.getText().isEmpty()){
            if(txtPassword.getText().equals(txtConfirmPassword.getText())){
                userBo.saveUser(new User(txtEmailAddress.getText(),txtPassword.getText(),(btnIsAdmin.isSelected())));
            }
            else {
                new Alert(Alert.AlertType.ERROR, "Please Enter Same password !").show();
            }
        }

    }


}

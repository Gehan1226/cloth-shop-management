package org.example.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.EmployeeBo;
import org.example.dto.Employee;
import org.example.util.BoType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeProfilePageFormController implements Initializable {
    public static Stage primaryStage;
    public JFXTextField txtName;
    public JFXTextField txtNic;
    public JFXTextField txtMobileNo;
    public JFXTextField txtEmail;
    public JFXTextField txtProvince;
    public JFXTextField txtDistrict;
    public Text txtEmpId;
    public static String employeeUserEmail;
    public EmployeeBo employeeBo = BoFactory.getInstance().getBo(BoType.EMPLOYEE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Employee employee = employeeBo.retrieveByEmail(employeeUserEmail);
        txtEmpId.setText(employee.getEmpID());
        txtName.setText(employee.getFirstName()+" "+employee.getLastName());
        txtEmail.setText(employee.getEmail());
        txtNic.setText(employee.getNic());
        txtProvince.setText(employee.getProvince());
        txtDistrict.setText(employee.getDistrict());
        txtMobileNo.setText(employee.getMobileNumber());
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

    public void btnChangePasswordOnAction(ActionEvent actionEvent) {
        try {
            primaryStage.close();
            ChangePasswordFormController.employeeUserEmail = employeeUserEmail;
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
            URL fxmlLocation = getClass().getClassLoader().getResource("view/userDashboardForm.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            UserDashboardFormController.primaryStage = stage;
        } catch (IOException e) {
        }
    }


}

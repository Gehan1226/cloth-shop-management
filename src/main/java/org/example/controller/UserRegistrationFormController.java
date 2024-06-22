package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.DataValidationBo;
import org.example.bo.custom.EmployeeBo;
import org.example.dto.Employee;
import org.example.util.BoType;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserRegistrationFormController implements Initializable {
    public static Stage primaryStage;
    public JFXTextField txtFirstName;
    public JFXTextField txtLastName;
    public JFXTextField txtNicNo;
    public JFXTextField txtMobileNumber;
    public JFXComboBox cmbProvince;
    public JFXComboBox cmbDistrict;
    public JFXTextField txtEmail;
    public JFXButton btnResetPassword;
    public Text txtEmailValidation;
    public Text txtMobileNumberValidation;
    public Text txtEmpID;
    private final DataValidationBo dataValidationBo = BoFactory.getInstance().getBo(BoType.VALIDATE);
    private final EmployeeBo employeeBo = BoFactory.getInstance().getBo(BoType.EMPLOYEE);
    private boolean isValidEmail;
    private boolean isValidMobileNo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtEmpID.setText(employeeBo.genarateEmployeeID());
        String[] provinceArr = {"Central", "Eastern", "North Central", "Northern", "North West", "Sabaragamuwa", "Southern", "Uva", "Western"};
        String[] districtArr = {
                "Ampara", "Anuradhapura", "Badulla", "Batticaloa", "Colombo",
                "Galle", "Gampaha", "Hambantota", "Jaffna", "Kalutara",
                "Kandy", "Kegalle", "Kilinochchi", "Kurunegala", "Mannar",
                "Matale", "Matara", "Monaragala", "Mullaitivu", "Nuwara Eliya",
                "Polonnaruwa", "Puttalam", "Ratnapura", "Trincomalee", "Vavuniya"
        };
        cmbProvince.getItems().addAll(provinceArr);
        cmbDistrict.getItems().addAll(districtArr);
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

    public void btnRegisterOnAction(ActionEvent actionEvent) {
        boolean allFieldsNotEmpty = dataValidationBo.isAllFieldsNotEmpty(
                txtFirstName.getText(),
                txtLastName.getText(),
                txtNicNo.getText()
        );
        boolean isSelectedcmbBoxes = !cmbProvince.getSelectionModel().isEmpty() && !cmbDistrict.getSelectionModel().isEmpty();
        if (allFieldsNotEmpty && isValidEmail && isValidMobileNo && isSelectedcmbBoxes) {
            Employee employee = new Employee(
                    txtEmpID.getText(),
                    txtFirstName.getText(),
                    txtLastName.getText(),
                    txtNicNo.getText(),
                    txtMobileNumber.getText(),
                    cmbProvince.getSelectionModel().getSelectedItem().toString(),
                    cmbDistrict.getSelectionModel().getSelectedItem().toString(),
                    txtEmail.getText(),
                    new ArrayList<>()
            );
            if (employeeBo.save(employee)) {
                txtEmpID.setText(employeeBo.genarateEmployeeID());
                txtFirstName.setText("");
                txtLastName.setText("");
                txtMobileNumber.setText("");
                txtEmail.setText("");
                txtNicNo.setText("");
                new Alert(Alert.AlertType.INFORMATION, "✅ User Registration Successfully !").show();
                return;
            }
            new Alert(Alert.AlertType.ERROR, "❌ User Registration Failed!").show();
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
    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        if (!dataValidationBo.isValidEmail(txtEmail.getText())) {
            txtEmailValidation.setVisible(true);
            isValidEmail = false;
            return;
        }
        txtEmailValidation.setVisible(false);
        isValidEmail = true;
    }

    public void txtMobileNoOnKeyReleased(KeyEvent keyEvent) {
        if (!dataValidationBo.isValidMobileNumber(txtMobileNumber.getText())) {
            txtMobileNumberValidation.setVisible(true);
            isValidMobileNo = false;
            return;
        }
        txtMobileNumberValidation.setVisible(false);
        isValidMobileNo = true;
    }

    public void btnUpdateRemoveOnAction(ActionEvent actionEvent) {
        try {
            primaryStage.close();
            URL fxmlLocation = getClass().getClassLoader().getResource("view/employeeUpdateRemovePageForm.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            EmployeeUpdateRemoveFormController.primaryStage = stage;
        } catch (IOException e) {
        }
    }
}

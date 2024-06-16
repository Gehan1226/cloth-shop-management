package org.example.controller;

import com.jfoenix.controls.JFXComboBox;
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
import org.example.bo.custom.EmployeeBo;
import org.example.dto.Employee;
import org.example.util.BoType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeUpdateRemoveFormController implements Initializable {

    public JFXTextField txtFirstName;
    public JFXTextField txtEmail;
    public JFXTextField txtLastName;
    public JFXTextField txtNicNo;
    public JFXTextField txtMobileNumber;
    public JFXTextField txtEmpID;
    public JFXComboBox cmbProvince;
    public JFXComboBox cmbDistrict;
    private DataValidationBo dataValidationBo = BoFactory.getInstance().getBo(BoType.VALIDATE);
    private EmployeeBo employeeBo = BoFactory.getInstance().getBo(BoType.EMPLOYEE);
    public static Stage primaryStage;
    private String empId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] provinceArr = {"Central","Eastern","North Central","Northern","North West","Sabaragamuwa","Southern","Uva","Western"};
        String[] districtArr = {
                "Ampara","Anuradhapura","Badulla","Batticaloa","Colombo",
                "Galle","Gampaha","Hambantota","Jaffna","Kalutara",
                "Kandy","Kegalle","Kilinochchi","Kurunegala","Mannar",
                "Matale","Matara","Monaragala","Mullaitivu","Nuwara Eliya",
                "Polonnaruwa","Puttalam","Ratnapura","Trincomalee","Vavuniya"
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

    public void btnSaveOnAction(ActionEvent actionEvent) {
        boolean allFieldsNotEmpty = dataValidationBo.isAllFieldsNotEmpty(
                empId,
                txtFirstName.getText(),
                txtLastName.getText(),
                txtEmail.getText(),
                txtNicNo.getText(),
                txtMobileNumber.getText()
        );
        if(allFieldsNotEmpty){
            Employee employee = new Employee(
                    empId,
                    txtFirstName.getText(),
                    txtLastName.getText(),
                    txtEmail.getText(),
                    txtNicNo.getText(),
                    txtMobileNumber.getText(),
                    cmbProvince.getSelectionModel().getSelectedItem().toString(),
                    cmbDistrict.getSelectionModel().getSelectedItem().toString()
            );
            if (employeeBo.replace(employee)){
                new Alert(Alert.AlertType.INFORMATION, "✅ Data Saved!").show();
            }
            else {
                new Alert(Alert.AlertType.ERROR, "❌ Data Save Failed!").show();
            }
            return;
        }
        new Alert(Alert.AlertType.ERROR, "❌ Enter valid data !").show();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
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

    public void btnSearchOnAction(ActionEvent actionEvent) {
        if (dataValidationBo.isValidEmpID(txtEmpID.getText())){
            Employee employee = employeeBo.retrieveById(txtEmpID.getText());
            txtFirstName.setText(employee.getFirstName());
            txtLastName.setText(employee.getLastName());
            txtEmail.setText(employee.getEmail());
            txtNicNo.setText(employee.getNic());
            txtMobileNumber.setText(employee.getMobileNumber());
            cmbProvince.getSelectionModel().select(employee.getProvince());
            cmbDistrict.getSelectionModel().select(employee.getDistrict());
            empId = employee.getEmpID();
            new Alert(Alert.AlertType.INFORMATION, "✅ Data Founded! Now you can update data.").show();
            return;
        }
        new Alert(Alert.AlertType.ERROR, "❌ Wrong ID").show();
    }

    public void btnSearchUserOnAction(ActionEvent actionEvent) {
    }

    public void btnUserListOnAction(ActionEvent actionEvent) {
    }
}

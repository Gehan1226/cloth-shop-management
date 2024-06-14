package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import org.example.bo.BoFactory;
import org.example.bo.custom.EmployeeBo;
import org.example.dto.Employee;
import org.example.util.BoType;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfilePageFormController implements Initializable {
    public JFXTextField txtName;
    public JFXTextField txtNic;
    public JFXTextField txtMobileNo;
    public JFXTextField txtEmail;
    public JFXTextField txtProvince;
    public JFXTextField txtDistrict;
    public JFXButton btnResetPassword1;
    public EmployeeBo employeeBo = BoFactory.getInstance().getBo(BoType.EMPLOYEE);
    public static Employee employee;
    public Text txtEmpId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        txtEmpId.setText(employee.getEmpId());
//        txtName.setText(employee.getFirstName()+" "+employee.getLastName());
//        txtEmail.setText(employee.getEmail());
//        txtNic.setText(employee.getNic());
//        txtProvince.setText(employee.getProvince());
//        txtDistrict.setText(employee.getDistrict());
//        txtMobileNo.setText(employee.getMobileNumber());
        employeeBo.genarateEmployeeID();
    }
    public void btnMainmenuOnAction(ActionEvent actionEvent) {
    }

    public void btnChangePasswordOnAction(ActionEvent actionEvent) {
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) {
    }


}

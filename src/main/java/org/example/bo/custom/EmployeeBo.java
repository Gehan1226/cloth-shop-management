package org.example.bo.custom;

import org.example.bo.SuperBo;
import org.example.dto.Employee;

public interface EmployeeBo extends SuperBo {
     boolean isEmployee(String email);
     boolean save(Employee employee);
     String genarateEmployeeID();


}

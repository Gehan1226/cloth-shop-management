package org.example.bo.custom;

import org.example.bo.SuperBo;
import org.example.dto.Employee;

public interface EmployeeBo extends SuperBo {
    public boolean isEmployee(String email);
    public boolean save(Employee employee);
}

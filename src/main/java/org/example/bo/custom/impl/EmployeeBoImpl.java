package org.example.bo.custom.impl;

import org.example.bo.custom.EmployeeBo;
import org.example.dao.Daofactory;
import org.example.dao.custom.EmployeeDao;
import org.example.dto.Employee;
import org.example.entity.EmployeeEntity;
import org.example.util.DaoType;

import java.util.List;

public class EmployeeBoImpl implements EmployeeBo {
    private static EmployeeDao employeeDao = Daofactory.getInstance().getDao(DaoType.EMPLOYEE);
    @Override
    public Boolean isEmployee(String email) {
        List<Employee> employeeEntities = employeeDao.retrieveEmployee(email);
        return employeeEntities.size() != 0 ? true: false;
    }
}

package org.example.bo.custom.impl;

import org.example.bo.custom.EmployeeBo;
import org.example.dao.Daofactory;
import org.example.dao.custom.EmployeeDao;
import org.example.dto.Employee;
import org.example.entity.EmployeeEntity;
import org.example.util.DaoType;
import org.modelmapper.ModelMapper;

import java.util.List;

public class EmployeeBoImpl implements EmployeeBo {
    private static final EmployeeDao employeeDao = Daofactory.getInstance().getDao(DaoType.EMPLOYEE);
    @Override
    public boolean isEmployee(String email) {
        List<Employee> employeeEntities = employeeDao.retrieveByEmail(email);
        return !employeeEntities.isEmpty();
    }
    @Override
    public boolean save(Employee employee) {
        return employeeDao.save(new ModelMapper().map(employee,EmployeeEntity.class));
    }
    @Override
    public String genarateEmployeeID() {
        Employee employee = employeeDao.retrieveLastRow();
        if (employee != null){
            String empID = employee.getEmpID();
            int empNumber = Integer.parseInt(empID.substring(1)) + 1;
            return "E"+empNumber;
        }
        return "E1";
    }



}

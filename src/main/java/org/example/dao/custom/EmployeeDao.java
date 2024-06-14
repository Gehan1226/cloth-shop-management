package org.example.dao.custom;

import org.example.dao.CrudDao;
import org.example.dao.SuperDao;
import org.example.dto.Employee;
import org.example.entity.EmployeeEntity;

import java.util.List;

public interface EmployeeDao extends CrudDao<EmployeeEntity> {
    List<Employee> retrieveByEmail(String email);
    Employee retrieveById(String empId);
    Employee retrieveLastRow();
}

package org.example.dao.custom;

import org.example.dao.SuperDao;
import org.example.dto.Employee;
import org.example.entity.EmployeeEntity;

import java.util.List;

public interface EmployeeDao extends SuperDao {
    List<Employee> retrieveEmployee(String email);
}

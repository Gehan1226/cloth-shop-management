package org.example.dao.custom.impl;

import org.example.dao.custom.EmployeeDao;
import org.example.dto.Employee;
import org.example.entity.EmployeeEntity;
import org.example.entity.UserEntity;
import org.example.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {
    @Override
    public List<Employee> retrieveEmployee(String email) {
        try{
            SessionFactory sessionFactory= HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();
            Transaction transaction = session.beginTransaction();

            Query<EmployeeEntity> query = session.createQuery("from EmployeeEntity where email = :email", EmployeeEntity.class);
            query.setParameter("email", email);
            List<EmployeeEntity> employeeEntityList = query.getResultList();
            transaction.commit();
            session.close();

            List<Employee> employeeList = new ArrayList<>();
            for (int i=0;i< employeeList.size();i++){
                employeeList.add(new ModelMapper().map(employeeEntityList.get(i), Employee.class));
            }
            return employeeList;
        }catch (HibernateException e){
            throw new RuntimeException("Error executing Hibernate query", e);
        }

    }
}

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

    private Session session;
    private Transaction transaction;

    private void beginSession() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        transaction = session.beginTransaction();
    }
    private void closeSession() {
        if (transaction != null && transaction.isActive()) {
            transaction.commit();
        }
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    @Override
    public List<Employee> retrieveByEmail(String email) {
        try{
            beginSession();
            Query<EmployeeEntity> query = session.createQuery("from EmployeeEntity where email = :email", EmployeeEntity.class);
            query.setParameter("email", email);
            List<EmployeeEntity> employeeEntityList = query.getResultList();
            List<Employee> employeeList = new ArrayList<>();
            employeeList.add(new ModelMapper().map(employeeEntityList.get(0), Employee.class));
            return employeeList;
        }catch (HibernateException e){
            throw new RuntimeException("Error executing Hibernate query", e);
        }finally {
            closeSession();
        }
    }
    @Override
    public boolean save(EmployeeEntity dto) {
        try {
            beginSession();
            session.persist(dto);
        } catch (HibernateException e) {
            return false;
        } finally {
            closeSession();
        }
        return true;
    }
    @Override
    public Employee retrieveById(String id){
        EmployeeEntity employeeEntity;
        try {
            beginSession();
            employeeEntity = session.get(EmployeeEntity.class, id);
        }catch (HibernateException e) {
            throw new RuntimeException("Error executing Hibernate query", e);
        } finally {
            closeSession();
        }
        return new ModelMapper().map(employeeEntity, Employee.class);
    }
    @Override
    public Employee retrieveLastRow() {
        EmployeeEntity employeeEntity;
        try {
            beginSession();
            Query<EmployeeEntity> query = session.createQuery("from EmployeeEntity order by id DESC", EmployeeEntity.class);
            query.setMaxResults(1);
            employeeEntity= (EmployeeEntity) query.uniqueResult();
        }catch (HibernateException e) {
            throw new RuntimeException("Error executing Hibernate query", e);
        } finally {
            closeSession();
        }

        return employeeEntity != null ? (new ModelMapper().map(employeeEntity,Employee.class)) : null;
    }


}

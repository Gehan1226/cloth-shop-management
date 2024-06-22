package org.example.dao.custom.impl;

import org.example.dao.custom.EmployeeDao;
import org.example.dto.Employee;
import org.example.entity.EmployeeEntity;
import org.example.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
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
        List<Employee> employeeList = new ArrayList<>();
        try{
            beginSession();
            Query<EmployeeEntity> query = session.createQuery("from EmployeeEntity where email = :email", EmployeeEntity.class);
            query.setParameter("email", email);
            List<EmployeeEntity> employeeEntityList = query.getResultList();
            if (!employeeEntityList.isEmpty()){
                employeeList.add(new ModelMapper().map(employeeEntityList.get(0), Employee.class));
            }
        }catch (HibernateException e){
            throw new HibernateException("Error in retrieveByEmail method", e);
        }finally {
            closeSession();
        }
        return employeeList;
    }
    @Override
    public boolean save(EmployeeEntity dto) {
        try {
            beginSession();
            session.persist(dto);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        } finally {
            closeSession();
        }
        return true;
    }
    @Override
    public Employee retrieve(String id){
        Employee employee = null;
        try {
            beginSession();
            EmployeeEntity employeeEntity = session.get(EmployeeEntity.class, id);
            if (employeeEntity != null){
                employee = new ModelMapper().map(employeeEntity, Employee.class);
            }
        }catch (HibernateException e) {
            throw new HibernateException("Error retrieving employee with id: " + id, e);
        } finally {
            closeSession();
        }
        return employee;
    }
    @Override
    public Employee retrieveLastRow() {
        Employee employee;
        try {
            beginSession();
            Query<EmployeeEntity> query = session.createQuery("from EmployeeEntity order by id DESC", EmployeeEntity.class);
            query.setMaxResults(1);
            EmployeeEntity employeeEntity= query.uniqueResult();
            employee = new ModelMapper().map(employeeEntity, Employee.class);
        }catch (HibernateException e) {
            throw new HibernateException("Error retrieveLastRow method ", e);
        } finally {
            closeSession();
        }
        return employee;
    }
    @Override
    public boolean update(EmployeeEntity employeeEntity){
        try {
            beginSession();
            session.merge(employeeEntity);
            transaction.commit();
            return true;
        }catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        } finally {
            closeSession();
        }
    }
    @Override
    public boolean delete(String empID) {
        try {
            beginSession();
            MutationQuery mutationQuery = session.createMutationQuery("delete from EmployeeEntity where empID = :primaryKeyValue");
            mutationQuery.setParameter("primaryKeyValue",empID);
            mutationQuery.executeUpdate();
        }catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        } finally {
            closeSession();
        }
        return true;
    }
}

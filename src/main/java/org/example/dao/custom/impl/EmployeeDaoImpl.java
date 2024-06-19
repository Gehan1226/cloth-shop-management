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
            employeeList.add(new ModelMapper().map(employeeEntityList.get(0), Employee.class));
        }catch (HibernateException e){
            throw new RuntimeException("Error executing Hibernate query", e);
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
            return false;
        } finally {
            closeSession();
        }
        return true;
    }
    @Override
    public Employee retrieve(String id){
        EmployeeEntity employeeEntity;
        try {
            beginSession();
            employeeEntity = session.get(EmployeeEntity.class, id);
        }catch (HibernateException e) {
            throw new RuntimeException("Error executing Hibernate query", e);
        } finally {
            closeSession();
        }
        return employeeEntity != null ? new ModelMapper().map(employeeEntity, Employee.class):null;
    }
    @Override
    public Employee retrieveLastRow() {
        EmployeeEntity employeeEntity;
        try {
            beginSession();
            Query<EmployeeEntity> query = session.createQuery("from EmployeeEntity order by id DESC", EmployeeEntity.class);
            query.setMaxResults(1);
            employeeEntity= query.uniqueResult();
        }catch (HibernateException e) {
            throw new RuntimeException("Error executing Hibernate query", e);
        } finally {
            closeSession();
        }
        return employeeEntity != null ? (new ModelMapper().map(employeeEntity,Employee.class)) : null;
    }
    @Override
    public boolean update(EmployeeEntity employeeEntity){
        String hql = "update EmployeeEntity " +
                     "set firstName = :value1," +
                        " lastName = :value2," +
                        " nic = :value3," +
                        " mobileNumber = :value4," +
                         "province = :value5," +
                        "district = :value6 ," +
                        "email = :value7 " +
                     "where empID = :primaryKeyValue";
        try {
            beginSession();
            MutationQuery mutationQuery = session.createMutationQuery(hql);
            mutationQuery.setParameter("value1",employeeEntity.getFirstName());
            mutationQuery.setParameter("value2",employeeEntity.getLastName());
            mutationQuery.setParameter("value3",employeeEntity.getNic());
            mutationQuery.setParameter("value4",employeeEntity.getMobileNumber());
            mutationQuery.setParameter("value5",employeeEntity.getProvince());
            mutationQuery.setParameter("value6",employeeEntity.getDistrict());
            mutationQuery.setParameter("value7",employeeEntity.getEmail());
            mutationQuery.setParameter("primaryKeyValue",employeeEntity.getEmpID());
            mutationQuery.executeUpdate();
        }catch (HibernateException e) {
            return false;
        } finally {
            closeSession();
        }
        return true;
    }
    @Override
    public boolean delete(String empID) {
        try {
            beginSession();
            MutationQuery mutationQuery = session.createMutationQuery("delete from EmployeeEntity where empID = :primaryKeyValue");
            mutationQuery.setParameter("primaryKeyValue",empID);
            mutationQuery.executeUpdate();
        }catch (HibernateException e) {
            return false;
        } finally {
            closeSession();
        }
        return true;
    }
}

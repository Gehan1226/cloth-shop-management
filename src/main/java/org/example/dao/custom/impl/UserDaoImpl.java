package org.example.dao.custom.impl;

import org.example.dao.custom.UserDao;
import org.example.dto.Employee;
import org.example.dto.User;
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

public class UserDaoImpl implements UserDao {
    @Override
    public List<User> hasAdmin() {
        try{
            SessionFactory sessionFactory= HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();
            Transaction transaction = session.beginTransaction();

            Query<UserEntity> query = session.createQuery("from UserEntity where isAdmin = :boolean", UserEntity.class);
            query.setParameter("boolean", true);
            List<UserEntity> userEntities = query.getResultList();
            transaction.commit();
            session.close();

            List<User> userList = new ArrayList<>();
            for (int i=0;i< userEntities.size();i++){
                userList.add(new ModelMapper().map(userEntities.get(i), User.class));
            }
            return userList;
        }catch (HibernateException e){
            throw new RuntimeException("Error executing Hibernate query", e);
        }

    }

    @Override
    public boolean save(UserEntity entity){
        try{
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            session.close();
        }catch (HibernateException e){
            throw new RuntimeException("Error executing Hibernate query", e);
        }
        return true;
    }
}

package org.example.dao.custom.impl;

import org.example.dao.custom.UserDao;
import org.example.dto.User;
import org.example.entity.UserEntity;
import org.example.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
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
    public List<User> hasAdmin() {
        List<User> userList = new ArrayList<>();
        try {
            beginSession();
            Query<UserEntity> query = session.createQuery("from UserEntity where isAdmin = :boolean", UserEntity.class);
            query.setParameter("boolean", true);
            List<UserEntity> userEntities = query.getResultList();

            for (int i = 0; i < userEntities.size(); i++) {
                userList.add(new ModelMapper().map(userEntities.get(i), User.class));
            }
        } catch (HibernateException e) {
            throw new RuntimeException("Error executing Hibernate query", e);
        } finally {
            closeSession();
        }
        return userList;
    }
    @Override
    public boolean save(UserEntity entity) {
        try {
            beginSession();
            session.persist(entity);
        } catch (HibernateException e) {
            return false;
        } finally {
            closeSession();
        }
        return true;
    }
    @Override
    public List<User> retrieveUser(String email) {
        List<User> userList = new ArrayList<>();
        try {
            beginSession();
            Query<UserEntity> query = session.createQuery("from UserEntity where email = :email", UserEntity.class);
            query.setParameter("email", email);
            List<UserEntity> userEntityList = query.getResultList();

            for (int i = 0; i < userEntityList.size(); i++) {
                userList.add(new ModelMapper().map(userEntityList.get(i), User.class));
            }
        } catch (HibernateException e) {
            throw new RuntimeException("Error executing Hibernate query", e);
        }finally {
            closeSession();
        }
        return userList;
    }
    @Override
    public boolean updateUserPassword(String email, String password) {
        try {
            beginSession();
            MutationQuery mutationQuery = session.createMutationQuery("update UserEntity set password=:password where email=:email");
            mutationQuery.setParameter("password", password);
            mutationQuery.setParameter("email", email);
            mutationQuery.executeUpdate();
        }catch (HibernateException e) {
            return false;
        }finally {
            closeSession();
        }
        return true;
    }
    @Override
    public boolean updateuserEmail(String oldEmail, String newEmail) {
        try {
            beginSession();
            MutationQuery mutationQuery = session.createMutationQuery("update UserEntity set email=:newEmail where email=:oldEmail");
            mutationQuery.setParameter("newEmail", newEmail);
            mutationQuery.setParameter("oldEmail", oldEmail);
            mutationQuery.executeUpdate();
        }catch (HibernateException e) {
            return false;
        }finally {
            closeSession();
        }
        return true;
    }

}

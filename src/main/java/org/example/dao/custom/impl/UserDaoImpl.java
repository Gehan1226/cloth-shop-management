package org.example.dao.custom.impl;

import org.example.dao.custom.UserDao;
import org.example.entity.UserEntity;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean save(UserEntity entity){
        try{
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            session.close();
        }catch (RuntimeException e){

        }
        return true;
    }
}

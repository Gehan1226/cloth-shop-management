package org.example.dao.custom.impl;

import org.example.dao.custom.UserDao;
import org.example.entity.UserEntity;
import org.example.util.HibernateUtil;
import org.hibernate.Session;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean save(UserEntity entity) {
        Session session = HibernateUtil.getSession("UserEntity");
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }
}

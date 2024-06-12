package org.example.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private HibernateUtil(){}

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration config = new Configuration();
            config.configure("hibernate.cfg.xml");
            return config.buildSessionFactory();
        }catch (Exception e){
            throw new RuntimeException("Error creating SessionFactory", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }



}

package org.example.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private HibernateUtil(){}

    private static SessionFactory buildSessionFactory() {
        Configuration config=new Configuration();
        config.configure("hibernate.cfg.xml");
        return config.buildSessionFactory();
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}

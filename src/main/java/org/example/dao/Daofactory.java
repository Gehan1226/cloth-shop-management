package org.example.dao;

import org.example.dao.custom.impl.EmployeeDaoImpl;
import org.example.dao.custom.impl.UserDaoImpl;
import org.example.util.DaoType;

public class Daofactory {

    private static Daofactory instance;
    private Daofactory(){}

    public static Daofactory getInstance(){
        return instance != null ? instance: (instance = new Daofactory());
    }
    public <T extends SuperDao>T getDao(DaoType type){
        switch (type){
            case USER:return (T) new UserDaoImpl();
            case EMPLOYEE:return (T) new EmployeeDaoImpl();
        }
        return null;
    }
}

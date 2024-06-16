package org.example.bo;

import org.example.bo.custom.impl.DataValidationBoImpl;
import org.example.bo.custom.impl.EmployeeBoImpl;
import org.example.bo.custom.impl.ItemBoImpl;
import org.example.bo.custom.impl.UserBoImpl;
import org.example.dao.custom.impl.ItemDaoImpl;
import org.example.util.BoType;

public class BoFactory {
    private static BoFactory instance;
    private BoFactory(){}

    public static BoFactory getInstance(){
        return instance != null ? instance:(instance = new BoFactory());
    }
    public <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case EMPLOYEE:return (T) new EmployeeBoImpl();
            case USER:return (T) new UserBoImpl();
            case VALIDATE:return (T) new DataValidationBoImpl();
            case ITEM:return (T) new ItemBoImpl();
        }
        return null;
    }
}


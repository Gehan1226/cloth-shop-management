package org.example.bo;

import org.example.util.BoType;

public class BoFactory {
    private static BoFactory instance;
    private BoFactory(){}

    public static BoFactory getInstance(){
        return instance != null ? instance:(instance = new BoFactory());
    }
    public <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case EMPLOYEE:return null;
            case USER:return null;
        }
        return null;
    }
}


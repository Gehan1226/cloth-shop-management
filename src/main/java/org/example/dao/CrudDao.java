package org.example.dao;

public interface CrudDao <T> extends SuperDao{
    boolean save(T dto);


}

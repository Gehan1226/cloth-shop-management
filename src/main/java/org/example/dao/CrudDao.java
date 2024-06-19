package org.example.dao;

import java.util.Optional;

public interface CrudDao <T,R> extends SuperDao{
    boolean save(T dto);
    R retrieve(String id);
    boolean update(T dto);
    boolean delete(String ID);
}

package org.example.dao.custom;

import org.example.dao.CrudDao;
import org.example.dto.User;
import org.example.entity.UserEntity;

import java.util.List;

public interface UserDao extends CrudDao<UserEntity> {
    List<User> hasAdmin();
}

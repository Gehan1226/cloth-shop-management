package org.example.dao.custom;

import org.example.dao.SuperDao;
import org.example.dto.User;
import org.example.entity.UserEntity;

import java.util.List;

public interface UserDao extends SuperDao {
    boolean save(UserEntity entity);
    List<User> hasAdmin();
    List<User> retrieveUser(String email);
    boolean updateUserPassword(String email, String password);
    boolean updateuserEmail(String oldEmail, String newEmail);
    boolean delete(String email);
}

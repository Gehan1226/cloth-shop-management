package org.example.bo.custom.impl;

import org.example.bo.custom.UserBo;
import org.example.dao.Daofactory;
import org.example.dao.custom.UserDao;
import org.example.dto.User;
import org.example.entity.UserEntity;
import org.example.util.DaoType;
import org.modelmapper.ModelMapper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserBoImpl implements UserBo {

    private UserDao userDao = Daofactory.getInstance().getDao(DaoType.USER);

    @Override
    public boolean saveUser(User dto) {
        dto.setPassword(passwordEncryption(dto.getPassword()));
        return userDao.save(new ModelMapper().map(dto, UserEntity.class));
    }

    public String passwordEncryption(String password) {
        String encryptedpassword = null;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(password.getBytes());
            byte[] bytes = m.digest();
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            encryptedpassword = s.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptedpassword;
    }
}

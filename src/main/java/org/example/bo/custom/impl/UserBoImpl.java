package org.example.bo.custom.impl;

import org.example.bo.custom.UserBo;
import org.example.dao.Daofactory;
import org.example.dao.custom.EmployeeDao;
import org.example.dao.custom.UserDao;
import org.example.dto.User;
import org.example.entity.UserEntity;
import org.example.util.DaoType;
import org.modelmapper.ModelMapper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UserBoImpl implements UserBo {
    private UserDao userDao = Daofactory.getInstance().getDao(DaoType.USER);
    private EmployeeDao employeeDao = Daofactory.getInstance().getDao(DaoType.EMPLOYEE);
    @Override
    public boolean hasAdmin() {
        List<User> users = userDao.hasAdmin();
        return users.size() != 0 ? true:false;
    }

    @Override
    public String saveUser(User dto) {
        if(!isValidEmail(dto.getEmail())){
            return "Wrong Email Pattern !";
        }
        if (employeeDao.retrieveEmployee(dto.getEmail()).isEmpty()){
            return dto.getEmail()+" is not a register email";
        }
        dto.setPassword(passwordEncryption(dto.getPassword()));
        if (dto.getIsAdmin()) {
            if(userDao.hasAdmin().isEmpty()){
                boolean value = userDao.save(new ModelMapper().map(dto, UserEntity.class));
                return value ? "Admin Account Created Successfully!":"Admin Account Create Failed !";
            }
            return "Admin User already exist!";
        }
        if(!userDao.save(new ModelMapper().map(dto, UserEntity.class))){
            return  "User Account Creation Failed";
        }
        return "User Account Creation Successfully !";
    }

    private String passwordEncryption(String password) {
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
    private boolean isValidEmail(String email){
        String regex = "^[A-Za-z0-9+_.-]+@gmail(.+)$";
        return email.matches(regex);
    }
}

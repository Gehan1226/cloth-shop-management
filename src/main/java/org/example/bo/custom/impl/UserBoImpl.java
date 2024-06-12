package org.example.bo.custom.impl;

import org.example.bo.custom.UserBo;
import org.example.dao.Daofactory;
import org.example.dao.custom.EmployeeDao;
import org.example.dao.custom.UserDao;
import org.example.dto.User;
import org.example.entity.UserEntity;
import org.example.util.DaoType;
import org.example.util.EmailUtil;
import org.modelmapper.ModelMapper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class UserBoImpl implements UserBo {
    private Integer lastOTP;
    private final UserDao userDao = Daofactory.getInstance().getDao(DaoType.USER);
    private final EmployeeDao employeeDao = Daofactory.getInstance().getDao(DaoType.EMPLOYEE);
    @Override
    public boolean isEqualsOTP(Integer otpByUser) {
        return Objects.equals(lastOTP, otpByUser);
    }
    @Override
    public boolean sendOTPTo(String email) {
        String body = "Your OTP Code - "+genarateOTP();
        return EmailUtil.sendEmail(email,body);
    }

    @Override
    public boolean loginRequest(User dto) {
        dto.setPassword(passwordEncryption(dto.getPassword()));
        List<User> users = userDao.retrieveUser(dto.getEmail());
        if(!users.isEmpty()){
            return users.get(0).equals(dto);
        }
        return false;
    }

    @Override
    public boolean hasAdmin() {
        List<User> users = userDao.hasAdmin();
        return !users.isEmpty();
    }

    @Override
    public String saveUser(User dto) {
        if(!isValidEmail(dto.getEmail())){
            return "Wrong Email Pattern !";
        }

        if(!isValidPassword(dto.getPassword())){
            return "Wrong Password pattern";
        }
        dto.setPassword(passwordEncryption(dto.getPassword()));

        if (Boolean.TRUE.equals(dto.getIsAdmin())) {
            if(!hasAdmin()){
                boolean value = userDao.save(new ModelMapper().map(dto, UserEntity.class));
                return value ? "Admin Account Created Successfully!":"Admin Account Create Failed !";
            }
            return "Admin User already exist!";
        }else {
            if (employeeDao.retrieveEmployee(dto.getEmail()).isEmpty()){
                return dto.getEmail()+" is not a register email";
            }
            if (userDao.retrieveUser(dto.getEmail()).isEmpty()) {
                userDao.save(new ModelMapper().map(dto, UserEntity.class));
                return "User Account Created Successfully!";
            }
            return  "User Account Alrady Exist !";
        }
    }

    @Override
    public boolean isUser(String email) {
        if (isValidEmail(email)){
            return !userDao.retrieveUser(email).isEmpty();
        }
        return false;
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
    private boolean isValidPassword(String password) {
        String regex = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[@#$%^&+=]).{8,}$";
        return password.matches(regex);
    }

    private Integer genarateOTP(){
        Random random = new Random(System.currentTimeMillis());
        return  (lastOTP = 10000 + random.nextInt(50000));
    }



}

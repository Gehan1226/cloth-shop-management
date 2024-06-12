package org.example.bo.custom;

import org.example.bo.SuperBo;
import org.example.dto.User;

public interface UserBo extends SuperBo {
    String saveUser(User dto) ;
    boolean hasAdmin();
    boolean loginRequest(User dto);
    boolean sendOTPTo(String email);
    boolean isUser(String email);
}

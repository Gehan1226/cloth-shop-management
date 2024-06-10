package org.example.bo.custom;

import org.example.bo.SuperBo;
import org.example.dto.User;

public interface UserBo extends SuperBo {
    boolean saveUser(User dto);
    boolean hasAdmin();
}

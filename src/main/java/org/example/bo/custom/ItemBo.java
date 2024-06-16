package org.example.bo.custom;

import org.example.bo.SuperBo;
import org.example.dao.CrudDao;
import org.example.entity.ItemEntity;

public interface ItemBo extends SuperBo {
    String genarateItemID();
}

package org.example.bo.custom;

import org.example.bo.SuperBo;
import org.example.dao.CrudDao;
import org.example.dto.Item;
import org.example.entity.ItemEntity;

import java.util.List;

public interface ItemBo extends SuperBo {
    String genarateItemID();
    boolean saveItem(Item item, List<String> supIDS);
    List<String> getAllIDSAndNames();
}

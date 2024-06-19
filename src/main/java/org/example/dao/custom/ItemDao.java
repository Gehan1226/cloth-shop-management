package org.example.dao.custom;

import org.example.dao.SuperDao;
import org.example.dto.Item;
import org.example.entity.ItemEntity;

import java.util.List;

public interface ItemDao extends SuperDao {
    Item retrieveLastRow();
    List<Item> retrieveAll();
    boolean save(ItemEntity itemEntity, List<String> supplierIDS);
    Item retrieve(String id);
    boolean update(ItemEntity itemEntity);
    boolean delete(String ID);

}

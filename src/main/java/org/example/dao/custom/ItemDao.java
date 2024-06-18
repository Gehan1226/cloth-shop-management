package org.example.dao.custom;

import org.example.dao.CrudDao;
import org.example.dao.SuperDao;
import org.example.dto.Employee;
import org.example.dto.Item;
import org.example.entity.ItemEntity;

import java.util.List;

public interface ItemDao extends SuperDao {
    Item retrieveLastRow();
    boolean save(ItemEntity itemEntity, List<String> supplierIDS);
    List<Item> retrieveAll();
    Item retrieveByID(String id);
    boolean update(ItemEntity itemEntity)
}

package org.example.dao.custom;

import org.example.dao.CrudDao;
import org.example.dto.Employee;
import org.example.dto.Item;
import org.example.entity.ItemEntity;

import java.util.List;

public interface ItemDao{
    Item retrieveLastRow();
    boolean save(ItemEntity itemEntity, List<String> supplierIDS);
}

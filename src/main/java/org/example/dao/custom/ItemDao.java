package org.example.dao.custom;

import org.example.dao.CrudDao;
import org.example.dto.Employee;
import org.example.dto.Item;
import org.example.entity.ItemEntity;

public interface ItemDao extends CrudDao<ItemEntity> {
    Item retrieveLastRow();
}

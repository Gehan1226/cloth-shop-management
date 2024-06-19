package org.example.dao.custom;

import org.example.dao.CrudDao;
import org.example.dao.SuperDao;
import org.example.dto.Employee;
import org.example.dto.Item;
import org.example.entity.ItemEntity;

import java.util.List;

public interface ItemDao extends CrudDao<ItemEntity,Item> {
    Item retrieveLastRow();
    List<Item> retrieveAll();

}

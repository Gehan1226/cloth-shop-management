package org.example.dao.custom;

import org.example.dao.SuperDao;
import org.example.dto.Item;
import org.example.dto.Order;
import org.example.entity.CustomerEntity;
import org.example.entity.OrderEntity;

import java.util.List;

public interface OrderDao extends SuperDao {
    Order retrieveLastRow();
    boolean save(OrderEntity order, List<String> itemIds, String employeeId);
}


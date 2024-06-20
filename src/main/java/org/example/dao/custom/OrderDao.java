package org.example.dao.custom;

import org.example.dao.SuperDao;
import org.example.dto.Item;
import org.example.dto.Order;

public interface OrderDao extends SuperDao {
    Order retrieveLastRow();
}


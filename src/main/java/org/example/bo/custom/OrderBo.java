package org.example.bo.custom;

import org.example.bo.SuperBo;
import org.example.dto.Order;

import java.util.List;

public interface OrderBo extends SuperBo {
    String genarateOrderID();
    boolean saveOrder(Order order, List<String> itemIds, String employeeId);
}

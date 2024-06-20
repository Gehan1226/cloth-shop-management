package org.example.bo.custom.impl;

import org.example.bo.custom.OrderBo;
import org.example.dao.Daofactory;
import org.example.dao.custom.ItemDao;
import org.example.dao.custom.OrderDao;
import org.example.dto.Order;
import org.example.util.DaoType;

public class OrderBoImpl implements OrderBo {
    private final OrderDao orderDao = Daofactory.getInstance().getDao(DaoType.ORDER);
    @Override
    public String genarateOrderID(){
        Order order = orderDao.retrieveLastRow();
        if (order!=null){
            return "O"+(Integer.parseInt(order.getOrderID().substring(1))+1);
        }
        return "O1";
    }
}

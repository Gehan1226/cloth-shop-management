package org.example.bo.custom.impl;

import org.example.bo.custom.OrderBo;
import org.example.dao.Daofactory;
import org.example.dao.custom.ItemDao;
import org.example.dao.custom.OrderDao;
import org.example.dto.Customer;
import org.example.dto.Order;
import org.example.entity.OrderEntity;
import org.example.util.DaoType;
import org.modelmapper.ModelMapper;

import java.util.List;


public class OrderBoImpl implements OrderBo {
    private final OrderDao orderDao = Daofactory.getInstance().getDao(DaoType.ORDER);
    private final ItemDao itemDao = Daofactory.getInstance().getDao(DaoType.ITEM);
    @Override
    public String genarateOrderID(){
        Order order = orderDao.retrieveLastRow();
        if (order!=null){
            return "O"+(Integer.parseInt(order.getOrderID().substring(1))+1);
        }
        return "O1";
    }
    @Override
    public boolean saveOrder(Order order, List<String> itemIds, String employeeId){
        OrderEntity orderEntity = new ModelMapper().map(order, OrderEntity.class);
        return orderDao.save(orderEntity,itemIds,employeeId);
    }
    @Override
    public Order getOrder(String orderID){
        return orderDao.retrieve(orderID);
    }
    @Override
    public boolean deleteOrder(String orderID){
        return orderDao.delete(orderID);
    }


}

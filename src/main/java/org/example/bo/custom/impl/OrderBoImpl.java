package org.example.bo.custom.impl;

import net.sf.jasperreports.engine.JRException;
import org.example.bo.custom.OrderBo;
import org.example.dao.Daofactory;
import org.example.dao.custom.ItemDao;
import org.example.dao.custom.OrderDao;
import org.example.dto.Item;
import org.example.dto.Order;
import org.example.entity.CustomerEntity;
import org.example.entity.OrderEntity;
import org.example.util.DaoType;
import org.example.util.EmailUtil;
import org.example.util.OrderReceiptGenerator;
import org.modelmapper.ModelMapper;
import java.util.ArrayList;
import java.util.List;


public class OrderBoImpl implements OrderBo {
    private final OrderDao orderDao = Daofactory.getInstance().getDao(DaoType.ORDER);
    private final ItemDao itemDao = Daofactory.getInstance().getDao(DaoType.ITEM);

    @Override
    public String genarateOrderID() {
        Order order = orderDao.retrieveLastRow();
        if (order != null) {
            return "O" + (Integer.parseInt(order.getOrderID().substring(1)) + 1);
        }
        return "O1";
    }

    @Override
    public boolean saveOrder(Order order, List<String> itemIds, String employeeId) {
        List<Item> itemList = new ArrayList<>();
        for (String id : itemIds) {
            Item item = itemDao.retrieve(id);
            itemList.add(item);
        }
        CustomerEntity customerEntity = new ModelMapper().map(order.getCustomer(), CustomerEntity.class);
        order.setCustomer(null);
        OrderEntity orderEntity = new ModelMapper().map(order, OrderEntity.class);

        if (orderDao.save(orderEntity, customerEntity, itemIds, employeeId)) {
            try {
                order.setItemList(itemList);
                OrderReceiptGenerator.generateReceipt(order);
                EmailUtil.sendEmail(order.getData().get(1), "Cloth Shop Order Receipt", "order receipt pdf", "orderReceipt.pdf");
                return true;
            } catch (JRException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    @Override
    public Order getOrder(String orderID) {
        return orderDao.retrieve(orderID);
    }

    @Override
    public boolean deleteOrder(String orderID) {
        return orderDao.delete(orderID);
    }
}

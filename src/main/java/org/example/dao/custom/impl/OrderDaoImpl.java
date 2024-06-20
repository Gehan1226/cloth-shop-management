package org.example.dao.custom.impl;

import org.example.dao.custom.OrderDao;
import org.example.dto.Item;
import org.example.dto.Order;
import org.example.entity.ItemEntity;
import org.example.entity.OrderEntity;
import org.example.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;

public class OrderDaoImpl implements OrderDao {
    private Session session;
    private Transaction transaction;
    private void beginSession() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        transaction = session.beginTransaction();
    }

    private void closeSession() {
        if (transaction != null && transaction.isActive()) {
            transaction.commit();
        }
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
    @Override
    public Order retrieveLastRow() {
        OrderEntity orderEntity;
        try {
            beginSession();
            Query<OrderEntity> query = session.createQuery("from OrderEntity order by id DESC", OrderEntity.class);
            query.setMaxResults(1);
            orderEntity = query.uniqueResult();
        } catch (HibernateException e) {
            throw new RuntimeException("Error executing Hibernate query", e);
        } finally {
            closeSession();
        }
        if (orderEntity != null) {
            orderEntity.setCustomer(null);
            orderEntity.setItemList(null);
        }
        return orderEntity != null ? (new ModelMapper().map(orderEntity, Order.class)) : null;
    }
}

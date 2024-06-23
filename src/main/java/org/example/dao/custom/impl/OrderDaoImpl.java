package org.example.dao.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.dao.custom.OrderDao;
import org.example.dto.Employee;
import org.example.dto.Item;
import org.example.dto.Order;
import org.example.entity.CustomerEntity;
import org.example.entity.EmployeeEntity;
import org.example.entity.ItemEntity;
import org.example.entity.OrderEntity;
import org.example.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

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
        Order order=null;
        try {
            beginSession();
            Query<OrderEntity> query = session.createQuery("from OrderEntity order by id DESC", OrderEntity.class);
            query.setMaxResults(1);
            OrderEntity orderEntity = query.uniqueResult();
            if (orderEntity != null) {
                orderEntity.setCustomer(null);
                orderEntity.setItemList(null);
                order = new ModelMapper().map(orderEntity, Order.class);
            }
        } catch (HibernateException e) {
            throw new HibernateException("Error executing retrieveLastRow method in orderDao", e);
        } finally {
            closeSession();
        }
        return order;
    }
    @Override
    public Order retrieve(String orderID) {
        Order order = null;
        try {
            beginSession();
            OrderEntity orderEntity = session.get(OrderEntity.class, orderID);
            if (orderEntity!=null){
                order = new ModelMapper().map(orderEntity,Order.class);
            }
        } catch (HibernateException e) {
            throw new HibernateException("Error executing retrieve method in orderDao", e);
        } finally {
            closeSession();
        }
        return order;
    }
    public boolean save(OrderEntity order, List<String> itemIds, String employeeId) {
        try {
            beginSession();
            session.persist(order.getCustomer());
            EmployeeEntity employee = session.get(EmployeeEntity.class, employeeId);
            order.setEmployee(employee);

            List<ItemEntity> items = new ArrayList<>();
            for (String itemId : itemIds) {
                ItemEntity item = session.get(ItemEntity.class, itemId);
                items.add(item);
            }
            order.setItemList(items);
            session.persist(order);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
        finally {
            closeSession();
        }
        return true;
    }
    @Override
    public boolean delete(String orderId) {
        EntityManager entityManager = HibernateUtil.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            beginSession();

            OrderEntity order = entityManager.find(OrderEntity.class, orderId);
            if (order != null) {
                entityManager.remove(order);
                entityTransaction.commit();
                return true;
            } else {
                System.out.println("Deleted");
                transaction.rollback();
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            closeSession();
        }
    }
}

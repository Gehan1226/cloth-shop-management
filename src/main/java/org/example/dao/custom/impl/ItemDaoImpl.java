package org.example.dao.custom.impl;

import org.example.bo.custom.ItemBo;
import org.example.dao.custom.ItemDao;
import org.example.dto.Employee;
import org.example.dto.Item;
import org.example.entity.EmployeeEntity;
import org.example.entity.ItemEntity;
import org.example.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;

public class ItemDaoImpl implements ItemDao {
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
    public boolean save(ItemEntity dto) {
        return false;
    }
    @Override
    public Item retrieveLastRow() {
        ItemEntity itemEntity;
        try {
            beginSession();
            Query<ItemEntity> query = session.createQuery("from ItemEntity order by id DESC", ItemEntity.class);
            query.setMaxResults(1);
            itemEntity = query.uniqueResult();
        }catch (HibernateException e) {
            throw new RuntimeException("Error executing Hibernate query", e);
        } finally {
            closeSession();
        }
        return itemEntity != null ? (new ModelMapper().map(itemEntity,Item.class)) : null;
    }
}

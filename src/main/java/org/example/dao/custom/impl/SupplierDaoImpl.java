package org.example.dao.custom.impl;

import org.example.dao.custom.SupplierDao;
import org.example.dto.Item;
import org.example.dto.Supplier;
import org.example.entity.ItemEntity;
import org.example.entity.SupplierEntity;
import org.example.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class SupplierDaoImpl implements SupplierDao {
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
    public List<Supplier> retrieveAll() {
        List<Supplier> supplierList = new ArrayList<>();
        try {
            beginSession();
            Query<SupplierEntity> query = session.createQuery("from SupplierEntity", SupplierEntity.class);
            List<SupplierEntity> resultList = query.getResultList();
            for (SupplierEntity entity : resultList) {
                supplierList.add(new ModelMapper().map(entity, Supplier.class));
            }
        } catch (HibernateException e) {
            throw new RuntimeException("Error executing Hibernate query", e);
        } finally {
            closeSession();
        }
        return supplierList;
    }
    @Override
    public Supplier retrieve(String supplierID) {
        SupplierEntity supplierEntity = null;
        try {
            beginSession();
            supplierEntity = session.get(SupplierEntity.class, supplierID);
            return new ModelMapper().map(supplierEntity, Supplier.class);
        } catch (HibernateException e) {
            throw new RuntimeException("Error executing Hibernate query", e);
        } finally {
            closeSession();
        }
    }
    @Override
    public Supplier retrieveLastRow() {
        SupplierEntity supplierEntity;
        try {
            beginSession();
            Query<SupplierEntity> query = session.createQuery("from SupplierEntity order by id DESC", SupplierEntity.class);
            query.setMaxResults(1);
            supplierEntity = query.uniqueResult();
        } catch (HibernateException e) {
            throw new RuntimeException("Error executing Hibernate query", e);
        } finally {
            closeSession();
        }
        if (supplierEntity != null) {
            supplierEntity.setItemList(null);
        }
        return supplierEntity != null ? (new ModelMapper().map(supplierEntity, Supplier.class)) : null;
    }
    @Override
    public boolean save(SupplierEntity supplierEntity) {
        try {
            beginSession();
            session.persist(supplierEntity);
        } catch (HibernateException e) {
            return false;
        } finally {
            closeSession();
        }
        return true;
    }
    @Override
    public boolean update(SupplierEntity dto) {
        return false;
    }
    @Override
    public boolean delete(String ID) {
        return false;
    }
}

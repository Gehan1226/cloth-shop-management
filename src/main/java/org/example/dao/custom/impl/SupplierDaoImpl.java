package org.example.dao.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
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
            return supplierList;
        } catch (HibernateException e) {
            throw new HibernateException("Error retrieveAll method in supplierDao", e);
        } finally {
            closeSession();
        }
    }
    @Override
    public Supplier retrieve(String supplierID) {
        Supplier supplier = null;
        try {
            beginSession();
            SupplierEntity supplierEntity = session.get(SupplierEntity.class, supplierID);
            if (supplierEntity!=null){
                supplier = new ModelMapper().map(supplierEntity, Supplier.class);
            }
            return supplier;
        } catch (HibernateException e) {
            throw new HibernateException("Error retrieve method in supplierDao ", e);
        } finally {
            closeSession();
        }
    }
    @Override
    public Supplier retrieveLastRow() {
        Supplier supplier = null;
        try {
            beginSession();
            Query<SupplierEntity> query = session.createQuery("from SupplierEntity order by id DESC", SupplierEntity.class);
            query.setMaxResults(1);
            SupplierEntity supplierEntity = query.uniqueResult();
            if (supplierEntity != null) {
                supplierEntity.setItemList(null);
                supplier = new ModelMapper().map(supplierEntity, Supplier.class);
            }
            return supplier;
        } catch (HibernateException e) {
            throw new HibernateException("Error retrieveLastRow method in supplierDao", e);
        } finally {
            closeSession();
        }
    }
    @Override
    public boolean save(SupplierEntity supplierEntity,List<String> itemIDS) {
        try {
            beginSession();
            session.persist(supplierEntity);

            if (!itemIDS.isEmpty()) {
                for (String id : itemIDS) {
                    ItemEntity itemEntity = session.get(ItemEntity.class, id);
                    if (itemEntity != null){
                        itemEntity.getSupplierList().add(supplierEntity);
                        session.merge(itemEntity);
                    }
                }
            }
            session.merge(supplierEntity);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        } finally {
            closeSession();
        }
    }
    @Override
    public boolean update(SupplierEntity dto,List<String> itemIDS) {
        try {
            beginSession();

            SupplierEntity supplierEntity = session.get(SupplierEntity.class, dto.getSupID());
            if (supplierEntity != null){
                new ModelMapper().map(dto,supplierEntity);
                for (String id : itemIDS) {
                    ItemEntity itemEntity = session.get(ItemEntity.class, id);
                    if (itemEntity != null) {
                        if (!itemEntity.getSupplierList().contains(supplierEntity)) {
                            itemEntity.getSupplierList().add(supplierEntity);
                            supplierEntity.getItemList().add(itemEntity);
                        }
                    }
                }
            }
            session.merge(supplierEntity);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        } finally {
            closeSession();
        }
    }
    @Override
    public boolean delete(String id) {
        EntityTransaction entityTransaction = null;
        try (EntityManager entityManager = HibernateUtil.createEntityManager()) {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            String hql = "DELETE FROM SupplierEntity WHERE supID = :supId";
            entityManager.createQuery(hql)
                    .setParameter("supId", id)
                    .executeUpdate();

            entityTransaction.commit();
            return true;
        } catch (HibernateException e) {
            if (entityTransaction != null && entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            e.printStackTrace();  // Consider logging the exception
            return false;
        }
    }
}

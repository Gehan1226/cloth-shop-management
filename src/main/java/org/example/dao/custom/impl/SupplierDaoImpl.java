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
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.example.movie_catalog");
    EntityManager entityManager = emf.createEntityManager();

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
    public boolean save(SupplierEntity supplierEntity,List<String> itemIDS) {
        try {
            beginSession();
            if (!itemIDS.isEmpty()) {
                for (int i = 0; i < itemIDS.size(); i++) {
                    ItemEntity itemEntity = session.get(ItemEntity.class, itemIDS.get(i));
                    supplierEntity.getItemList().add(itemEntity);
                    session.persist(supplierEntity);
                    session.persist(itemEntity);
                }
            } else {
                session.persist(supplierEntity);
            }
        } catch (HibernateException e) {
            return false;
        } finally {
            closeSession();
        }
        return true;
    }
    @Override
    public boolean update(SupplierEntity dto,List<String> itemIDS) {
        try {
            beginSession();

            SupplierEntity supplierEntity = session.get(SupplierEntity.class, dto.getSupID());
            supplierEntity.setFirstName(dto.getFirstName());
            supplierEntity.setLastName(dto.getLastName());
            supplierEntity.setEmail(dto.getEmail());
            supplierEntity.setCompany(dto.getCompany());
            supplierEntity.setMobileNumber(dto.getMobileNumber());
            supplierEntity.getItemList().clear();

            for (String id : itemIDS) {
                ItemEntity itemEntity = session.get(ItemEntity.class, id);
                if (itemEntity != null) {
                    supplierEntity.getItemList().add(itemEntity);

                    if (!itemEntity.getSupplierList().contains(supplierEntity)) {
                        itemEntity.getSupplierList().add(supplierEntity);
                    }
                }
            }
            session.merge(supplierEntity);
        } catch (HibernateException e) {
            return false;
        } finally {
            closeSession();
        }
        return true;
    }
    @Override
    public boolean delete(String id) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            SupplierEntity supplierEntity = entityManager.find(SupplierEntity.class, id);
            if (supplierEntity != null) {
                List<ItemEntity> items = new ArrayList<>(supplierEntity.getItemList());
                for (ItemEntity item : items) {
                    item.getSupplierList().remove(supplierEntity);
                    entityManager.merge(item);
                }
                supplierEntity.getItemList().clear();
                entityManager.merge(supplierEntity);
                entityManager.remove(supplierEntity);
            }
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            entityManager.close();
        }
        return true;
    }
}

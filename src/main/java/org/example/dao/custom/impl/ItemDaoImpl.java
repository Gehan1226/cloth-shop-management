package org.example.dao.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.dao.custom.ItemDao;
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
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class ItemDaoImpl implements ItemDao {
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
    public Item retrieveLastRow() {
        ItemEntity itemEntity;
        try {
            beginSession();
            Query<ItemEntity> query = session.createQuery("from ItemEntity order by id DESC", ItemEntity.class);
            query.setMaxResults(1);
            itemEntity = query.uniqueResult();
        } catch (HibernateException e) {
            throw new RuntimeException("Error executing Hibernate query", e);
        } finally {
            closeSession();
        }
        if (itemEntity != null) {
            itemEntity.setOrderList(null);
            itemEntity.setSupplierList(null);
        }
        return itemEntity != null ? (new ModelMapper().map(itemEntity, Item.class)) : null;
    }

    @Override
    public boolean save(ItemEntity itemEntity, List<String> supplierIDS) {
        try {
            beginSession();
            if (!supplierIDS.isEmpty()) {
                for (int i = 0; i < supplierIDS.size(); i++) {
                    SupplierEntity supplierEntity = session.get(SupplierEntity.class, supplierIDS.get(i));
                    supplierEntity.getItemList().add(itemEntity);
                    session.persist(itemEntity);
                    session.persist(supplierEntity);
                }
            } else {
                session.persist(itemEntity);
            }
        } catch (HibernateException e) {
            return false;
        } finally {
            closeSession();
        }
        return true;
    }

    @Override
    public List<Item> retrieveAll() {
        try {
            List<Item> itemList = new ArrayList<>();
            beginSession();
            Query<ItemEntity> query = session.createQuery("from ItemEntity", ItemEntity.class);
            List<ItemEntity> resultList = query.getResultList();
            for (ItemEntity entity : resultList) {
                entity.setSupplierList(null);
                itemList.add(new ModelMapper().map(entity, Item.class));
            }
            return itemList;
        } catch (HibernateException e) {
            throw new RuntimeException("Error executing Hibernate query", e);
        } finally {
            closeSession();
        }
    }

    @Override
    public Item retrieve(String id) {
        ItemEntity itemEntity = null;
        Item item = null;
        try {
            beginSession();
            itemEntity = session.get(ItemEntity.class, id);
            if (itemEntity != null) {
                item = new ModelMapper().map(itemEntity, Item.class);
            }
        } catch (HibernateException e) {
            throw new RuntimeException("Error executing Hibernate query", e);
        } finally {
            closeSession();
        }
        return item;
    }
    @Override
    public boolean update(ItemEntity dto,List<String> suplierIDS) {
        try {
            beginSession();

            ItemEntity itemEntity = session.get(ItemEntity.class, dto.getItemId());
            itemEntity.setItemName(dto.getItemName());
            itemEntity.setCategorie(dto.getCategorie());
            itemEntity.setItemImagePath(dto.getItemImagePath());
            itemEntity.setPrice(dto.getPrice());
            itemEntity.setQty(dto.getQty());
            itemEntity.setSize(dto.getSize());

            for (String id : suplierIDS){
                SupplierEntity supplierEntity = session.get(SupplierEntity.class, id);
                List<ItemEntity> itemList = supplierEntity.getItemList();
                boolean isItem = false;
                for (ItemEntity entity : itemList){
                    if (entity.getItemId().equals(dto.getItemId())){
                        isItem = true;
                        break;
                    }
                }
                if (!isItem){
                    supplierEntity.getItemList().add(itemEntity);
                }
            }
            session.merge(itemEntity);
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
            ItemEntity itemEntity = entityManager.find(ItemEntity.class, id);
            if (itemEntity != null) {
                List<SupplierEntity> suppliers = new ArrayList<>(itemEntity.getSupplierList());
                for (SupplierEntity supplier : suppliers) {
                    supplier.getItemList().remove(itemEntity);
                    entityManager.merge(supplier);
                }
                itemEntity.getSupplierList().clear();
                entityManager.merge(itemEntity);
                entityManager.remove(itemEntity);
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

package org.example.dao.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.dao.custom.ItemDao;
import org.example.dto.Item;
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
        Item item = null;
        try {
            beginSession();
            Query<ItemEntity> query = session.createQuery("from ItemEntity order by id DESC", ItemEntity.class);
            query.setMaxResults(1);
            ItemEntity itemEntity = query.uniqueResult();
            if (itemEntity != null) {
//                itemEntity.setOrderList(null);
//                itemEntity.setSupplierList(null);
                item = new ModelMapper().map(itemEntity, Item.class);
            }
        } catch (HibernateException e) {
            throw new HibernateException("Error retrieveLastRow method in itemDao", e);
        } finally {
            closeSession();
        }
        return item;
    }

    @Override
    public boolean save(ItemEntity itemEntity, List<String> supplierIDS) {
        try {
            beginSession();
            session.persist(itemEntity);

            if (!supplierIDS.isEmpty()) {
                for (String supplierID : supplierIDS) {
                    SupplierEntity supplierEntity = session.get(SupplierEntity.class, supplierID);
                    if (supplierEntity != null) {
                        supplierEntity.getItemList().add(itemEntity);
                        session.persist(supplierEntity);
                    }
                }
            }
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
    public List<Item> retrieveAll() {
        try {
            List<Item> itemList = new ArrayList<>();
            beginSession();
            Query<ItemEntity> query = session.createQuery("from ItemEntity", ItemEntity.class);
            List<ItemEntity> resultList = query.getResultList();
            for (ItemEntity entity : resultList) {
              itemList.add(new ModelMapper().map(entity, Item.class));
            }
            return itemList;
        } catch (HibernateException e) {
            throw new HibernateException("Error retrieveAll method in itemDao", e);
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
            throw new HibernateException("Error retrieve method in itemDao", e);
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
            if (itemEntity!=null){
                new ModelMapper().map(dto, itemEntity);

                for (String id : suplierIDS) {
                    SupplierEntity supplierEntity = session.get(SupplierEntity.class, id);
                    if (supplierEntity != null) {
                        List<ItemEntity> itemList = supplierEntity.getItemList();
                        if (!itemList.contains(itemEntity)) {
                            itemList.add(itemEntity);
                        }
                    }
                }
                session.merge(itemEntity);
                transaction.commit();
                return true;
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        } finally {
            closeSession();
        }
        return false;
    }
    @Override
    public boolean delete(String id) {
        HibernateUtil.createEntityManager()
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
            return false;
        } finally {
            entityManager.close();
        }
        return true;
    }
}

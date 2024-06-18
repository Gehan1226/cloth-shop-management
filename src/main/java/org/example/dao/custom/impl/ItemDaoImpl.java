package org.example.dao.custom.impl;

import org.example.bo.custom.ItemBo;
import org.example.dao.custom.ItemDao;
import org.example.dto.Employee;
import org.example.dto.Item;
import org.example.dto.Supplier;
import org.example.entity.EmployeeEntity;
import org.example.entity.ItemEntity;
import org.example.entity.SupplierEntity;
import org.example.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

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
        if (itemEntity!= null){
            itemEntity.setSupplierList(null);
        }
        return itemEntity != null ? (new ModelMapper().map(itemEntity,Item.class)) : null;
    }
    @Override
    public boolean save(ItemEntity itemEntity, List<String> supplierIDS){
        try {
            beginSession();
            if (!supplierIDS.isEmpty()){
                for (int i=0;i<supplierIDS.size();i++){
                    SupplierEntity supplierEntity = session.get(SupplierEntity.class, supplierIDS.get(i));
                    supplierEntity.getItemList().add(itemEntity);
                    session.persist(supplierEntity);
                }
            }else {
                session.persist(itemEntity);
            }
        }catch (HibernateException e) {
            return false;
        } finally {
            closeSession();
        }
        return true;
    }
    @Override
    public List<Item> retrieveAll(){
        List<Item> itemList = new ArrayList<>();
        try {
            beginSession();
            Query<ItemEntity> query = session.createQuery("from ItemEntity", ItemEntity.class);
            List<ItemEntity> resultList = query.getResultList();
            for(ItemEntity entity : resultList){
                entity.setSupplierList(null);
                itemList.add(new ModelMapper().map(entity,Item.class));
            }
        }catch (HibernateException e) {
            throw new RuntimeException("Error executing Hibernate query", e);
        } finally {
            closeSession();
        }
        return itemList;
    }
    public Item retrieveByID(String id){
        Item item = null;
        try {
            beginSession();
            ItemEntity itemEntity = session.get(ItemEntity.class, id);
            item = new ModelMapper().map(itemEntity, Item.class);
        }catch (HibernateException e) {
            throw new RuntimeException("Error executing Hibernate query", e);
        } finally {
            closeSession();
        }
        return item;
    }
    public boolean update(ItemEntity itemEntity){
        try {
            beginSession();
            session.get(ItemEntity.class,itemEntity.getItemId());
            session.merge(itemEntity);
        }catch (HibernateException e) {
            return false;
        } finally {
            closeSession();
        }
        return true;
    }
}

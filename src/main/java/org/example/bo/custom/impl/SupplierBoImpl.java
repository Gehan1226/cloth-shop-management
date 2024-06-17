package org.example.bo.custom.impl;

import org.example.bo.custom.SupplierBo;
import org.example.dao.Daofactory;
import org.example.dao.custom.SupplierDao;
import org.example.dto.Item;
import org.example.dto.Supplier;
import org.example.entity.ItemEntity;
import org.example.entity.SupplierEntity;
import org.example.util.DaoType;
import org.hibernate.HibernateException;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class SupplierBoImpl implements SupplierBo {
    private final SupplierDao supplierDao = Daofactory.getInstance().getDao(DaoType.SUPPLIER);
    @Override
    public List<String> getAllIDSAndNames() {
        System.out.println("he");
        List<String> supplierIDS = new ArrayList<>();
//        List<Supplier> supplierList = supplierDao.retrieveAll();
//        for (Supplier supplier : supplierList){
//            supplierIDS.add(supplier.getSupID());
//        }
        return supplierIDS;
    }
    @Override
    public String genarateSupplierID() {
        Supplier supplier = supplierDao.retrieveLastRow();
        if (supplier!= null){
            return "S"+(Integer.parseInt(supplier.getSupID().substring(1))+1);
        }
        return "S1";
    }
    @Override
    public boolean saveSupplier(Supplier supplier,List<String> itemIDS){
        return supplierDao.save(new ModelMapper().map(supplier,SupplierEntity.class),itemIDS);
    }
}

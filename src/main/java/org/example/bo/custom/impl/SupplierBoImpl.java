package org.example.bo.custom.impl;

import org.example.bo.custom.SupplierBo;
import org.example.dao.Daofactory;
import org.example.dao.custom.ItemDao;
import org.example.dao.custom.SupplierDao;
import org.example.dto.Supplier;
import org.example.util.DaoType;

import java.util.ArrayList;
import java.util.List;

public class SupplierBoImpl implements SupplierBo {
    private final SupplierDao supplierDao = Daofactory.getInstance().getDao(DaoType.SUPPLIER);
    @Override
    public List<String> getAllSupplierIDS() {
        System.out.println("he");
        List<String> supplierIDS = new ArrayList<>();
//        List<Supplier> supplierList = supplierDao.retrieveAll();
//        for (Supplier supplier : supplierList){
//            supplierIDS.add(supplier.getSupID());
//        }
        return supplierIDS;
    }
}

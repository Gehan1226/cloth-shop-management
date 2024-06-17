package org.example.dao.custom;

import org.example.dao.CrudDao;
import org.example.dto.Supplier;
import org.example.entity.SupplierEntity;

import java.util.List;

public interface SupplierDao extends CrudDao<SupplierEntity> {
    List<Supplier> retrieveAll();
    Supplier retrieveLastRow();
    boolean save(SupplierEntity supplierEntity, List<String> itemIDS);
}

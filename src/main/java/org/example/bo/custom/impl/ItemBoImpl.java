package org.example.bo.custom.impl;

import org.example.bo.BoFactory;
import org.example.bo.custom.EmployeeBo;
import org.example.bo.custom.ItemBo;
import org.example.dao.Daofactory;
import org.example.dao.custom.ItemDao;
import org.example.dao.custom.SupplierDao;
import org.example.dao.custom.UserDao;
import org.example.dto.Employee;
import org.example.dto.Item;
import org.example.dto.Supplier;
import org.example.entity.ItemEntity;
import org.example.util.BoType;
import org.example.util.DaoType;
import org.example.util.EmployeeReport;
import org.example.util.InventoryReport;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ItemBoImpl implements ItemBo {
    private final ItemDao itemDao = Daofactory.getInstance().getDao(DaoType.ITEM);
    private final SupplierDao supplierDao = Daofactory.getInstance().getDao(DaoType.SUPPLIER);
    @Override
    public String genarateItemID() {
        Item item = itemDao.retrieveLastRow();
        if (item!= null){
            return "I"+(Integer.parseInt(item.getItemId().substring(1))+1);
        }
        return "I1";
    }
    public boolean saveItem(Item item, List<String> supIDS){
        return itemDao.save(new ModelMapper().map(item, ItemEntity.class),supIDS);
    }
    public List<String> getAllIDSAndNames() {
        List<String> itemIDSandNames = new ArrayList<>();
        List<Item> itemList = itemDao.retrieveAll();
        for (Item item : itemList){
            itemIDSandNames.add(item.getItemId()+" - "+item.getItemName());
        }
        return itemIDSandNames;
    }
    @Override
    public Item retrieveById(String id){
        return itemDao.retrieve(id);
    }
    public boolean updateItem(Item item,List<String> supllierIDS){
        return itemDao.update(new ModelMapper().map(item, ItemEntity.class),supllierIDS);
    }
    @Override
    public boolean deleteItem(String itemID){
        return itemDao.delete(itemID);
    }
    @Override
    public List<Item> getAllItems(){
        return itemDao.retrieveAll();
    }
    @Override
    public boolean genarateInventoryReport(){
        List<Item> itemList = itemDao.retrieveAll();
        return InventoryReport.genarateInventoryReport(itemList);
    }
}

package org.example.util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example.dto.Item;
import org.example.dto.Order;

import java.time.LocalDate;
import java.util.*;

public class OrderReceiptGenerator {
    public static void generateReceipt(Order order) throws JRException {
        System.out.println("done1");
        List<Map<String, Object>> itemListData = new ArrayList<>();
        for (int i = 0; i < order.getItemList().size(); i++) {
            Item item = order.getItemList().get(i);
            Map<String, Object> itemData = new HashMap<>();
            itemData.put("itemName", item.getItemName());
            itemData.put("itemSize", item.getSize());
            itemData.put("itemPrice", item.getPrice());
            itemData.put("itemQuantity", order.getItemQtyList().get(i));
            itemListData.add(itemData);
        }
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(itemListData);
        JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/jrxmlFiles/orderReceipt.jrxml");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("orderID", order.getOrderID());
        parameters.put("orderDate", order.getOrderDate().toString());
        parameters.put("fullPrice", order.getFullPrice());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        JasperExportManager.exportReportToPdfFile(jasperPrint, "orderReceipt.pdf");
        System.out.println("done2");
    }
}

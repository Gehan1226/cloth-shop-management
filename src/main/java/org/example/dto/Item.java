package org.example.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Item {
    private String itemId;
    private String itemName;
    private String size;
    private Double price;
    private Integer qty;
    private String categorie;
    private String itemImagePath;
    private List<Supplier> supplierList;
    private List<Order> orderList ;
}

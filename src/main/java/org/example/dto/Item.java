package org.example.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Item {
    private String itemId;
    private String itemName;
    private String size;
    private Double price;
    private Integer qty;
    private Supplier supplier;
}

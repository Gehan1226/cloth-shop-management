package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Item {
    private String itemId;
    private String itemName;
    private String size;
    private Double price;
    private Integer qty;
    private List<Supplier> supplier = new ArrayList<>();
}

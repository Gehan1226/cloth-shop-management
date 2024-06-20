package org.example.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    private String orderID;
    private Date orderDate;
    private Customer customer;
    private List<Item> itemList;
}

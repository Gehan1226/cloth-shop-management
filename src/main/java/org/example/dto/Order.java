package org.example.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    private String orderID;
    private LocalDate orderDate;
    private Customer customer;
    private List<Item> itemList;
    private Employee employee;
}

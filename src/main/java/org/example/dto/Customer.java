package org.example.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {
    private String name;
    private String email;
    private String mobileNumber;
    private List<Order> orderList;
}

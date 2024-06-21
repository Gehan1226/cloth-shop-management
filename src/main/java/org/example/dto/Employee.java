package org.example.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employee {
    private String empID;
    private String firstName;
    private String lastName;
    private String nic;
    private String mobileNumber;
    private String province;
    private String district;
    private String email;
    private List<Order> orderList;
}

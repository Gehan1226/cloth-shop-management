package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Employee {
    private String firstName;
    private String lastName;
    private String nic;
    private String mobileNumber;
    private String city;
    private String district;
    private String email;


}

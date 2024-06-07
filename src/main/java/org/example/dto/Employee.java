package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Employee {
    private String firstName;
    private String lastName;
    private Integer nic;
    private Integer mobileNumber;
    private String city;
    private String email;

}

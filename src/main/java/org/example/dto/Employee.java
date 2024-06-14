package org.example.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employee {
    private String empId;
    private String firstName;
    private String lastName;
    private String nic;
    private String mobileNumber;
    private String province;
    private String district;
    private String email;

}

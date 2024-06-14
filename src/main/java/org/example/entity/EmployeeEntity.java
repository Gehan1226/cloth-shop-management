package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.dto.Employee;
import org.example.dto.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class EmployeeEntity {
    @Id
    private String empID;
    private String firstName;
    private String lastName;
    private String nic;
    private String mobileNumber;
    private String province;
    private String district;
    private String email;
}

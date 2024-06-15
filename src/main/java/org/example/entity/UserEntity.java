package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.dto.Employee;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String email;
    private String password;
    private Boolean isAdmin;

}

package org.example.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class User {
    private String email;
    private String password;
    private Boolean isAdmin;
}

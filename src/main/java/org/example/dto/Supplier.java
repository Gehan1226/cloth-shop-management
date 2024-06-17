package org.example.dto;

import lombok.*;

import javax.management.ConstructorParameters;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
    private String supID;
    private String firstName;
    private String lastName;
    private String company;
    private String email;
    private String mobileNumber;
    private List<Item> itemList = new ArrayList<>();
}

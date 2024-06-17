package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.management.ConstructorParameters;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor

public class Supplier {
    private String supID;
    private String firstName;
    private String lastName;
    private String company;
    private String email;
    private String mobileNumber;
    private List<Item> itemList = new ArrayList<>();
}

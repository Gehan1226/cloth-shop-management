package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.dto.Item;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Entity
public class SupplierEntity {
    @Id
    private String supID;
    private String firstName;
    private String lastName;
    private String company;
    private String email;
    private String mobileNumber;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "supplierList",
            joinColumns = { @JoinColumn(name = "supID") },
            inverseJoinColumns = { @JoinColumn(name = "itemId") }
    )
    private List<ItemEntity> itemList = new ArrayList<>();
}

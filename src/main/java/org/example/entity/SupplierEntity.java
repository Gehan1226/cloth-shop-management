package org.example.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.dto.Item;
import java.util.List;

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
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> itemList;
}

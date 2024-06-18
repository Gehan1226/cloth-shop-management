package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.dto.Supplier;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ItemEntity {
    @Id
    private String itemId;
    private String itemName;
    private String size;
    private Double price;
    private Integer qty;
    private String categorie;
    private String itemImagePath;
    @ManyToMany(mappedBy = "itemList")
    private List<SupplierEntity> supplierList = new ArrayList<>();
}

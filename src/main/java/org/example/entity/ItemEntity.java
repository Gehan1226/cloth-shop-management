package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.dto.Supplier;

@Data
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supID")
    private Supplier supplier;
}

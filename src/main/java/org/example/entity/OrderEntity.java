package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class OrderEntity {
    @Id
    private String orderID;
    private Date orderDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerID")
    private CustomerEntity customer;
    @ManyToMany(cascade = {CascadeType.ALL })
    @JoinTable(
            name = "order_item",
            joinColumns = { @JoinColumn(name = "orderID") },
            inverseJoinColumns = { @JoinColumn(name = "itemId") }
    )
    private List<ItemEntity> itemList;
}

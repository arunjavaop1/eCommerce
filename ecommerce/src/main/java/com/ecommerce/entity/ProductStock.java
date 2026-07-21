package com.ecommerce.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.SequenceGenerator;
import lombok.*;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductStock {

    @Id
    @SequenceGenerator(name = "productStock_seq", sequenceName = "productStock_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productStock_seq")
    private long id;

    @ManyToOne
    @JoinColumn(name = "productStock_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;
    private int quantity;
}

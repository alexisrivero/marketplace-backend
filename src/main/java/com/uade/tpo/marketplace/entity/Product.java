package com.uade.tpo.marketplace.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String brand;

    @Column
    private String description;

    @Column
    private double price;

    @Column
    private int stock;

    @Column
    private String imageRoute;
}

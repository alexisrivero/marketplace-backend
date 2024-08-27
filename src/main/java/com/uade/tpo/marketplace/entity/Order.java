package com.uade.tpo.marketplace.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Builder
@Table(name = "customer_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    private Address address;

    @OneToOne
    private PaymentMethod paymentMethod;

    @Column
    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProductList;

    @OneToOne
    private Transaction transaction;

    @Column
    private double total;

}

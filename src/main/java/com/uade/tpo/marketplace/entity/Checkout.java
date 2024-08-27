package com.uade.tpo.marketplace.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Builder
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    @OneToMany(mappedBy = "checkout")
    private List<CheckoutProduct> checkoutProductList;

    @OneToOne
    private Address address;

    @OneToOne
    private PaymentMethod paymentMethod;
}

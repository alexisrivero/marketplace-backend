package com.uade.tpo.marketplace.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String phoneNumber;

    @Column
    private String role;

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<Address> addresses;

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<PaymentMethod> paymentMethods;

    @OneToOne(mappedBy = "user")
    private Checkout checkout;
}

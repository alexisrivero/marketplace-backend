package com.uade.tpo.marketplace.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Builder
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String cardType;

    @Column
    private String cardNumber;

    @Column
    private Date expirationDate;

    @Column
    private String ownerName;

    @Column
    private double funds;

}

package com.uade.tpo.marketplace.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
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
    private double subTotal;

    @Column
    private double total;

}

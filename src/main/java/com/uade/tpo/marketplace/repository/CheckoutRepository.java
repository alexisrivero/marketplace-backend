package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
}

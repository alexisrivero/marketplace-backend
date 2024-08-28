package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.CheckoutProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutProductRepository extends JpaRepository<CheckoutProduct, Long> {
}

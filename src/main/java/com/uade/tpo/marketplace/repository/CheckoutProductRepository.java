package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.CheckoutProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutProductRepository extends JpaRepository<CheckoutProduct, Long> {
}

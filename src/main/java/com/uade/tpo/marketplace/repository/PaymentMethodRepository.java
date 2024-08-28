package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}

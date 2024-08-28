package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}

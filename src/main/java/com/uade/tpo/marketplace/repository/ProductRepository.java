package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

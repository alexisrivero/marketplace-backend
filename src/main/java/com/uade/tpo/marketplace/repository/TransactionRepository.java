package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}

package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.Checkout;
import com.uade.tpo.marketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
    Checkout findByUser(User user);

}

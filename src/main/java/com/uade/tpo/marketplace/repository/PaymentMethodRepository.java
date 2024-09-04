package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.PaymentMethod;
import com.uade.tpo.marketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    PaymentMethod findByUserAndId(User user, Long id);

    List<PaymentMethod> findAllByUser(User user);
}

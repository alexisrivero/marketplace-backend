package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.Checkout;
import com.uade.tpo.marketplace.entity.CheckoutProduct;
import com.uade.tpo.marketplace.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutProductRepository extends JpaRepository<CheckoutProduct, Long> {

    CheckoutProduct findByCheckoutAndProduct(Checkout checkout, Product product);

    void deleteAllByCheckout(Checkout checkout);
}

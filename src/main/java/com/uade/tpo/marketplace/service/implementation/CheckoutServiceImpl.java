package com.uade.tpo.marketplace.service.implementation;

import com.uade.tpo.marketplace.repository.CheckoutRepository;
import com.uade.tpo.marketplace.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private CheckoutRepository checkoutRepository;
}

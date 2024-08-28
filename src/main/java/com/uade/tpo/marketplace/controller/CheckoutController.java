package com.uade.tpo.marketplace.controller;

import com.uade.tpo.marketplace.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;
}

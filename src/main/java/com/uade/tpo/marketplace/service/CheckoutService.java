package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.dto.CheckoutDTO;
import com.uade.tpo.marketplace.dto.CreateCheckoutDTO;
import com.uade.tpo.marketplace.dto.CreateCheckoutProductDTO;
import com.uade.tpo.marketplace.dto.UpdateCheckoutProductDTO;

public interface CheckoutService {

    CheckoutDTO getCheckout(String authHeader);
    void createCheckOut(String authHeader, CreateCheckoutDTO checkoutDTO);

    void addProductToCheckout(String authHeader, CreateCheckoutProductDTO checkoutProductDTO);

    void modifyCheckoutProductQuantity(String authHeader,long productId, UpdateCheckoutProductDTO updateCheckoutProductDTO);

    void deleteCheckoutProduct(String authHeader,long productId);

    void deleteCheckout(String authHeader);

    void changeCheckoutAddress (String authHeader,long id);

    void changeCheckoutPaymentMethod (String authHeader,long id);

    void generateOrder(String authHeader);
}

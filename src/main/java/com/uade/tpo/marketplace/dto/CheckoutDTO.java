package com.uade.tpo.marketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutDTO {
//DTO to show on JSON all the info of a checkout

    private String email;
    private String name;
    private String lastName;
    private List<CheckoutProductDTO> checkoutProducts;
    private CreateAddressDTO address;
    private PaymentMethodNoIdDTO paymentMethod;
    private double subTotal;
    private double total;
}

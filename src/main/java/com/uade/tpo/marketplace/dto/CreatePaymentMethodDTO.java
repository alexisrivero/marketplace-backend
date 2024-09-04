package com.uade.tpo.marketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentMethodDTO {
//Dto used to create a payment method on the checkout


    private String name;
    private double founds;
    private String paymentType;

}

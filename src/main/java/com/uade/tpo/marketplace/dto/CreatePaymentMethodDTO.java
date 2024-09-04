package com.uade.tpo.marketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentMethodDTO {
//Dto used to create a payment method on the checkout

    private String cardType;
    private String cardNumber;
    private Date expirationDate;
    private String ownerName;
    private double funds;

}

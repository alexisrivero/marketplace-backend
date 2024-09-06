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
public class PaymentMethodNoIdDTO {
//DTO used to show the payment method information
    private String cardNumber;
    private String cardType;
    private Date expirationDate;
    private String ownerName;

}

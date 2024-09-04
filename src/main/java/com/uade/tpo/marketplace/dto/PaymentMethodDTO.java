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
public class PaymentMethodDTO {
//DTO used to show the payment method with id and without the funds

    private long id;
    private String cardType;
    private String cardNumber;
    private Date expirationDate;
    private String ownerName;

}

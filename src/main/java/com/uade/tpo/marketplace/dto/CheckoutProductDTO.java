package com.uade.tpo.marketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutProductDTO {
//DTO used to show  only some information of the product checkout

    private String name;
    private double price;
    private int quantity;
}

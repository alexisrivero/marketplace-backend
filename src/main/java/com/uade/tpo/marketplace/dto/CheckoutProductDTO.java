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

    private Long id;
    private String name;
    private String brand;
    private String category;
    private String description;
    private double price;
    private int quantity;
    private String imageRoute;
}

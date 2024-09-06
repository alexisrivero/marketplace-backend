package com.uade.tpo.marketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    //Dto used to show the information of an specific order

    private long id;
    private String name;
    private String lastName;
    private CreateAddressDTO address;
    private PaymentMethodNoIdDTO paymentMethod;
    private List<OrderProductDTO> orderProducts;
    private double total;
}

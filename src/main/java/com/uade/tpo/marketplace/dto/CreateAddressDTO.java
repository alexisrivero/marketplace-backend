package com.uade.tpo.marketplace.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAddressDTO {
//Dto used for creating an address in the checkout

    private String state;
    private String city;
    private String street;
    private String houseNumber;
    private String description;
}

package com.uade.tpo.marketplace.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAddressDTO {
//Dto used for creating an address in the checkout

    private String houseNumber;
    private String street;
    private String city;
    private String state;
}

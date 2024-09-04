package com.uade.tpo.marketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddressDTO {
//DTO used for geting an address from the checkout

    private long id;

    private String houseNumber;

    private String street;

    private String city;

    private String state;
}

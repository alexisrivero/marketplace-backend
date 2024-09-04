package com.uade.tpo.marketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
//DTO used for showing important information about the user

    private String email;

    private String userName;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private List<CreateAddressDTO> address;

    private List<PaymentMethodNoIdDTO> paymentMethods;
}

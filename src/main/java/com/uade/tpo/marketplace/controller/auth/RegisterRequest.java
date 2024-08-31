package com.uade.tpo.marketplace.controller.auth;


import com.uade.tpo.marketplace.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String name;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;
}

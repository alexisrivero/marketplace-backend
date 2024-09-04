package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.dto.*;
import com.uade.tpo.marketplace.entity.Address;
import com.uade.tpo.marketplace.entity.PaymentMethod;
import com.uade.tpo.marketplace.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDTO getUser(String email);
    List<UserAddressDTO> getAllAddresses(String authHeader);
    void createAddress(String authHeader, CreateAddressDTO createAddressDTO);
    List<PaymentMethodDTO> getAllPaymentMethods(String authHeader);
    void createPaymentMethod(String authHeader, CreatePaymentMethodDTO createPaymentMethodDTO);

    List<User> getAllUsers();
    Optional<User> getUserById(Long id);

    void deleteUserById(Long userId) throws RuntimeException;

}

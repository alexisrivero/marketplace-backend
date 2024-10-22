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
    void editAddress(Long addressId, CreateAddressDTO createAddressDTO);
    void deleteAddress(Long addressId);
    List<PaymentMethodDTO> getAllPaymentMethods(String authHeader);
    void createPaymentMethod(String authHeader, CreatePaymentMethodDTO createPaymentMethodDTO);
    void editPaymentMethod(Long paymentMethodId, CreatePaymentMethodDTO createPaymentMethodDTO);
    void deletePaymentMethod(Long paymentMethodId);
    List<User> getAllUsers();
    UserDTO getUserById(String authHeader);
    void deleteUserById(Long userId) throws RuntimeException;

}

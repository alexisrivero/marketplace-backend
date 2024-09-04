package com.uade.tpo.marketplace.service.implementation;

import com.uade.tpo.marketplace.controller.config.JwtService;
import com.uade.tpo.marketplace.dto.*;
import com.uade.tpo.marketplace.entity.Address;
import com.uade.tpo.marketplace.entity.PaymentMethod;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.exceptions.ResourceNotFoundException;
import com.uade.tpo.marketplace.mapper.AddressMapper;
import com.uade.tpo.marketplace.mapper.PaymentMethodMapper;
import com.uade.tpo.marketplace.mapper.UserMapper;
import com.uade.tpo.marketplace.repository.AddressRepository;
import com.uade.tpo.marketplace.repository.PaymentMethodRepository;
import com.uade.tpo.marketplace.repository.UserRepository;
import com.uade.tpo.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private JwtService jwtService;

    @Override
    public UserDTO getUser(String email) {
        User user = this.findUser(email);

        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    @Override
    public List<UserAddressDTO> getAllAddresses(String authHeader) {
        String email = this.getEmailFromAuthHeader(authHeader);

        User user = this.findUser(email);

        List<Address> Addresses = this.addressRepository.findAllByUser(user);

        if (Addresses.isEmpty()) throw new ResourceNotFoundException("This user currently has no addresses. Try to create a new one");

        return AddressMapper.INSTANCE.addressToCheckoutUserAddressDTO(Addresses);
    }

    @Override
    public void createAddress(String authHeader, CreateAddressDTO createAddressDTO) {
        String email = this.getEmailFromAuthHeader(authHeader);

        User user = this.findUser(email);

        Address createAddress = AddressMapper.INSTANCE.createAddressDTOToAddress(createAddressDTO);
        createAddress.setUser(user);

        addressRepository.save(createAddress);
    }

    @Override
    public List<PaymentMethodDTO> getAllPaymentMethods(String authHeader) {
        String email = this.getEmailFromAuthHeader(authHeader);

        User user = this.findUser(email);

        List<PaymentMethod> paymentMethods = this.paymentMethodRepository.findAllByUser(user);

        if (paymentMethods.isEmpty()) throw new ResourceNotFoundException("This user currently has no payment methods. Try to create a new one");

        return PaymentMethodMapper.INSTANCE.paymentMethodToPaymentMethodDTO(paymentMethods);
    }

    @Override
    public void createPaymentMethod(String authHeader, CreatePaymentMethodDTO createPaymentMethodDTO) {
        String email = this.getEmailFromAuthHeader(authHeader);

        User user = this.findUser(email);

        PaymentMethod createPaymentMethod = PaymentMethodMapper.INSTANCE.createPaymentMethodDTOToPaymentMethod(createPaymentMethodDTO);

        createPaymentMethod.setUser(user);

        paymentMethodRepository.save(createPaymentMethod);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        return user;
    }

    public void deleteUserById(Long userId) throws RuntimeException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        userRepository.deleteById(userId);
    }

    private User findUser (String email)
    {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) throw new ResourceNotFoundException("User not found with ID: " + email);

        return user.get();
    }

    private String getEmailFromAuthHeader(String authHeader) {
        String jwt = authHeader.substring(7);

        return jwtService.extractUsername(jwt);
    }

}

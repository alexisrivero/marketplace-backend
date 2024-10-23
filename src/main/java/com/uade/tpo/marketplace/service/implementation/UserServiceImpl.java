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

import java.util.ArrayList;
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
    public UserDTO getUser(String authHeader) {
        String email = this.getEmailFromAuthHeader(authHeader);
        User user = this.findUser(email);

        UserDTO dto = this.mapUserToUserDTO(user);

        return dto;
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
    public void editAddress(Long addressId, CreateAddressDTO createAddressDTO) {
        Optional<Address> foundAddress = this.addressRepository.findById(addressId);

        if (foundAddress.isEmpty()) throw new ResourceNotFoundException("This Address does not exist");

        Address addressToUpdate = foundAddress.get();

        addressToUpdate.setState(createAddressDTO.getState());
        addressToUpdate.setCity(createAddressDTO.getCity());
        addressToUpdate.setStreet(createAddressDTO.getStreet());
        addressToUpdate.setHouseNumber(createAddressDTO.getHouseNumber());
        addressToUpdate.setDescription(createAddressDTO.getDescription());

        addressRepository.save(addressToUpdate);
    }

    @Override
    public void deleteAddress(Long addressId) {
        Optional<Address> foundAddress = this.addressRepository.findById(addressId);

        if (foundAddress.isEmpty()) throw new ResourceNotFoundException("This Address does not exist");

        this.addressRepository.delete(foundAddress.get());
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

    @Override
    public void editPaymentMethod(Long paymentMethodId, CreatePaymentMethodDTO createPaymentMethodDTO) {
        Optional<PaymentMethod> foundPaymentMethod = this.paymentMethodRepository.findById(paymentMethodId);

        if (foundPaymentMethod.isEmpty()) throw new ResourceNotFoundException("This Payment Method does not exist");

        PaymentMethod paymentMethodToUpdate = foundPaymentMethod.get();

        paymentMethodToUpdate.setCardType(createPaymentMethodDTO.getCardType());
        paymentMethodToUpdate.setCardNumber(createPaymentMethodDTO.getCardNumber());
        paymentMethodToUpdate.setExpirationDate(createPaymentMethodDTO.getExpirationDate());
        paymentMethodToUpdate.setOwnerName(createPaymentMethodDTO.getOwnerName());

        this.paymentMethodRepository.save(paymentMethodToUpdate);
    }

    @Override
    public void deletePaymentMethod(Long paymentMethodId) {
        Optional<PaymentMethod> foundPaymentMethod = this.paymentMethodRepository.findById(paymentMethodId);

        if (foundPaymentMethod.isEmpty()) throw new ResourceNotFoundException("This Payment Method does not exist");

        this.paymentMethodRepository.delete(foundPaymentMethod.get());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }

        User foundUser = user.get();

        UserDTO dto = this.mapUserToUserDTO(foundUser);

        return dto;
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


    private UserDTO mapUserToUserDTO(User user) {
        UserDTO dto = new UserDTO();

        dto.setEmail(user.getEmail());
        dto.setUserName(user.getUsername());
        dto.setFirstName(user.getName());
        dto.setLastName(user.getLastName());
        dto.setPhoneNumber(user.getPhoneNumber());

        List<CreateAddressDTO> addressDTOList= new ArrayList<>();

        for (Address address: user.getAddresses()) {
            CreateAddressDTO addressDTO = new CreateAddressDTO();
            addressDTO.setCity(address.getCity());
            addressDTO.setStreet(address.getStreet());
            addressDTO.setState(address.getState());
            addressDTO.setHouseNumber(address.getHouseNumber());
            addressDTO.setDescription(address.getDescription());
            addressDTOList.add(addressDTO);
        }

        dto.setAddress(addressDTOList);

        List<PaymentMethodNoIdDTO> paymentMethodNoIdDTOList = new ArrayList<>();

        for (PaymentMethod paymentMethod: user.getPaymentMethods()) {
            PaymentMethodNoIdDTO paymentMethodNoIdDTO = new PaymentMethodNoIdDTO();
            paymentMethodNoIdDTO.setCardNumber(paymentMethod.getCardNumber());
            paymentMethodNoIdDTO.setCardType(paymentMethod.getCardType());
            paymentMethodNoIdDTO.setOwnerName(paymentMethod.getOwnerName());
            paymentMethodNoIdDTO.setExpirationDate(paymentMethod.getExpirationDate());
            paymentMethodNoIdDTOList.add(paymentMethodNoIdDTO);
        }

        dto.setPaymentMethods(paymentMethodNoIdDTOList);

        return dto;
    }
}

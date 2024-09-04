package com.uade.tpo.marketplace.controller;

import com.uade.tpo.marketplace.dto.CreateAddressDTO;
import com.uade.tpo.marketplace.dto.CreatePaymentMethodDTO;
import com.uade.tpo.marketplace.dto.PaymentMethodDTO;
import com.uade.tpo.marketplace.dto.UserAddressDTO;
import com.uade.tpo.marketplace.entity.Address;
import com.uade.tpo.marketplace.entity.PaymentMethod;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //TODO: agregar editar/borrar addresses y payment-methods

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long userId) {
        Optional<User> result = userService.getUserById(userId);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUserById(@PathVariable("userId") Long userId)  throws RuntimeException{
        userService.deleteUserById(userId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/address")
    public ResponseEntity<List<UserAddressDTO>> getAllUserAddresses(@RequestHeader("Authorization") String authHeader) {
        return new ResponseEntity<>(this.userService.getAllAddresses(authHeader), HttpStatus.OK);
    }

    @PostMapping("/address")
    public ResponseEntity<String> createAddress (@RequestHeader("Authorization") String authHeader, @RequestBody CreateAddressDTO createAddressDTO) {
        this.userService.createAddress(authHeader, createAddressDTO);
        return new ResponseEntity<>("Address Added Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/payment-method")
    public ResponseEntity<List<PaymentMethodDTO>> getAllUserPaymentMethods(@RequestHeader("Authorization") String authHeader) {
        return new ResponseEntity<>(this.userService.getAllPaymentMethods(authHeader), HttpStatus.OK);
    }

    @PostMapping("/payment-method")
    public ResponseEntity<String> createPaymentMethod (@RequestHeader("Authorization") String authHeader, @RequestBody CreatePaymentMethodDTO createPaymentMethodDTO) {
        this.userService.createPaymentMethod(authHeader, createPaymentMethodDTO);
        return new ResponseEntity<>("Payment Method Added Successfully", HttpStatus.CREATED);
    }



}

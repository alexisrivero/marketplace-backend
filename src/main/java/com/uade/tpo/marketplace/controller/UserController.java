package com.uade.tpo.marketplace.controller;

import com.uade.tpo.marketplace.entity.PaymentMethod;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
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

    @PostMapping("/{userId}/payment-method")
    public ResponseEntity<User> addPaymentMethodToUser(@PathVariable("userId") Long userId, @RequestBody PaymentMethod paymentMethod) {
        User updatedUser = userService.addPaymentMethodToUser(userId, paymentMethod);
        return new ResponseEntity<>(updatedUser, HttpStatus.valueOf(200));
    }


}

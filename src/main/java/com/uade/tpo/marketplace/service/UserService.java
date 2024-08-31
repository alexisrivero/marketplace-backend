package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();
    Optional<User> getUserById(Long id);

    void deleteUserById(Long userId) throws RuntimeException;
}

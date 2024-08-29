package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.exceptions.CategoryDuplicateException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    public User createUser(User user) throws CategoryDuplicateException;
}

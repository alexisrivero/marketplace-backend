package com.uade.tpo.marketplace.service.implementation;

import com.uade.tpo.marketplace.entity.PaymentMethod;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.exceptions.ResourceNotFoundException;
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

    @Override
    public User addPaymentMethodToUser(Long userId, PaymentMethod paymentMethod) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        User user = userOptional.get();
        paymentMethod.setUser(user);
        user.getPaymentMethods().add(paymentMethod);
        return this.userRepository.save(user);
    }


}

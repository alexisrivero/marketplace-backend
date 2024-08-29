package com.uade.tpo.marketplace.service.implementation;

import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.exceptions.CategoryDuplicateException;
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
        return userRepository.findById(id);
    }

    public User createUser(User user) throws CategoryDuplicateException {
        Optional<User> users = userRepository.findByEmail(user.getEmail());
        if (users.isEmpty())
            return userRepository.save(user);
        throw new CategoryDuplicateException();


    }
}

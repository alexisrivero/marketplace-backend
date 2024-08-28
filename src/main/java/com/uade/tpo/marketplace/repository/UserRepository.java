package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

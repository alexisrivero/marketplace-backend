package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.Address;
import com.uade.tpo.marketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByUserAndId(User user, Long id);

    List<Address> findAllByUser(User user);
}

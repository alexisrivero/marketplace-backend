package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}

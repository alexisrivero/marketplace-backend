package com.uade.tpo.marketplace.service.implementation;

import com.uade.tpo.marketplace.repository.OrderRepository;
import com.uade.tpo.marketplace.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
}

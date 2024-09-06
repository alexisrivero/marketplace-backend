package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    List<OrderDTO> getAllOrders();

    List<OrderDTO> getCurrentUserOrders(String email);

    OrderDTO getCurrentUserOrderById(String email,long id);

    OrderDTO getOrderById(long id);
}

package com.uade.tpo.marketplace.controller;

import com.uade.tpo.marketplace.dto.OrderDTO;
import com.uade.tpo.marketplace.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //TODO: get All orders y get Order By Id (todo esto sin tener en cuenta el current user, seria para el usuario administrador
    @GetMapping("/admin")
    public List<OrderDTO> getAllOrders()
    {
        return this.orderService.getAllOrders();
    }
    @GetMapping("/admin/{id}")
    public OrderDTO getOrderById(@PathVariable long id)
    {
        return this.orderService.getOrderById(id);
    }
    @GetMapping()
    public List<OrderDTO> getCurrentUserOrders(@RequestHeader("Authorization") String authHeader)
    {
        return this.orderService.getCurrentUserOrders(authHeader);
    }

    @GetMapping("/{id}")
    public OrderDTO getCurrentUserOrderById(@RequestHeader("Authorization") String authHeader, @PathVariable long id)
    {
        return this.orderService.getCurrentUserOrderById(authHeader,id);
    }

}

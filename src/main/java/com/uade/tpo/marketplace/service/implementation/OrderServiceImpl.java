package com.uade.tpo.marketplace.service.implementation;

import com.uade.tpo.marketplace.controller.config.JwtService;
import com.uade.tpo.marketplace.dto.OrderDTO;
import com.uade.tpo.marketplace.dto.OrderProductDTO;
import com.uade.tpo.marketplace.entity.Order;
import com.uade.tpo.marketplace.entity.OrderProduct;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.exceptions.ResourceNotFoundException;
import com.uade.tpo.marketplace.mapper.OrderMapper;
import com.uade.tpo.marketplace.repository.OrderRepository;
import com.uade.tpo.marketplace.repository.UserRepository;
import com.uade.tpo.marketplace.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;


    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = this.orderRepository.findAll();

        if (orders.isEmpty()) throw new ResourceNotFoundException("There are no orders");

        return getOrderDTOS(orders);
    }
    @Override
    public OrderDTO getOrderById(long id) {
        Order order = this.orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        OrderDTO orderDTO = OrderMapper.INSTANCE.orderToOrderDTO(order);

        mappingOrderProducts(orderDTO,order);

        return orderDTO;
    }

    @Override
    public List<OrderDTO> getCurrentUserOrders(String authHeader) {
        String email = this.getEmailFromAuthHeader(authHeader);

        User user = getUser(email);

        List<Order> orders = this.orderRepository.findAllByUser(user);

        if (orders.isEmpty()) throw new ResourceNotFoundException("This user has no orders");

        return getOrderDTOS(orders);

    }

    @Override
    public OrderDTO getCurrentUserOrderById(String authHeader, long id) {
        String email = this.getEmailFromAuthHeader(authHeader);

        User user = getUser(email);

        Order order = orderRepository.findByUserAndId(user,id);

        if (order == null) throw new ResourceNotFoundException("Order not found");

        OrderDTO orderDTO = OrderMapper.INSTANCE.orderToOrderDTO(order);

        mappingOrderProducts(orderDTO,order);

        return orderDTO;

    }

    private void mappingOrderProducts(OrderDTO orderDTO, Order order)
    {
        List<OrderProductDTO> orderProductDTOS = new ArrayList<>();

        for (OrderProduct orderProduct: order.getOrderProductList())
        {
            OrderProductDTO dto = OrderMapper.INSTANCE.orderProductAndProductToOrderProductDTO
                    (orderProduct,orderProduct.getProduct());
            orderProductDTOS.add(dto);
        }
        orderDTO.setOrderProductList(orderProductDTOS);
    }

    private User getUser (String email) {
        Optional<User> user = this.userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("We could not find a user with the given id");
        }
        return user.get();
    }

    private String getEmailFromAuthHeader(String authHeader) {
        String jwt = authHeader.substring(7);

        return jwtService.extractUsername(jwt);
    }
    private List<OrderDTO> getOrderDTOS(List<Order> orders) {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order: orders)
        {
            OrderDTO dto = OrderMapper.INSTANCE.orderToOrderDTO(order);
            mappingOrderProducts(dto,order);
            orderDTOS.add(dto);
        }
        return orderDTOS;
    }

}

package com.uade.tpo.marketplace.mapper;

import com.uade.tpo.marketplace.dto.OrderDTO;
import com.uade.tpo.marketplace.dto.OrderProductDTO;
import com.uade.tpo.marketplace.entity.Order;
import com.uade.tpo.marketplace.entity.OrderProduct;
import com.uade.tpo.marketplace.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "name", source = "order.user.name")
    @Mapping(target = "lastName", source = "order.user.lastName")
    OrderDTO orderToOrderDTO (Order order);

    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "quantity", source = "orderProduct.quantity")
    OrderProductDTO orderProductAndProductToOrderProductDTO (OrderProduct orderProduct, Product product);
}

package com.uade.tpo.marketplace.mapper;

import com.uade.tpo.marketplace.entity.Checkout;
import com.uade.tpo.marketplace.entity.CheckoutProduct;
import com.uade.tpo.marketplace.entity.Order;
import com.uade.tpo.marketplace.entity.OrderProduct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CheckoutOrderMapper {
    CheckoutOrderMapper INSTANCE = Mappers.getMapper(CheckoutOrderMapper.class);

    Order checkoutToOrder(Checkout checkout);

    OrderProduct checkoutProductToOrderProduct (CheckoutProduct checkoutProduct);
}

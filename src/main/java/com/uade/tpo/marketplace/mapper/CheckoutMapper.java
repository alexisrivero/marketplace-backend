package com.uade.tpo.marketplace.mapper;

import com.uade.tpo.marketplace.dto.CheckoutDTO;
import com.uade.tpo.marketplace.dto.CheckoutProductDTO;
import com.uade.tpo.marketplace.entity.Checkout;
import com.uade.tpo.marketplace.entity.CheckoutProduct;
import com.uade.tpo.marketplace.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CheckoutMapper {

    CheckoutMapper INSTANCE = Mappers.getMapper(CheckoutMapper.class);

    @Mapping(target = "email", source = "checkout.user.email")
    @Mapping(target = "name", source = "checkout.user.name")
    @Mapping(target = "lastName", source = "checkout.user.lastName")
    CheckoutDTO checkoutToCheckoutDTO(Checkout checkout);

    @Mapping(target = "id", source = "product.id")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "brand", source = "product.brand")
    @Mapping(target = "category", source = "product.category")
    @Mapping(target = "description", source = "product.description")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "quantity", source = "checkoutProduct.quantity")
    @Mapping(target = "imageRoute", source = "product.imageRoute")
    CheckoutProductDTO checkoutProductAndProductToProductCheckoutDTO (CheckoutProduct checkoutProduct, Product product);
}

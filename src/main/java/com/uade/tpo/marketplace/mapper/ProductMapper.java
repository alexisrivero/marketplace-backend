package com.uade.tpo.marketplace.mapper;

import com.uade.tpo.marketplace.dto.ProductDTO;
import com.uade.tpo.marketplace.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface ProductMapper {
    ProductDTO productToProductDTO (Product product);

    List<ProductDTO> productsToProductDTOS (List<Product> products);
}

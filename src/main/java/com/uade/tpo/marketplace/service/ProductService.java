package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.dto.CategoryDTO;
import com.uade.tpo.marketplace.dto.ProductDTO;
import com.uade.tpo.marketplace.entity.Product;

import java.util.List;

public interface ProductService {

    Product addProduct(Product product);

    String deleteProduct(long id);

    List<ProductDTO> getAllProducts();

    List<Product> getProductsByFilter(String brand, String category, Double minPrice, Double maxPrice);

    ProductDTO getProduct(long id);

    Product updateProduct(long id, Product product);

    List<ProductDTO> getAllProductsByBrand(String brand);

    List<ProductDTO> getAllProductsByCategory(String category);

    List<CategoryDTO> getCategories();
}

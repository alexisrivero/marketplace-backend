package com.uade.tpo.marketplace.service.implementation;

import com.uade.tpo.marketplace.dto.ProductDTO;
import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.exceptions.ResourceNotFoundException;
import com.uade.tpo.marketplace.repository.ProductRepository;
import com.uade.tpo.marketplace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public Product addProduct(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public String deleteProduct(long id) {
        Optional<Product> productToDelete = this.productRepository.findById(id);

        if (productToDelete.isEmpty()) {
            throw new ResourceNotFoundException("There is no product with this id");
        }

        this.productRepository.delete(productToDelete.get());

        return "Product deleted successfully";
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = this.productRepository.findAll();

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("There are no products available");
        }

        return products.stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProduct(long id) {
        Optional<Product> product = this.productRepository.findById(id);

        if (product.isEmpty()) {
            throw new ResourceNotFoundException("There is no product with this id");
        }

        return convertToProductDTO(product.get());
    }

    @Override
    public Product updateProduct(long id, Product product) {
        Optional<Product> productToUpdate = this.productRepository.findById(id);

        if (productToUpdate.isEmpty()) {
            throw new ResourceNotFoundException("There is no product with this id");
        }

        productToUpdate.get().setName(product.getName());
        productToUpdate.get().setBrand(product.getBrand());
        productToUpdate.get().setCategory(product.getCategory());
        productToUpdate.get().setDescription(product.getDescription());
        productToUpdate.get().setPrice(product.getPrice());
        productToUpdate.get().setStock(product.getStock());
        productToUpdate.get().setImageRoute(product.getImageRoute());

        this.productRepository.save(productToUpdate.get());

        return productToUpdate.get();
    }

    @Override
    public List<ProductDTO> getAllProductsByBrand(String brand) {
        List<Product> products = this.productRepository.findByBrand();

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("There are no products available");
        }

        return products.stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllProductsByCategory(String category) {
        List<Product> products = this.productRepository.findByCategory();

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("There are no products available");
        }

        return products.stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO convertToProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setBrand(product.getBrand());
        dto.setCategory(product.getCategory());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setImageRoute(product.getImageRoute());
        return dto;
    }


}

package com.uade.tpo.marketplace.service.implementation;

import com.uade.tpo.marketplace.dto.ProductDTO;
import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.exceptions.ResourceNotFoundException;
import com.uade.tpo.marketplace.mapper.ProductMapper;
import com.uade.tpo.marketplace.repository.ProductRepository;
import com.uade.tpo.marketplace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

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

        return productMapper.productsToProductDTOS(products);
    }

    @Override
    public ProductDTO getProduct(long id) {
        Optional<Product> product = this.productRepository.findById(id);

        if (product.isEmpty()) {
            throw new ResourceNotFoundException("There is no product with this id");
        }

        return productMapper.productToProductDTO(product.get());
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
        List<Product> products = this.productRepository.findByBrand(brand.toLowerCase());

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("There are no products available");
        }

        return productMapper.productsToProductDTOS(products);
    }

    @Override
    public List<ProductDTO> getAllProductsByCategory(String category) {
        List<Product> products = this.productRepository.findByCategory(category.toLowerCase());

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("There are no products available");
        }

        return productMapper.productsToProductDTOS(products);
    }


}

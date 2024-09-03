package com.uade.tpo.marketplace.controller;

import com.uade.tpo.marketplace.dto.ProductDTO;
import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping()
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product createdProduct = this.productService.addProduct(product);
        return new ResponseEntity<>(product, HttpStatusCode.valueOf(200));
    }

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> getProducts() {
        List<ProductDTO> productDTOList = this.productService.getAllProducts();
        return new ResponseEntity<>(productDTOList, HttpStatusCode.valueOf(200));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable long id) {
        ProductDTO product = this.productService.getProduct(id);
        return new ResponseEntity<>(product, HttpStatusCode.valueOf(200));
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        return new ResponseEntity<>(this.productService.deleteProduct(id), HttpStatusCode.valueOf(200));
    }

    @PutMapping()
    public ResponseEntity<Product> updateProduct(@PathVariable long id, Product product) {
        return new ResponseEntity<>(this.productService.updateProduct(id, product), HttpStatusCode.valueOf(200));
    }

}

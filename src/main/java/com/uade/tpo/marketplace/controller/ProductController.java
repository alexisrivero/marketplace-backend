package com.uade.tpo.marketplace.controller;

import com.uade.tpo.marketplace.dto.CategoryDTO;
import com.uade.tpo.marketplace.dto.ProductDTO;
import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.exceptions.InvalidCategoryException;
import com.uade.tpo.marketplace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
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

    @GetMapping("/filter")
    public List<Product> getProductsByFilter(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        return productService.getProductsByFilter(brand, category, minPrice, maxPrice);
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<ProductDTO>> getAllProductsByBrand(@PathVariable String brand) {
        List<ProductDTO> productDTOList = this.productService.getAllProductsByBrand(brand);
        return new ResponseEntity<>(productDTOList, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        List<CategoryDTO> categoryList = this.productService.getCategories();
        return new ResponseEntity<>(categoryList, HttpStatusCode.valueOf(200));
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getAllProductsByCategory(@PathVariable("category") String category) {
        List<ProductDTO> productDTOList = this.productService.getAllProductsByCategory(category);
        return new ResponseEntity<>(productDTOList, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable long id) {
        ProductDTO product = this.productService.getProduct(id);
        return new ResponseEntity<>(product, HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        return new ResponseEntity<>(this.productService.deleteProduct(id), HttpStatusCode.valueOf(200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product product) {
        return new ResponseEntity<>(this.productService.updateProduct(id, product), HttpStatusCode.valueOf(200));
    }

}

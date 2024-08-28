package com.uade.tpo.marketplace.service.implementation;

import com.uade.tpo.marketplace.repository.ProductRepository;
import com.uade.tpo.marketplace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
}

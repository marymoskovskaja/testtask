package com.testtask.delivery.controllers.rest;

import com.testtask.delivery.accessingdatamysql.model.Product;
import com.testtask.delivery.accessingdatamysql.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для получения перечня продукции.
 */
@RestController
public class GetProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public Iterable<Product> products() {
        return productRepository.findAll();
    }
}

package com.testtask.delivery.accessingdatamysql.repo;

import com.testtask.delivery.accessingdatamysql.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}

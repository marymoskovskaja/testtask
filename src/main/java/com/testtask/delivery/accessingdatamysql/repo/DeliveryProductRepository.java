package com.testtask.delivery.accessingdatamysql.repo;

import com.testtask.delivery.accessingdatamysql.model.DeliveryProduct;
import org.springframework.data.repository.CrudRepository;

public interface DeliveryProductRepository extends CrudRepository<DeliveryProduct, Long> {
}

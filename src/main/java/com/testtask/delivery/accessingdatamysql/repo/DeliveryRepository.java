package com.testtask.delivery.accessingdatamysql.repo;

import com.testtask.delivery.accessingdatamysql.model.Delivery;
import org.springframework.data.repository.CrudRepository;

public interface DeliveryRepository extends CrudRepository<Delivery, Long> {
}

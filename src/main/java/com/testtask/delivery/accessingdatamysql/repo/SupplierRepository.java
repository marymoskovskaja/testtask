package com.testtask.delivery.accessingdatamysql.repo;

import com.testtask.delivery.accessingdatamysql.model.Supplier;
import org.springframework.data.repository.CrudRepository;

public interface SupplierRepository extends CrudRepository<Supplier, Long> {
}

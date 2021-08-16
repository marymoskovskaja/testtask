package com.testtask.delivery.controllers.rest;

import com.testtask.delivery.accessingdatamysql.model.Supplier;
import com.testtask.delivery.accessingdatamysql.repo.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для получения перечня поставщиков.
 */
@RestController
public class GetSupplierController {

    @Autowired
    private SupplierRepository supplierRepository;

    @GetMapping("/suppliers")
    public Iterable<Supplier> suppliers() {
        return supplierRepository.findAll();
    }
}

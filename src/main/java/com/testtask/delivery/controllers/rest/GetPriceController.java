package com.testtask.delivery.controllers.rest;

import com.testtask.delivery.Utils.DateUtils;
import com.testtask.delivery.Utils.PriceUtils;
import com.testtask.delivery.accessingdatamysql.model.Price;
import com.testtask.delivery.accessingdatamysql.model.Product;
import com.testtask.delivery.accessingdatamysql.repo.ProductRepository;
import com.testtask.delivery.accessingdatamysql.model.Supplier;
import com.testtask.delivery.accessingdatamysql.repo.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Контроллер для получения цены.
 */
@RestController
public class GetPriceController {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/price")
    public Price price(@RequestParam("supplier") Long supplierId,
                       @RequestParam("product") Long productId,
                       @RequestParam("date") String date) {
        LocalDate deliveryDate = DateUtils.parseDate(date);

        if (deliveryDate == null) {
            return null;
        }

        Optional<Supplier> supplier = supplierRepository.findById(supplierId);
        Optional<Product> product = productRepository.findById(productId);

        if (supplier.isPresent() && product.isPresent()) {
            return PriceUtils.calcPrice(entityManager, product.get(), supplier.get(), deliveryDate);
        }

        return null;
    }
}

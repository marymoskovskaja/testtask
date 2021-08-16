package com.testtask.delivery.controllers.rest;

import com.testtask.delivery.Utils.DateUtils;
import com.testtask.delivery.Utils.PriceUtils;
import com.testtask.delivery.accessingdatamysql.model.Delivery;
import com.testtask.delivery.accessingdatamysql.model.DeliveryProduct;
import com.testtask.delivery.accessingdatamysql.repo.DeliveryProductRepository;
import com.testtask.delivery.accessingdatamysql.repo.DeliveryRepository;
import com.testtask.delivery.accessingdatamysql.model.Price;
import com.testtask.delivery.accessingdatamysql.model.Product;
import com.testtask.delivery.accessingdatamysql.repo.ProductRepository;
import com.testtask.delivery.accessingdatamysql.model.Supplier;
import com.testtask.delivery.accessingdatamysql.repo.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер для добавления записи поставки.
 */
@RestController
public class AddDeliveryController {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryProductRepository deliveryProductRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping(value = "/add-delivery")
    public ResponseEntity<Object> deliveryAdd(@RequestParam("number") String number,
                                           @RequestParam("date") String date,
                                           @RequestParam("supplier") Long supplierId,
                                           @RequestParam("purchaseProducts") List<Long> purchaseProducts,
                                           @RequestParam("purchaseSizes") List<Long> purchaseSizes
    ) {
        LocalDate deliveryDate = DateUtils.parseDate(date);

        if (deliveryDate == null) {
            return new ResponseEntity<>(
                    "Произошла ошибка при обработке даты поставки",
                    new HttpHeaders(), HttpStatus.FORBIDDEN);
        }

        Optional<Supplier> deliverySupplier = supplierRepository.findById(supplierId);

        if (deliverySupplier.isPresent()) {
            Delivery delivery = new Delivery(number, deliverySupplier.get(), deliveryDate);
            List<DeliveryProduct> deliveryProducts = createDeliveryDetails(delivery, purchaseProducts, purchaseSizes);

            if (deliveryProducts.size() < purchaseProducts.size()) {
                return new ResponseEntity<>(
                        "Произошла ошибка при сохранении данных: указанный продукт или его цена не найдены в БД",
                        new HttpHeaders(), HttpStatus.FORBIDDEN);
            }

            deliveryRepository.save(delivery);
            deliveryProductRepository.saveAll(deliveryProducts);
        } else {
            return new ResponseEntity<>(
                    "Произошла ошибка при сохранении данных: указанный поставщик не найден в БД",
                    new HttpHeaders(), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>("Запись была успешно сохранена",
                new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Подготовка и создание перечня товаров поставки для добавления.
     * @param delivery поставка.
     * @param purchaseProducts список выбранных продуктов.
     * @param purchaseSizes объемы поставок.
     * @return список доставляемых товаров.
     */
    private List<DeliveryProduct> createDeliveryDetails(Delivery delivery, List<Long> purchaseProducts,
                                                        List<Long> purchaseSizes) {
        List<DeliveryProduct> result = new ArrayList<>();

        for (int i = 0; i < purchaseProducts.size(); i++) {
            Optional<Product> product = productRepository.findById(purchaseProducts.get(i));

            if (product.isPresent()) {
                try {
                    Price price = PriceUtils.calcPrice(entityManager, product.get(),
                            delivery.getSupplier(), delivery.getDate());
                    Long size = purchaseSizes.get(i);
                    Double sum = PriceUtils.calcDeliveryProductSum(price.getPrice(), size);

                    result.add(new DeliveryProduct(product.get(), delivery, size, sum));
                } catch (NoResultException e) {
                    return result;
                }
            } else {
                break;
            }
        }

        return result;
    }
}

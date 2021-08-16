package com.testtask.delivery.Utils;

import com.testtask.delivery.accessingdatamysql.model.Price;
import com.testtask.delivery.accessingdatamysql.model.Product;
import com.testtask.delivery.accessingdatamysql.model.Supplier;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;

/**
 * Утилитный класс для работы с ценами.
 */
public class PriceUtils {

    private PriceUtils() {
    }

    /**
     * Поиск цены товара по информации о поставщике, товаре и дате поставки.
     * @param product товар.
     * @param supplier поставщик.
     * @param date дата поставки.
     * @return цена товара.
     */
    public static Price calcPrice(EntityManager entityManager, Product product, Supplier supplier, LocalDate date) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("from Price where product = :product and supplier = :supplier" +
                " and (:date between startDate and endDate)");
        query.setParameter("product", product);
        query.setParameter("supplier", supplier);
        query.setParameter("date", date);

        return (Price) query.getSingleResult();
    }

    /**
     * Расчет суммы поставки для конкретного товара.
     * @param price цена товара в прайс-листе.
     * @param size объем.
     * @return сумма.
     */
    public static Double calcDeliveryProductSum(Double price, Long size) {
        return price * size;
    }
}

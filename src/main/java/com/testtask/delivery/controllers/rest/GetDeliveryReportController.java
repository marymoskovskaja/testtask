package com.testtask.delivery.controllers.rest;

import com.testtask.delivery.Utils.DateUtils;
import org.hibernate.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

/**
 * Контроллер для получения данных по поставкам товаров в разрезе продавцов.
 */
@RestController
public class GetDeliveryReportController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/deliveryReport")
    public List<Object> suppliers(@RequestParam String dateFrom,
                                               @RequestParam String dateTo) {
        LocalDate startDate = DateUtils.parseDate(dateFrom);
        LocalDate endDate = DateUtils.parseDate(dateTo);

        startDate = (startDate != null) ? startDate : LocalDate.of(1900, 1, 1);
        endDate = (endDate != null) ? endDate : LocalDate.of(3000, 1, 1);

        return getDeliveryReport(startDate, endDate);
    }

    /**
     * Получение отчетных данных за запрашиваемый период.
     * @param startDate начало периода.
     * @param endDate конец периода.
     * @return данные отчета.
     */
    private List<Object> getDeliveryReport(LocalDate startDate, LocalDate endDate) {
        Session session = entityManager.unwrap(Session.class);

        Query query = session.createQuery("SELECT d.supplier, dp.product, sum(dp.purchaseSum), dp.size " +
                "FROM Delivery d \n" +
                "INNER JOIN DeliveryProduct dp ON (d.id = dp.delivery) \n" +
                "WHERE d.date >= :started and d.date <= :ended " +
                "GROUP BY d.supplier, dp.product ");

        query.setParameter("started", startDate);
        query.setParameter("ended", endDate);

        return query.getResultList();
    }

}

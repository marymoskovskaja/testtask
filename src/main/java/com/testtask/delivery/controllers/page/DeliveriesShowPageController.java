package com.testtask.delivery.controllers.page;

import com.testtask.delivery.accessingdatamysql.repo.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер страницы с отчетом по поставкам.
 */
@Controller
public class DeliveriesShowPageController {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @GetMapping({"/deliveries", "/report"})
    public String deliveries(Model model) {
        model.addAttribute("records", deliveryRepository.findAll());

        return "deliveries";
    }
}

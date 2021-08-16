package com.testtask.delivery.controllers.page;

import com.testtask.delivery.accessingdatamysql.repo.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для страницы регистрации поставок.
 */
@Controller
public class DeliveryAddPageController {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @GetMapping({"/","/application"})
    public String newDelivery(Model model) {
        return "newDelivery";
    }

}

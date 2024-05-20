package com.ivoriandev.saveursolidaire.models.listeners;

import com.ivoriandev.saveursolidaire.models.Order;
import com.ivoriandev.saveursolidaire.services.BasketService;
import jakarta.persistence.PostPersist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderEntityListener {
    private static BasketService basketService;

    @Autowired
    public void setBasketService(BasketService basketService) {
        OrderEntityListener.basketService = basketService;
    }

    @PostPersist
    public void postPersist(Order order) {
        log.info("Order with reference {} has been created", order.getReference());
        basketService.updateBasket(order);
    }
}

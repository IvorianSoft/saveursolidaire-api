package com.ivoriandev.saveursolidaire.rest.controllers;

import com.ivoriandev.saveursolidaire.models.Basket;
import com.ivoriandev.saveursolidaire.services.BasketService;
import com.ivoriandev.saveursolidaire.utils.constants.AuthoritiesConstants;
import com.ivoriandev.saveursolidaire.utils.dto.basket.CreateBasketDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ivoriandev.saveursolidaire.utils.constants.AppConstant.API_BASE_URL_V1;

@RestController
@RequestMapping(API_BASE_URL_V1 + "/baskets")
@Slf4j
public class BasketController {
    @Autowired
    private BasketService basketService;

    @GetMapping(value = "", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<List<Basket>> all() {
        return ResponseEntity.ok(basketService.all());
    }

    @GetMapping(value = "/{id}", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.SELLER + "')")
    public ResponseEntity<Basket> read(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(basketService.read(id));
    }

    @GetMapping(value = "/store/{storeId}", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.SELLER + "')")
    public ResponseEntity<List<Basket>> allByStore(@PathVariable("storeId") Integer storeId) {
        return ResponseEntity.ok(basketService.allByStore(storeId));
    }

    @PostMapping(value = "", consumes = {"application/json"}, produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.SELLER + "')")
    public ResponseEntity<Basket> create(@RequestBody @Validated CreateBasketDto basket) {
        return ResponseEntity.ok(basketService.create(basket));
    }

    @PutMapping(value = "/{id}", consumes = {"application/json"}, produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.SELLER + "')")
    public ResponseEntity<Basket> update(@RequestBody @Validated Basket basket, @PathVariable("id") Integer id) {
        return ResponseEntity.ok(basketService.update(id, basket));
    }

    @PutMapping(value = "/{id}/quantity", consumes = {"application/json"}, produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.SELLER + "')")
    public ResponseEntity<Basket> updateQuantity(@PathVariable("id") Integer id, @RequestParam("quantity") Integer quantity) {
        return ResponseEntity.ok(basketService.updateQuantity(id, quantity));
    }

    @PutMapping(value = "/{id}/active-status", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.SELLER + "')")
    public ResponseEntity<Basket> updateActiveStatus(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(basketService.updateActiveStatus(id));
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        basketService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

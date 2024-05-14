package com.ivoriandev.saveursolidaire.rest.controllers;

import com.ivoriandev.saveursolidaire.models.Basket;
import com.ivoriandev.saveursolidaire.services.BasketService;
import com.ivoriandev.saveursolidaire.utils.constants.AuthoritiesConstants;
import com.ivoriandev.saveursolidaire.utils.dto.basket.BasketDto;
import com.ivoriandev.saveursolidaire.utils.dto.basket.CreateBasketDto;
import com.ivoriandev.saveursolidaire.utils.dto.geospatial.SearchDto;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get all baskets")
    @GetMapping(value = "", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<List<Basket>> all() {
        return ResponseEntity.ok(basketService.all());
    }

    @Operation(summary = "Get basket by id")
    @GetMapping(value = "/{id}", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.SELLER + "')")
    public ResponseEntity<Basket> read(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(basketService.read(id));
    }

    @Operation(summary = "Get all baskets by store id")
    @GetMapping(value = "/store/{storeId}", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.SELLER + "')")
    public ResponseEntity<List<Basket>> allByStore(@PathVariable("storeId") Integer storeId) {
        return ResponseEntity.ok(basketService.allByStore(storeId));
    }

    @Operation(summary = "Create a basket")
    @PostMapping(value = "", consumes = {"application/json"}, produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.SELLER + "')")
    public ResponseEntity<Basket> create(@RequestBody @Validated CreateBasketDto basket) {
        return ResponseEntity.ok(basketService.create(basket));
    }

    @Operation(summary = "Update a basket, only name, description, price, quantity and note can be updated.")
    @PutMapping(value = "/{id}", consumes = {"application/json"}, produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.SELLER + "')")
    public ResponseEntity<Basket> update(@RequestBody @Validated Basket basket, @PathVariable("id") Integer id) {
        return ResponseEntity.ok(basketService.update(id, basket));
    }

    @Operation(summary = "Update quantity of a basket")
    @PutMapping(value = "/{id}/quantity/{quantity}", consumes = {"application/json"}, produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.SELLER + "')")
    public ResponseEntity<Basket> updateQuantity(@PathVariable("id") Integer id, @PathVariable("quantity") Integer quantity) {
        return ResponseEntity.ok(basketService.updateQuantity(id, quantity));
    }

    @Operation(summary = "Update is_active status of a basket")
    @PutMapping(value = "/{id}/active-status", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.SELLER + "')")
    public ResponseEntity<Basket> updateActiveStatus(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(basketService.updateActiveStatus(id));
    }

    @Operation(summary = "Delete a basket by id")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        basketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search for baskets")
    @GetMapping(value = "/search", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.SELLER + "', '" + AuthoritiesConstants.USER + "', '" + AuthoritiesConstants.CUSTOMER + "')")
    public ResponseEntity<List<BasketDto>> search(
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam(value = "radius", required = false) Integer radius
    ) {
        SearchDto searchDto = SearchDto.builder()
                .latitude(latitude)
                .longitude(longitude)
                .radius(radius == null ? 0 : radius)
                .build();
        return ResponseEntity.ok(basketService.getAllFromLocation(searchDto));
    }
}

package com.ivoriandev.saveursolidaire.rest.controllers;

import com.ivoriandev.saveursolidaire.models.Order;
import com.ivoriandev.saveursolidaire.services.OrderService;
import com.ivoriandev.saveursolidaire.utils.constants.AuthoritiesConstants;
import com.ivoriandev.saveursolidaire.utils.dto.order.CreateOrderDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ivoriandev.saveursolidaire.utils.constants.AppConstant.API_BASE_URL_V1;

@RestController
@RequestMapping(API_BASE_URL_V1 + "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Get all orders")
    @GetMapping(value = "", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "' , '" + AuthoritiesConstants.CUSTOMER + "')")
    public ResponseEntity<List<Order>> all() {
        return ResponseEntity.ok(orderService.all());
    }

    @Operation(summary = "Get all orders by store")
    @GetMapping(value = "/store/{storeId}", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.SELLER + "')")
    public ResponseEntity<List<Order>> allByStore(@PathVariable("storeId") Integer storeId) {
        return ResponseEntity.ok(orderService.allByStore(storeId));
    }

    @Operation(summary = "Get order by id")
    @GetMapping(value = "/{id}", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.SELLER + "' , '" + AuthoritiesConstants.CUSTOMER + "')")
    public ResponseEntity<Order> read(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(orderService.read(id));
    }

    @Operation(summary = "Create an order")
    @PostMapping(value = "", consumes = {"application/json"}, produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.CUSTOMER + "')")
    public ResponseEntity<Order> create(@RequestBody @Validated CreateOrderDto order) {
        return ResponseEntity.ok(orderService.create(order));
    }

    @Operation(summary = "Update an order, only isPaid and isRecovered can be updated.")
    @PutMapping(value = "/{id}/status", consumes = {"application/json"}, produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.CUSTOMER + "')")
    public ResponseEntity<Order> updateStatus(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(orderService.updateStatus(id));
    }

    @Operation(summary = "Delete an order")
    @DeleteMapping(value = "/{id}", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all orders by user and isPaid true")
    @GetMapping(value = "/user/is-paid", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.CUSTOMER + "')")
    public ResponseEntity<List<Order>> allByUserAndIsPaid() {
        return ResponseEntity.ok(orderService.allByUserAndIsPaidTrue());
    }

    @Operation(summary = "Get all orders by user and isPaid false")
    @GetMapping(value = "/user/is-not-paid", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.CUSTOMER + "')")
    public ResponseEntity<List<Order>> allByUserAndIsPaidFalse() {
        return ResponseEntity.ok(orderService.allByUserAndIsPaidFalse());
    }
}

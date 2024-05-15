package com.ivoriandev.saveursolidaire.rest.controllers;

import com.ivoriandev.saveursolidaire.models.Order;
import com.ivoriandev.saveursolidaire.services.OrderService;
import com.ivoriandev.saveursolidaire.utils.constants.AuthoritiesConstants;
import com.ivoriandev.saveursolidaire.utils.dto.order.OrderDto;
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
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<List<Order>> all() {
        return ResponseEntity.ok(orderService.all());
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
    public ResponseEntity<Order> create(@RequestBody @Validated OrderDto order) {
        return ResponseEntity.ok(orderService.create(order));
    }

    @Operation(summary = "Update an order, only reference, totalPrice, isPaid, isRecovered and paymentMethod can be updated.")
    @PutMapping(value = "/{id}", consumes = {"application/json"}, produces = {"application/json;charset=UTF-8"})
@PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.CUSTOMER + "')")
    public ResponseEntity<Order> update(@RequestBody @Validated OrderDto order, @PathVariable("id") Integer id) {
        return ResponseEntity.ok(orderService.update(id, order));
    }

    @Operation(summary = "Delete an order")
    @DeleteMapping(value = "/{id}", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all orders by user id")
    @GetMapping(value = "/user/{userId}", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.CUSTOMER + "')")
    public ResponseEntity<List<Order>> allByUser(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(orderService.allByUser(userId));
    }

    @Operation(summary = "Get all orders by seller id")
    @GetMapping(value = "/seller/{sellerId}", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.SELLER + "')")
    public ResponseEntity<List<Order>> allBySeller(@PathVariable("sellerId") Integer sellerId) {
        return ResponseEntity.ok(orderService.allBySeller(sellerId));
    }

    @Operation(summary = "Get all orders by user id and isPaid true")
    @GetMapping(value = "/user/{userId}/is-paid", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.CUSTOMER + "')")
    public ResponseEntity<List<Order>> allByUserAndIsPaid(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(orderService.allByUserAndIsPaidTrue(userId));
    }
}

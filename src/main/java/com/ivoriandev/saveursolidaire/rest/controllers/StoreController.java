package com.ivoriandev.saveursolidaire.rest.controllers;

import com.ivoriandev.saveursolidaire.models.Store;
import com.ivoriandev.saveursolidaire.models.embedded.Location;
import com.ivoriandev.saveursolidaire.services.StoreService;
import com.ivoriandev.saveursolidaire.utils.constants.AuthoritiesConstants;
import com.ivoriandev.saveursolidaire.utils.dto.store.StoreDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ivoriandev.saveursolidaire.utils.constants.AppConstant.API_BASE_URL_V1;

@RestController
@RequestMapping(API_BASE_URL_V1 + "/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Operation(summary = "Get all stores")
    @GetMapping(value = "", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('"+ AuthoritiesConstants.ADMIN +"', '"+ AuthoritiesConstants.SELLER +"')")
    public ResponseEntity<List<Store>> all() {
        return ResponseEntity.ok(storeService.all());
    }

    @Operation(summary = "Get store by id")
    @GetMapping(value = "/{id}", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('"+ AuthoritiesConstants.ADMIN +"', '"+ AuthoritiesConstants.SELLER +"')")
    public ResponseEntity<Store> read(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(storeService.read(id));
    }

    @Operation(summary = "Create a store")
    @PostMapping(value = "", consumes = {"application/json"}, produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.SELLER + "')")
    public ResponseEntity<Store> create(@RequestBody @Validated Store store) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storeService.create(store));
    }

    @Operation(summary = "Update a store, only name, description and contact can be updated.")
    @PutMapping(value = "/{id}", consumes = {"application/json"}, produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "', '" + AuthoritiesConstants.SELLER + "')")
    public ResponseEntity<Store> update(@RequestBody @Validated Store store, @RequestParam("id") Integer id) {
        return ResponseEntity.ok(storeService.update(id, store));
    }

    @Operation(summary = "Delete a store by id")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        storeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update is active status of a store")
    @PutMapping(value = "/{id}/status", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<Store> updateStatus(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(storeService.updateIsActive(id));
    }

    @Operation(summary = "Update location of a store")
    @PutMapping(value = "/{id}/location", consumes = {"application/json"}, produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<Store> updateLocation(@RequestBody @Validated Location location, @PathVariable("id") Integer id) {
        return ResponseEntity.ok(storeService.updateLocation(id, location));
    }
}

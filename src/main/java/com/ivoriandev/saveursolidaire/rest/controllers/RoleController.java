package com.ivoriandev.saveursolidaire.rest.controllers;

import com.ivoriandev.saveursolidaire.models.Role;
import com.ivoriandev.saveursolidaire.services.RoleService;
import com.ivoriandev.saveursolidaire.utils.constants.AuthoritiesConstants;
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
@RequestMapping(API_BASE_URL_V1 + "/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Operation(summary = "Get all roles")
    @GetMapping(value = "", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('"+ AuthoritiesConstants.ADMIN +"')")
    public ResponseEntity<List<Role>> all() {
        return ResponseEntity.ok(roleService.all());
    }

    @Operation(summary = "Get a role by id")
    @GetMapping(value = "/{id}", produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('"+ AuthoritiesConstants.ADMIN +"')")
    public ResponseEntity<Role> read(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(roleService.read(id));
    }

    @Operation(summary = "Create a role")
    @PostMapping(value = "", consumes = {"application/json"}, produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Role> create(@RequestBody @Validated Role role) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.create(role));
    }

    @Operation(summary = "Update a role")
    @PutMapping(value = "/{id}", consumes = {"application/json"}, produces = {"application/json;charset=UTF-8"})
    @PreAuthorize("hasAnyRole('"+ AuthoritiesConstants.ADMIN +"')")
    public ResponseEntity<Role> update(@RequestBody @Validated Role role, @PathVariable("id") Integer id) {
        return ResponseEntity.ok(roleService.update(id, role));
    }

    @Operation(summary = "Delete a role by id")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('"+ AuthoritiesConstants.ADMIN +"')")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

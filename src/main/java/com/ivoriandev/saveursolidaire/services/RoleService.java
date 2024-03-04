package com.ivoriandev.saveursolidaire.services;

import com.ivoriandev.saveursolidaire.exceptions.BadRequestException;
import com.ivoriandev.saveursolidaire.exceptions.NotFoundException;
import com.ivoriandev.saveursolidaire.models.Role;
import com.ivoriandev.saveursolidaire.repositories.RoleRepository;
import com.ivoriandev.saveursolidaire.services.interfaces.CrudService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements CrudService<Role> {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role create(Role role) {
        if (exists(role.getName()) != null) {
            throw new BadRequestException("Role already exists");
        }

        role.setName(role.getName().toUpperCase());
        role.setDeletedAt(null);
        return roleRepository.save(role);
    }

    @Override
    public List<Role> all() {
        return roleRepository.findAll();
    }

    @Override
    public Role read(Integer id) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role == null) {
            throw new NotFoundException(String.format("Role with id %d not found", id));
        }
        return role;
    }

    @Override
    public Role update(Integer id, Role role) {
        Role existingRole = read(id);
        existingRole.setName(role.getName().toUpperCase());
        return roleRepository.save(existingRole);
    }

    @Override
    public void delete(Integer id) {
        Role role = read(id);
        roleRepository.delete(role);
    }

    private Role exists(String name) {
        Optional<Role> role = roleRepository.findByNameIgnoreCase(name);
        return role.orElse(null);
    }

    @NotNull
    public Role getDefaultRole() {
        return getRoleByName("CUSTOMER");
    }

    private Role getRoleByName(String name) {
        Role role = roleRepository.findByNameIgnoreCase(name).orElse(null);
        if (role == null) {
            throw new NotFoundException(String.format("Role %s not found", name));
        }
        return role;
    }

    public Role getSellerRole() {
        return getRoleByName("SELLER");
    }
}

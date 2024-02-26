package com.ivoriandev.saveursolidaire.services;

import com.ivoriandev.saveursolidaire.exceptions.NotFoundException;
import com.ivoriandev.saveursolidaire.models.User;
import com.ivoriandev.saveursolidaire.repositories.UserRepository;
import com.ivoriandev.saveursolidaire.services.interfaces.CrudService;
import com.ivoriandev.saveursolidaire.utils.Utilities;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements CrudService<User> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> all() {
        return userRepository.findAll();
    }

    @Override
    public User read(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        return user;
    }

    @Override
    public User update(User user) {
        User existingUser = read(user.getId());
        existingUser.setName(user.getName());
        existingUser.setContact(user.getContact());
        existingUser.setEmail(user.getEmail());

        return userRepository.save(existingUser);
    }

    @Override
    public void delete(Integer id) {
        User user = read(id);
        user.setDeletedAt(Utilities.getCurrentDate());
        userRepository.save(user);
    }

    @NotNull
    public boolean existsByEmail(String email) {
        return userRepository.findFirstByEmailIgnoreCase(email).isPresent();
    }

    @NotNull
    public boolean existByContact(String contact) {
        return userRepository.findFirstByContact(contact).isPresent();
    }

    public User findByEmail(String email) {
        return userRepository.findFirstByEmailIgnoreCase(email).orElse(null);
    }
}

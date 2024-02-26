package com.ivoriandev.saveursolidaire.repositories;

import com.ivoriandev.saveursolidaire.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findFirstByEmailIgnoreCase(String email);

    Optional<User> findFirstByContact(String contact);
}
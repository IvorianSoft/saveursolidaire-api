package com.ivoriandev.saveursolidaire.repositories;

import com.ivoriandev.saveursolidaire.models.UserStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStoreRepository extends JpaRepository<UserStore, Integer> {
}
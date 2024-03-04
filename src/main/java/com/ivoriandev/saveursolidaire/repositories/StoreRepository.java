package com.ivoriandev.saveursolidaire.repositories;

import com.ivoriandev.saveursolidaire.models.Store;
import com.ivoriandev.saveursolidaire.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface StoreRepository extends JpaRepository<Store, Integer> {
    List<Store> findAllByIdIn(Set<Integer> ids);
}
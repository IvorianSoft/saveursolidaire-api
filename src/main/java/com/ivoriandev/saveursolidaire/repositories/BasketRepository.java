package com.ivoriandev.saveursolidaire.repositories;

import com.ivoriandev.saveursolidaire.models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
    List<Basket> findAllByStoreId(Integer storeId);
}
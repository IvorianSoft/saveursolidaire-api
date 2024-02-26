package com.ivoriandev.saveursolidaire.repositories;

import com.ivoriandev.saveursolidaire.models.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
}
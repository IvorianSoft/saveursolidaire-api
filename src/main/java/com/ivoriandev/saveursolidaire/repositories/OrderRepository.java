package com.ivoriandev.saveursolidaire.repositories;

import com.ivoriandev.saveursolidaire.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByUserId(Integer userId);

    List<Order> findAllByStoreId(Integer sellerId);

    List<Order> findAllByUserIdAndIsPaidTrue(Integer userId);

    Boolean existsByReference(String reference);
}

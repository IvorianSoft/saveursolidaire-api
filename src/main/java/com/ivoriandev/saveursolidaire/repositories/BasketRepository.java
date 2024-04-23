package com.ivoriandev.saveursolidaire.repositories;

import com.ivoriandev.saveursolidaire.models.Basket;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
    List<Basket> findAllByStoreId(Integer storeId);

    @Query("SELECT b FROM Basket b WHERE b.store.isActive = true AND within(b.store.geopoint, ?1) = true AND b.quantity > 0 AND b.isActive = true ORDER BY b.price ASC")
    List<Basket> findAllByStore_IsActiveTrueAndStore_GeopointWithinAndQuantityIsGreaterThanAndIsActiveTrueOrderByPriceAsc(Geometry geometry, Integer startQuantity);
}

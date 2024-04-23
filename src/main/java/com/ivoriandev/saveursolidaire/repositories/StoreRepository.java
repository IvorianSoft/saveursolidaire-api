package com.ivoriandev.saveursolidaire.repositories;

import com.ivoriandev.saveursolidaire.models.Store;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface StoreRepository extends JpaRepository<Store, Integer> {
    List<Store> findAllByIdIn(Set<Integer> ids);

    @Query("SELECT s FROM Store s WHERE s.isActive = true AND within(s.geopoint, ?1) = true")
    List<Store> findAllByIsActiveTrueAndGeopointWithin(Geometry geometry);
}

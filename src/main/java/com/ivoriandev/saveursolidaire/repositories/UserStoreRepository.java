package com.ivoriandev.saveursolidaire.repositories;

import com.ivoriandev.saveursolidaire.models.UserStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserStoreRepository extends JpaRepository<UserStore, Integer> {
    List<UserStore> findAllByUserIdAndIsActiveTrue(Integer userId);

    List<UserStore> findAllByStoreIdAndIsActiveTrue(Integer storeId);

    UserStore findFirstByUserIdAndStoreIdAndIsActiveTrue(Integer id, Integer id1);
}
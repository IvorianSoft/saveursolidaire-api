package com.ivoriandev.saveursolidaire.services;

import com.ivoriandev.saveursolidaire.models.Store;
import com.ivoriandev.saveursolidaire.models.User;
import com.ivoriandev.saveursolidaire.models.UserStore;
import com.ivoriandev.saveursolidaire.repositories.UserStoreRepository;
import com.ivoriandev.saveursolidaire.services.interfaces.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserStoreService {
    private final UserStoreRepository userStoreRepository;

    public void attachUserToStore(User user, Store store) {
        UserStore userStore = UserStore.builder()
                .user(user)
                .store(store)
                .isActive(Boolean.TRUE)
                .build();

        userStoreRepository.save(userStore);
    }
}

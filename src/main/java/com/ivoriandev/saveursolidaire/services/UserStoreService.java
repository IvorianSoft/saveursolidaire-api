package com.ivoriandev.saveursolidaire.services;

import com.ivoriandev.saveursolidaire.models.Store;
import com.ivoriandev.saveursolidaire.models.User;
import com.ivoriandev.saveursolidaire.models.UserStore;
import com.ivoriandev.saveursolidaire.repositories.UserStoreRepository;
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

    public List<UserStore> allByUserId(User user) {
        return userStoreRepository.findAllByUserIdAndIsActiveTrue(user.getId());
    }

    public List<UserStore> allByStoreId(Store store) {
        return userStoreRepository.findAllByStoreIdAndIsActiveTrue(store.getId());
    }

    public UserStore getOneByUserIdAndStoreId(User user, Store store) {
        if (user == null || store == null)
            return null;
        return userStoreRepository.findFirstByUserIdAndStoreIdAndIsActiveTrue(user.getId(), store.getId());
    }
}

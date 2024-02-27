package com.ivoriandev.saveursolidaire.services;

import com.ivoriandev.saveursolidaire.exceptions.BadRequestException;
import com.ivoriandev.saveursolidaire.exceptions.NotFoundException;
import com.ivoriandev.saveursolidaire.models.Store;
import com.ivoriandev.saveursolidaire.models.User;
import com.ivoriandev.saveursolidaire.models.embedded.Location;
import com.ivoriandev.saveursolidaire.repositories.StoreRepository;
import com.ivoriandev.saveursolidaire.services.interfaces.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService implements CrudService<Store> {

    private final StoreRepository storeRepository;

    private final UserService userService;
    private final UserStoreService userStoreService;

    @Override
    public Store create(Store store) {
        try {
            store.setDeletedAt(null);
            store.setIsActive(Boolean.FALSE);
            return storeRepository.save(store);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public Store createAndAttacheUser(Store store, Integer userId) {
        store = create(store);
        User user = userService.read(userId);
        userStoreService.attachUserToStore(user, store);

        return store;
    }

    @Override
    public List<Store> all() {
        return storeRepository.findAll();
    }

    @Override
    public Store read(Integer id) {
        return storeRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Store with id %d not found", id))
        );
    }

    @Override
    public Store update(Store store) {
        Store existingStore = read(store.getId());

        existingStore.setName(store.getName());
        existingStore.setContact(store.getContact());
        existingStore.setDescription(store.getDescription());

        return storeRepository.save(existingStore);
    }

    public Store updateIsActive(Integer id) {
        Store existingStore = read(id);

        existingStore.setIsActive(!existingStore.getIsActive());

        return storeRepository.save(existingStore);
    }

    public Store updateLocation(Integer id, Location storeLocation) {
        Store existingStore = read(id);

        Location location = existingStore.getLocation();
        location.setAddress(storeLocation.getAddress());
        location.setCity(storeLocation.getCity());
        location.setCountry(storeLocation.getCountry());
        location.setPostalCode(storeLocation.getPostalCode());
        location.setComplement(storeLocation.getComplement());
        location.setLatitude(storeLocation.getLatitude());
        location.setLongitude(storeLocation.getLongitude());
        existingStore.setLocation(location);

        return storeRepository.save(existingStore);
    }

    @Override
    public void delete(Integer id) {
        Store store = read(id);
        storeRepository.delete(store);
    }
}

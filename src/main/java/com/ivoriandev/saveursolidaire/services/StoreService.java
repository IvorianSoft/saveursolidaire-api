package com.ivoriandev.saveursolidaire.services;

import com.ivoriandev.saveursolidaire.exceptions.BadRequestException;
import com.ivoriandev.saveursolidaire.exceptions.NotFoundException;
import com.ivoriandev.saveursolidaire.models.Store;
import com.ivoriandev.saveursolidaire.models.User;
import com.ivoriandev.saveursolidaire.models.UserStore;
import com.ivoriandev.saveursolidaire.models.embedded.Location;
import com.ivoriandev.saveursolidaire.repositories.StoreRepository;
import com.ivoriandev.saveursolidaire.services.interfaces.CrudService;
import com.ivoriandev.saveursolidaire.utils.dto.store.SearchStoreDto;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.util.GeometricShapeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StoreService implements CrudService<Store> {

    private static final Logger log = LoggerFactory.getLogger(StoreService.class);
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private UserStoreService userStoreService;

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
        //check if the user is an admin or a seller
        if (userService.isCurrentUserAdmin()) {
            return storeRepository.findAll();
        }

        Set<Integer> storeIds = userStoreService.allByUserId(userService.getCurrentUser()).stream()
                .map(UserStore::getStore)
                .map(Store::getId)
                .collect(Collectors.toSet());
        return storeRepository.findAllByIdIn(storeIds);
    }

    @Override
    public Store read(Integer id) {
        return storeRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Store with id %d not found", id))
        );
    }

    @Override
    public Store update(Integer id, Store store) {
        Store existingStore = read(id);

        if (!userService.isCurrentUserAdmin()) {
            User user = userService.getCurrentUser();
            UserStore userStore = userStoreService.getOneByUserIdAndStoreId(user, existingStore);
            if (userStore == null) {
                throw new BadRequestException(String.format(
                        "This user is not affiliated with this store %s and cannot update it", existingStore.getId()
                ));
            }
        }

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

    public List<Store> getAllFromLocation(SearchStoreDto searchStoreDto) {
        return storeRepository.findAllByIsActiveTrueAndWithin(getSearchedArea(searchStoreDto));
    }

    private Geometry getSearchedArea(SearchStoreDto searchStoreDto) {
        int POINTS = 32;
        double DEFAULT_RADIUS = 5; // 5 km
        double radius = searchStoreDto.getRadius() != null && searchStoreDto.getRadius() > 0.0 ? searchStoreDto.getRadius() : DEFAULT_RADIUS;
        double radiusInDegrees = kilometersToDegrees(radius);

        GeometricShapeFactory geometricShapeFactory = new GeometricShapeFactory();
        geometricShapeFactory.setNumPoints(POINTS);
        geometricShapeFactory.setCentre(new Coordinate(searchStoreDto.getLatitude(), searchStoreDto.getLongitude()));
        geometricShapeFactory.setSize(radiusInDegrees * 2);

        return geometricShapeFactory.createCircle();
    }

    private double kilometersToDegrees(double kilometers) {
        return kilometers / 111; // Approximation générale : 1 degré de latitude ~ 111 km
    }

}

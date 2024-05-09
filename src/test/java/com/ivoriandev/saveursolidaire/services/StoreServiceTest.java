package com.ivoriandev.saveursolidaire.services;

import com.ivoriandev.saveursolidaire.models.Store;
import com.ivoriandev.saveursolidaire.models.embedded.Location;
import com.ivoriandev.saveursolidaire.utils.constants.AppConstantsTest;
import com.ivoriandev.saveursolidaire.utils.constants.AuthoritiesConstantsTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = AppConstantsTest.TEST_PROFILE)
@Transactional
public class StoreServiceTest {
    @Autowired
    private StoreService storeService;

    private Store getValidStore() {
        Store store = new Store();
        store.setName("NEW_STORE");
        store.setContact("1234567890");
        store.setDescription("NEW_DESCRIPTION");
        store.setIsActive(Boolean.TRUE); // isActive is set to false by default, it should not consider this value
        store.setLocation(Location.builder()
                .address("NEW_ADDRESS")
                .city("NEW_CITY")
                .country("NEW_COUNTRY")
                .postalCode("NEW_POSTAL_CODE")
                .complement("NEW_COMPLEMENT")
                .latitude(2.0)
                .longitude(2.0)
                .build());

        return store;
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    @BeforeAll
    public void testGetAllStores() {
        int count = storeService.all().size();
        Assert.assertTrue(count > 0);
    }

    @Test
    public void testGetStoreById() {
        Store store = storeService.read(1);
        Assert.assertEquals("STORE", store.getName());
        Assert.assertEquals("0123456789", store.getContact());
        Assert.assertEquals("DESCRIPTION", store.getDescription());
        Assert.assertEquals(Boolean.TRUE, store.getIsActive());
        Assert.assertEquals("ADDRESS", store.getLocation().getAddress());
        Assert.assertEquals("CITY", store.getLocation().getCity());
        Assert.assertEquals("COUNTRY", store.getLocation().getCountry());
        Assert.assertEquals("00000", store.getLocation().getPostalCode());
        Assert.assertEquals("COMPLEMENT", store.getLocation().getComplement());
        Assert.assertEquals(Double.valueOf(1.0), store.getLocation().getLatitude());
        Assert.assertEquals(Double.valueOf(1.0), store.getLocation().getLongitude());
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testCreateStore() {
        //count before create
        int countBeforeCreate = storeService.all().size();

        Store store = getValidStore();
        store.setContact("0234567890");
        Store createdStore = storeService.create(store);

        Assert.assertEquals("NEW_STORE", createdStore.getName());
        Assert.assertEquals("0234567890", createdStore.getContact());
        Assert.assertEquals("NEW_DESCRIPTION", createdStore.getDescription());
        Assert.assertEquals(Boolean.FALSE, createdStore.getIsActive());
        Assert.assertEquals("NEW_ADDRESS", createdStore.getLocation().getAddress());
        Assert.assertEquals("NEW_CITY", createdStore.getLocation().getCity());
        Assert.assertEquals("NEW_COUNTRY", createdStore.getLocation().getCountry());
        Assert.assertEquals("NEW_POSTAL_CODE", createdStore.getLocation().getPostalCode());
        Assert.assertEquals("NEW_COMPLEMENT", createdStore.getLocation().getComplement());
        Assert.assertEquals(Double.valueOf(2.0), createdStore.getLocation().getLatitude());
        Assert.assertEquals(Double.valueOf(2.0), createdStore.getLocation().getLongitude());
        Assert.assertEquals(countBeforeCreate + 1, storeService.all().size());
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testUpdateStore() {
        Store store = storeService.read(1);
        store.setName("UPDATED_STORE");
        store.setContact("9876543210");
        store.setDescription("UPDATED_DESCRIPTION");

        Store updatedStore = storeService.update(store.getId(), store);

        Assert.assertEquals("UPDATED_STORE", updatedStore.getName());
        Assert.assertEquals("9876543210", updatedStore.getContact());
        Assert.assertEquals("UPDATED_DESCRIPTION", updatedStore.getDescription());
        Assert.assertEquals(Boolean.TRUE, updatedStore.getIsActive());
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testDeleteStore() {
        Store store = storeService.create(getValidStore());

        //count before delete
        int countBeforeDelete = storeService.all().size();

        storeService.delete(store.getId());

        Assert.assertEquals(countBeforeDelete - 1, storeService.all().size());
    }

    @Test
    public void testUpdateIsActive() {
        Store store = storeService.read(1);
        Boolean isActiveOld = store.getIsActive();

        Store updatedStore = storeService.updateIsActive(store.getId());

        Assert.assertNotEquals(isActiveOld, updatedStore.getIsActive());
        Assert.assertEquals(Boolean.FALSE, updatedStore.getIsActive());
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testUpdateLocation() {
        Location location = Location.builder()
                .address("UPDATED_ADDRESS")
                .city("UPDATED_CITY")
                .country("UPDATED_COUNTRY")
                .postalCode("UPDATED_POSTAL_CODE")
                .complement("UPDATED_COMPLEMENT")
                .latitude(3.0)
                .longitude(3.0)
                .build();

        Store updatedStore = storeService.updateLocation(1, location);

        Assert.assertEquals("UPDATED_ADDRESS", updatedStore.getLocation().getAddress());
        Assert.assertEquals("UPDATED_CITY", updatedStore.getLocation().getCity());
        Assert.assertEquals("UPDATED_COUNTRY", updatedStore.getLocation().getCountry());
        Assert.assertEquals("UPDATED_POSTAL_CODE", updatedStore.getLocation().getPostalCode());
        Assert.assertEquals("UPDATED_COMPLEMENT", updatedStore.getLocation().getComplement());
        Assert.assertEquals(Double.valueOf(3.0), updatedStore.getLocation().getLatitude());
        Assert.assertEquals(Double.valueOf(3.0), updatedStore.getLocation().getLongitude());
    }

}

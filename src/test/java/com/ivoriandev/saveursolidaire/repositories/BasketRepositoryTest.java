package com.ivoriandev.saveursolidaire.repositories;

import com.ivoriandev.saveursolidaire.models.Basket;
import com.ivoriandev.saveursolidaire.models.Store;
import com.ivoriandev.saveursolidaire.models.embedded.Location;
import com.ivoriandev.saveursolidaire.utils.constants.AppConstantsTest;
import com.ivoriandev.saveursolidaire.utils.enums.store.StoreCategoryEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = AppConstantsTest.TEST_PROFILE)
public class BasketRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Test
    public void testSave() {
        Location location = new Location();
        location.setLatitude(1.0);
        location.setLongitude(1.0);
        location.setAddress("Address");
        location.setCity("City");
        location.setCountry("Country");
        location.setPostalCode("00000");

        Store store = new Store();
        store.setName("Store");
        store.setIsActive(Boolean.TRUE);
        store.setLocation(location);
        store.setContact("0000000000");
        store.setDescription("Description");
        store.setCategory(StoreCategoryEnum.GROCERY);
        entityManager.persistAndFlush(store);

        Basket basket = new Basket();
        basket.setName("Basket");
        basket.setQuantity(1);
        basket.setInitialQuantity(1);
        basket.setPrice(10.0);
        basket.setNote("Note");
        basket.setIsActive(Boolean.TRUE);
        basket.setStore(store);
        entityManager.persistAndFlush(basket);

        Basket found = basketRepository.findById(basket.getId()).orElse(null);
        Assert.assertNotNull(found);
        Assert.assertEquals(basket.getName(), found.getName());
        Assert.assertEquals(basket.getQuantity(), found.getQuantity());
        Assert.assertEquals(basket.getInitialQuantity(), found.getInitialQuantity());
        Assert.assertEquals(basket.getPrice(), found.getPrice());
        Assert.assertEquals(basket.getNote(), found.getNote());
        Assert.assertEquals(basket.getIsActive(), found.getIsActive());
        Assert.assertEquals(basket.getStore().getId(), found.getStore().getId());
    }

    @Test
    public void testFindAllByStoreId()
    {
        List<Basket> baskets = basketRepository.findAllByStoreId(1);
        Assert.assertEquals(1, baskets.size());
        Basket basket = baskets.get(0);
        Assert.assertEquals("BASKET", basket.getName());
        Assert.assertEquals(1, basket.getQuantity(), 0);
        Assert.assertEquals(1, basket.getInitialQuantity(), 0);
        Assert.assertEquals(100.0, basket.getPrice(), 0.0);
        Assert.assertEquals("NOTE", basket.getNote());
        Assert.assertEquals(Boolean.TRUE, basket.getIsActive());
        Assert.assertEquals(1, basket.getStore().getId(), 0);
        Assert.assertEquals("DESCRIPTION", basket.getStore().getDescription());
    }

    @Test
    public void testFindAllByStoreIdNotFound()
    {
        List<Basket> baskets = basketRepository.findAllByStoreId(2);
        Assert.assertEquals(0, baskets.size());
    }

    @Test
    public void testFindAllByStore2()
    {
        Location location = new Location();
        location.setLatitude(1.0);
        location.setLongitude(1.0);
        location.setAddress("Address");
        location.setCity("City");
        location.setCountry("Country");
        location.setPostalCode("00000");

        Store store = new Store();
        store.setName("Store");
        store.setIsActive(Boolean.TRUE);
        store.setLocation(location);
        store.setContact("0000000000");
        store.setDescription("Description");
        store.setCategory(StoreCategoryEnum.GROCERY);
        entityManager.persistAndFlush(store);

        Basket basket = new Basket();
        basket.setName("Basket");
        basket.setQuantity(1);
        basket.setInitialQuantity(1);
        basket.setPrice(10.0);
        basket.setNote("Note");
        basket.setIsActive(Boolean.TRUE);
        basket.setStore(store);
        entityManager.persistAndFlush(basket);

        List<Basket> baskets = basketRepository.findAllByStoreId(store.getId());
        Assert.assertEquals(1, baskets.size());
        Basket found = baskets.get(0);
        Assert.assertEquals(basket.getName(), found.getName());
        Assert.assertEquals(basket.getQuantity(), found.getQuantity());
        Assert.assertEquals(basket.getInitialQuantity(), found.getInitialQuantity());
        Assert.assertEquals(basket.getPrice(), found.getPrice());
        Assert.assertEquals(basket.getNote(), found.getNote());
        Assert.assertEquals(basket.getIsActive(), found.getIsActive());

        Assert.assertEquals(2, basketRepository.findAll().size());
    }
}

package com.ivoriandev.saveursolidaire.services;

import com.ivoriandev.saveursolidaire.exceptions.NotFoundException;
import com.ivoriandev.saveursolidaire.models.Basket;
import com.ivoriandev.saveursolidaire.repositories.BasketRepository;
import com.ivoriandev.saveursolidaire.utils.constants.AppConstantsTest;
import com.ivoriandev.saveursolidaire.utils.dto.basket.CreateBasketDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = AppConstantsTest.TEST_PROFILE)
public class BasketServiceTest {
    @Autowired
    private BasketService basketService;

    @Autowired
    private BasketRepository basketRepository;

    @Test
    public void testCreateBasket() {
        CreateBasketDto createBasketDto = new CreateBasketDto();
        createBasketDto.setName("BASKET_NAME");
        createBasketDto.setDescription("BASKET_DESCRIPTION");
        createBasketDto.setPrice(1000.0);
        createBasketDto.setQuantity(10);
        createBasketDto.setNote("BASKET_NOTE");
        createBasketDto.setStoreId(1);

        //count before create
        int countBeforeCreate = basketRepository.findAll().size();

        Basket createdBasket = basketService.create(createBasketDto);

        assertEquals("BASKET_NAME", createdBasket.getName());
        assertEquals("BASKET_DESCRIPTION", createdBasket.getDescription());
        assertEquals(1000.0, createdBasket.getPrice(), 0.0);
        assertEquals(10, createdBasket.getQuantity(), 0);
        assertEquals("BASKET_NOTE", createdBasket.getNote());
        assertEquals(Boolean.TRUE, createdBasket.getIsActive());
        assertEquals(1, createdBasket.getStore().getId(), 0);
        assertEquals(countBeforeCreate + 1, basketRepository.findAll().size());
    }

    @Test
    public void testUpdateBasket() {
        Basket basket = basketService.read(1);
        basket.setName("UPDATED_BASKET_NAME");
        basket.setDescription("UPDATED_BASKET_DESCRIPTION");
        basket.setPrice(2000.0);
        basket.setNote("UPDATED_BASKET_NOTE");

        Basket updatedBasket = basketService.update(basket.getId(), basket);

        assertEquals("UPDATED_BASKET_NAME", updatedBasket.getName());
        assertEquals("UPDATED_BASKET_DESCRIPTION", updatedBasket.getDescription());
        assertEquals(2000.0, updatedBasket.getPrice(), 0.0);
        assertEquals("UPDATED_BASKET_NOTE", updatedBasket.getNote());
    }

    @Test
    public void testDeleteBasket() {
        //count before delete
        int countBeforeDelete = basketRepository.findAll().size();

        basketService.delete(1);

        assertEquals(countBeforeDelete - 1, basketRepository.findAll().size());
        assertNull(basketRepository.findById(1).orElse(null));
    }

    @Test
    public void testGetAllBaskets() {
        List<Basket> baskets = basketService.all();

        assertEquals(1, baskets.size());
    }

    @Test
    public void testGetAllBasketsByStore() {
        List<Basket> baskets = basketService.allByStore(1);

        assertEquals(1, baskets.size());
    }

    @Test
    public void testGetBasketById() {
        Basket basket = basketService.read(1);

        assertEquals("BASKET", basket.getName());
        assertEquals("DESCRIPTION", basket.getDescription());
        assertEquals(100.0, basket.getPrice(), 0.0);
        assertEquals(1, basket.getQuantity(), 0);
        assertEquals(1, basket.getInitialQuantity(), 0);
        assertEquals("NOTE", basket.getNote());
        assertEquals(Boolean.TRUE, basket.getIsActive());
        assertEquals(1, basket.getStore().getId(), 0);
    }

    @Test
    public void testGetOneThatDoesNotExistAndThrowNotFoundException() {
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            basketService.read(2);
        });

        assertEquals("Basket with id 2 not found", notFoundException.getReason());
        assertEquals(HttpStatus.NOT_FOUND.value(), notFoundException.getStatusCode().value());
    }

    @Test
    public void testUpdateQuantity() {
        Basket updatedBasket = basketService.updateQuantity(1, 5);

        assertEquals(5, updatedBasket.getQuantity(), 0);
    }
}

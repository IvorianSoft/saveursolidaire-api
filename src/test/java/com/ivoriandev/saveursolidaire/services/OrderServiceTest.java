package com.ivoriandev.saveursolidaire.services;

import com.ivoriandev.saveursolidaire.exceptions.BadRequestException;
import com.ivoriandev.saveursolidaire.models.Basket;
import com.ivoriandev.saveursolidaire.models.Order;
import com.ivoriandev.saveursolidaire.repositories.BasketRepository;
import com.ivoriandev.saveursolidaire.utils.constants.AppConstantsTest;
import com.ivoriandev.saveursolidaire.utils.constants.AuthoritiesConstantsTest;
import com.ivoriandev.saveursolidaire.utils.dto.order.CreateOrderDto;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = AppConstantsTest.TEST_PROFILE)
@Transactional
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private BasketRepository basketRepository;

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.CUSTOMER)
    public void testCreateOrder() {
        //The default BasketId is 1 and has 1 quantity and 100 price
        Basket basket = basketRepository.findById(1).orElse(null);
        Assert.assertNotNull(basket);

        CreateOrderDto createOrderDto = new CreateOrderDto();
        createOrderDto.setBasketId(basket.getId());
        createOrderDto.setQuantity(1);

        Order order = orderService.create(createOrderDto);

        Assert.assertNotNull(order);
        Assert.assertTrue(order.getReference().startsWith("REF-"));
        Assert.assertEquals(100, order.getTotalPrice(), 0.0);
        Assert.assertEquals(1, order.getQuantity(), 0.0);
        Assert.assertEquals(Boolean.FALSE, order.getIsPaid());
        Assert.assertEquals(Boolean.FALSE, order.getIsRecovered());
        Assert.assertEquals("CASH", order.getPaymentMethod().toString());
        Assert.assertEquals(basket.getStore().getId(), order.getStore().getId());
        Assert.assertEquals(basket.getId(), order.getBasket().getId());
        Assert.assertEquals(0, basket.getQuantity(), 0.0);
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.CUSTOMER)
    public void testCreateOrderWithQuantityGreaterThanBasketQuantity() {
        //The default BasketId is 1 and has 1 quantity and 100 price
        Basket basket = basketRepository.findById(1).orElse(null);
        Assert.assertNotNull(basket);

        CreateOrderDto createOrderDto = new CreateOrderDto();
        createOrderDto.setBasketId(basket.getId());
        createOrderDto.setQuantity(2);

        BadRequestException badRequestException = Assert.assertThrows(BadRequestException.class, () -> {
            orderService.create(createOrderDto);
        });

        Assert.assertEquals("You can't order more than available quantity", badRequestException.getReason());
        Assert.assertTrue(badRequestException.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.CUSTOMER)
    public void testCreateOrderWithBasketNotAvailable() {
        //The default BasketId is 1 and has 1 quantity and 100 price
        Basket basket = basketRepository.findById(1).orElse(null);
        Assert.assertNotNull(basket);

        basket.setIsActive(Boolean.FALSE);
        basketRepository.save(basket);

        CreateOrderDto createOrderDto = new CreateOrderDto();
        createOrderDto.setBasketId(basket.getId());
        createOrderDto.setQuantity(1);

        BadRequestException badRequestException = Assert.assertThrows(BadRequestException.class, () -> {
            orderService.create(createOrderDto);
        });

        Assert.assertEquals("Basket is not available", badRequestException.getReason());
        Assert.assertTrue(badRequestException.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testGetAllOrders() {
        Assert.assertEquals(1, orderService.all().size());
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.CUSTOMER)
    public void testGetAllOrdersByStore() {
        Assert.assertEquals(1, orderService.allByStore(1).size());
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.CUSTOMER)
    public void testGetOrderById() {
        Order order = orderService.read(1);
        Assert.assertNotNull(order);
        Assert.assertEquals(1, order.getId(), 0.0);
        Assert.assertEquals("REF-123", order.getReference());
        Assert.assertEquals(100, order.getTotalPrice(), 0.0);
        Assert.assertEquals(1, order.getQuantity(), 0.0);
        Assert.assertEquals(Boolean.FALSE, order.getIsPaid());
        Assert.assertEquals(Boolean.FALSE, order.getIsRecovered());
        Assert.assertEquals("CASH", order.getPaymentMethod().toString());
        Assert.assertEquals(2, order.getUser().getId(), 0.0);
        Assert.assertEquals(1, order.getStore().getId(), 0.0);
        Assert.assertEquals(1, order.getBasket().getId(), 0.0);
    }

    @Test
    public void testGetAllByStore() {
        List<Order> orders = orderService.allByStore(1);
        Assert.assertEquals(1, orders.size());
        Order order = orders.get(0);
        Assert.assertEquals(1, order.getId(), 0.0);
        Assert.assertEquals("REF-123", order.getReference());
        Assert.assertEquals(100, order.getTotalPrice(), 0.0);
        Assert.assertEquals(1, order.getQuantity(), 0.0);
        Assert.assertEquals(Boolean.FALSE, order.getIsPaid());
        Assert.assertEquals(Boolean.FALSE, order.getIsRecovered());
        Assert.assertEquals("CASH", order.getPaymentMethod().toString());
        Assert.assertEquals(2, order.getUser().getId(), 0.0);
        Assert.assertEquals(1, order.getStore().getId(), 0.0);
        Assert.assertEquals(1, order.getBasket().getId(), 0.0);
    }

    @Test
    public void testGetAllByStoreWithNoOrders() {
        List<Order> orders = orderService.allByStore(2);
        Assert.assertEquals(0, orders.size());
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.CUSTOMER)
    public void testUpdateOrderStatus() {
        Order order = orderService.updateStatus(1);
        Assert.assertNotNull(order);
        Assert.assertEquals(1, order.getId(), 0.0);
        Assert.assertEquals("REF-123", order.getReference());
        Assert.assertEquals(100, order.getTotalPrice(), 0.0);
        Assert.assertEquals(1, order.getQuantity(), 0.0);
        Assert.assertEquals(Boolean.TRUE, order.getIsPaid());
        Assert.assertEquals(Boolean.TRUE, order.getIsRecovered());
        Assert.assertEquals("CASH", order.getPaymentMethod().toString());
        Assert.assertEquals(2, order.getUser().getId(), 0.0);
        Assert.assertEquals(1, order.getStore().getId(), 0.0);
        Assert.assertEquals(1, order.getBasket().getId(), 0.0);
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testDeleteOrder() {
        orderService.delete(1);
        Assert.assertEquals(0, orderService.all().size());
    }
}

package com.ivoriandev.saveursolidaire.services;

import com.ivoriandev.saveursolidaire.exceptions.BadRequestException;
import com.ivoriandev.saveursolidaire.exceptions.NotFoundException;
import com.ivoriandev.saveursolidaire.models.Basket;
import com.ivoriandev.saveursolidaire.models.Order;
import com.ivoriandev.saveursolidaire.models.User;
import com.ivoriandev.saveursolidaire.repositories.OrderRepository;
import com.ivoriandev.saveursolidaire.services.interfaces.CrudService;
import com.ivoriandev.saveursolidaire.utils.Utilities;
import com.ivoriandev.saveursolidaire.utils.constants.AuthoritiesConstants;
import com.ivoriandev.saveursolidaire.utils.dto.order.CreateOrderDto;
import com.ivoriandev.saveursolidaire.utils.enums.order.PaymentMethodEnum;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService implements CrudService<Order> {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BasketService basketService;

    @Autowired
    private UserService userService;

    @Transactional(rollbackOn = Exception.class)
    public Order create(CreateOrderDto createOrderDto) {
        Basket basket = basketService.read(createOrderDto.getBasketId());

        throwErrorIfBasketIsNotAvailable(basket, createOrderDto);

        Order order = Order.builder()
                .reference(generateReference())
                .totalPrice(getTotalPrice(basket, createOrderDto))
                .quantity(createOrderDto.getQuantity())
                .isPaid(Boolean.FALSE)
                .isRecovered(Boolean.FALSE)
                .paymentMethod(PaymentMethodEnum.CASH)
                .user(userService.getCurrentUser())
                .store(basket.getStore())
                .basket(basketService.read(createOrderDto.getBasketId()))
                .build();
        order.setDeletedAt(null);

        return orderRepository.save(order);
    }

    @Override
    public List<Order> all() {
        User user = userService.getCurrentUser();
        if (user.getRole().getName().equals(AuthoritiesConstants.ADMIN)) {
            return orderRepository.findAll();
        }

        return orderRepository.findAllByUserId(user.getId());
    }

    public List<Order> allByStore(Integer storeId) {
        return orderRepository.findAllByStoreId(storeId);
    }

    @Override
    public Order read(Integer id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            throw new NotFoundException(String.format("Order with id %d not found", id));
        }
        return order;
    }

    public Order updateStatus(Integer id) {
        Order existingOrder = read(id);

        User user = userService.getCurrentUser();
        if (!user.getRole().getName().equals(AuthoritiesConstants.ADMIN) && !existingOrder.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("You are not allowed to update this order");
        }

        existingOrder.setIsPaid(Boolean.TRUE);
        existingOrder.setIsRecovered(Boolean.TRUE);
        return orderRepository.save(existingOrder);
    }

    @Override
    public void delete(Integer id) {
        Order order = read(id);
        orderRepository.delete(order);
    }

    public List<Order> allByUserAndIsPaidTrue() {
        User user = userService.getCurrentUser();
        return orderRepository.findAllByUserIdAndIsPaidTrue(user.getId());
    }

    private String generateReference() {
        Date date = Utilities.getCurrentDate();
        String reference = "REF-" + DateFormatUtils.format(date, "yyyyMMdd");
        int count = 1;

        while (orderRepository.existsByReference(reference + "-" + count)) {
            count++;
        }

        return String.format("%s-%d", reference, count);
    }

    private void throwErrorIfBasketIsNotAvailable(Basket basket, CreateOrderDto createOrderDto) {
        basketService.throwErrorIfBasketIsNotAvailable(basket, createOrderDto.getQuantity());
    }

    private Double getTotalPrice(Basket basket, CreateOrderDto createOrderDto) {
        return basket.getPrice() * createOrderDto.getQuantity();
    }
}

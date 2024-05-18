package com.ivoriandev.saveursolidaire.services;

import com.ivoriandev.saveursolidaire.exceptions.BadRequestException;
import com.ivoriandev.saveursolidaire.exceptions.NotFoundException;
import com.ivoriandev.saveursolidaire.models.Order;
import com.ivoriandev.saveursolidaire.repositories.OrderRepository;
import com.ivoriandev.saveursolidaire.services.interfaces.CrudService;
import com.ivoriandev.saveursolidaire.utils.dto.order.CreateOrderDto;
import com.ivoriandev.saveursolidaire.utils.dto.order.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.nio.file.Files.exists;

@Service
public class OrderService implements CrudService<Order> {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BasketService basketService;

    @Autowired
    private UserService userService;

    public Order create(CreateOrderDto orderDto) {
        Order order = Order.builder()
                .reference(orderDto.getReference())
                .totalPrice(orderDto.getTotalPrice())
                .isPaid(orderDto.getIsPaid())
                .isRecovered(orderDto.getIsRecovered())
                .paymentMethod(orderDto.getPaymentMethod())
                .user(userService.read(orderDto.getUserId()))
                .seller(userService.read(orderDto.getSellerId()))
                .basket(basketService.read(orderDto.getBasketId()))
                .build();
        order.setDeletedAt(null);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> all() {
        return orderRepository.findAll();
    }

    @Override
    public Order read(Integer id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            throw new NotFoundException(String.format("Order with id %d not found", id));
        }
        return order;
    }

    public Order update(Integer id, OrderDto order) {
        Order existingOrder = read(id);
        existingOrder.setReference(order.getReference());
        existingOrder.setTotalPrice(order.getTotalPrice());
        existingOrder.setIsPaid(order.getIsPaid());
        existingOrder.setIsRecovered(order.getIsRecovered());
        existingOrder.setPaymentMethod(order.getPaymentMethod());
        return orderRepository.save(existingOrder);
    }

    public Order updateStatus(Integer id) {
        Order existingOrder = read(id);
        existingOrder.setIsPaid(true);
        existingOrder.setIsRecovered(true);
        return orderRepository.save(existingOrder);
    }

    @Override
    public void delete(Integer id) {
        Order order = read(id);
        orderRepository.delete(order);
    }

    public List<Order> allByUser(Integer userId) {
        return orderRepository.findAllByUserId(userId);
    }

    public List<Order> allBySeller(Integer sellerId) {
        return orderRepository.findAllBySellerId(sellerId);
    }

    public List<Order> allByUserAndIsPaidTrue(Integer userId) {
        return orderRepository.findAllByUserIdAndIsPaidTrue(userId);
    }
}

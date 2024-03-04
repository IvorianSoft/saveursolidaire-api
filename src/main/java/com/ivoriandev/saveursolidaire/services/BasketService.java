package com.ivoriandev.saveursolidaire.services;

import com.ivoriandev.saveursolidaire.exceptions.NotFoundException;
import com.ivoriandev.saveursolidaire.models.Basket;
import com.ivoriandev.saveursolidaire.repositories.BasketRepository;
import com.ivoriandev.saveursolidaire.services.interfaces.CrudService;
import com.ivoriandev.saveursolidaire.utils.dto.basket.CreateBasketDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketService implements CrudService<Basket> {
    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private StoreService storeService;

    public Basket create(CreateBasketDto createBasketDto) {
        Basket basket = Basket.builder()
                .name(createBasketDto.getName())
                .description(createBasketDto.getDescription())
                .price(createBasketDto.getPrice())
                .quantity(createBasketDto.getQuantity())
                .initialQuantity(createBasketDto.getQuantity())
                .note(createBasketDto.getNote())
                .isActive(Boolean.TRUE)
                .store(storeService.read(createBasketDto.getStoreId()))
                .build();

        return basketRepository.save(basket);
    }

    @Override
    public List<Basket> all() {
        return basketRepository.findAll();
    }

    public List<Basket> allByStore(Integer storeId) {
        return basketRepository.findAllByStoreId(storeId);
    }

    @Override
    public Basket read(Integer id) {
        return basketRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Basket with id %d not found", id))
        );
    }

    @Override
    public Basket update(Integer id, Basket basket) {
        Basket existingBasket = read(id);
        existingBasket.setName(basket.getName());
        existingBasket.setDescription(basket.getDescription());
        existingBasket.setPrice(basket.getPrice());
        existingBasket.setNote(basket.getNote());

        return basketRepository.save(existingBasket);
    }

    public Basket updateQuantity(Integer id, Integer quantity) {
        Basket existingBasket = read(id);
        existingBasket.setQuantity(quantity);
        return basketRepository.save(existingBasket);
    }

    @Override
    public void delete(Integer id) {
        Basket basket = read(id);
        basketRepository.delete(basket);
    }

    public Basket updateActiveStatus(Integer id) {
        Basket basket = read(id);
        basket.setIsActive(!basket.getIsActive());
        return basketRepository.save(basket);
    }
}

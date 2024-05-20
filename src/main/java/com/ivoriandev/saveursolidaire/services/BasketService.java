package com.ivoriandev.saveursolidaire.services;

import com.ivoriandev.saveursolidaire.exceptions.BadRequestException;
import com.ivoriandev.saveursolidaire.exceptions.NotFoundException;
import com.ivoriandev.saveursolidaire.models.Basket;
import com.ivoriandev.saveursolidaire.models.File;
import com.ivoriandev.saveursolidaire.models.Order;
import com.ivoriandev.saveursolidaire.repositories.BasketRepository;
import com.ivoriandev.saveursolidaire.services.interfaces.CrudService;
import com.ivoriandev.saveursolidaire.utils.dto.basket.BasketDto;
import com.ivoriandev.saveursolidaire.utils.dto.basket.CreateBasketDto;
import com.ivoriandev.saveursolidaire.utils.dto.geospatial.SearchDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BasketService implements CrudService<Basket> {
    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private StoreService storeService;
    @Autowired
    private FileService fileService;

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
        basket = basketRepository.save(basket);

        return basket;
    }

    public List<BasketDto> getAllFromLocation(SearchDto searchDto) {
        List<Basket> baskets = basketRepository.findAllByStore_IsActiveTrueAndStore_GeopointWithinAndQuantityIsGreaterThanAndIsActiveTrueOrderByPriceAsc(
                SearchAreaService.getSearchedArea(
                        searchDto.getLatitude(),
                        searchDto.getLongitude(),
                        Double.valueOf(searchDto.getRadius())
                ),
                BigDecimal.ZERO.intValue()
        );

        return baskets.stream().map(BasketDto::from).toList();
    }

    public Basket uploadImage(Integer id, @NotNull MultipartFile image) {
        Basket basket = read(id);

        File file = fileService.saveImage(image);
        basket.setImage(file);

        return basketRepository.save(basket);
    }

    public void throwErrorIfBasketIsNotAvailable(Basket basket, Integer quantity){
        if(!basket.getIsActive()) {
            throw new BadRequestException("Basket is not available");
        }

        if(basket.getQuantity() <= 0) {
            throw new BadRequestException("Basket quantity is not available");
        }

        if(basket.getQuantity() < quantity) {
            throw new BadRequestException("You can't order more than available quantity");
        }

        if(basket.getStore().getIsActive() == Boolean.FALSE) {
            throw new BadRequestException("Store is not available");
        }
    }

    @Async
    public void updateBasket(Order order) {
        Basket basket = order.getBasket();
        basket.setQuantity(basket.getQuantity() - order.getQuantity());
        basketRepository.save(basket);
    }
}

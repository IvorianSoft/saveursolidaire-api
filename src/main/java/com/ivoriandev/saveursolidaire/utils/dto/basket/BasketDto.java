package com.ivoriandev.saveursolidaire.utils.dto.basket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ivoriandev.saveursolidaire.models.Basket;
import com.ivoriandev.saveursolidaire.utils.dto.file.FileDto;
import com.ivoriandev.saveursolidaire.utils.dto.store.StoreDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link Basket}
 */
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasketDto implements Serializable {
    Integer id;
    Date createdAt;
    Date updatedAt;
    Date deletedAt;
    String name;
    String description;
    Double price;
    Integer quantity;
    Integer initialQuantity;
    FileDto image;
    String note;
    Boolean isActive;
    StoreDto store;

    public static BasketDto from(Basket basket) {
        return BasketDto.builder()
                .id(basket.getId())
                .createdAt(basket.getCreatedAt())
                .updatedAt(basket.getUpdatedAt())
                .deletedAt(basket.getDeletedAt())
                .name(basket.getName())
                .description(basket.getDescription())
                .price(basket.getPrice())
                .quantity(basket.getQuantity())
                .initialQuantity(basket.getInitialQuantity())
                .image(FileDto.from(basket.getImage()))
                .note(basket.getNote())
                .isActive(basket.getIsActive())
                .store(StoreDto.from(basket.getStore()))
                .build();
    }
}

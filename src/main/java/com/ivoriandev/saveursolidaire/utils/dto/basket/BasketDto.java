package com.ivoriandev.saveursolidaire.utils.dto.basket;

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
}
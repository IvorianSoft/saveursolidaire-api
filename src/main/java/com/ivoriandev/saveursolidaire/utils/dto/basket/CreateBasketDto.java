package com.ivoriandev.saveursolidaire.utils.dto.basket;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateBasketDto {
    @NotNull
    String name;
    String description;
    @NotNull
    @Min(1)
    Double price;
    @NotNull
    @Min(1)
    Integer quantity;
    @NotNull(message = "Please provide if client must be prepared bag or not")
    String note;
    @NotNull
    Integer storeId;
}

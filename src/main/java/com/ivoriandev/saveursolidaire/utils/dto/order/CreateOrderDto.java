package com.ivoriandev.saveursolidaire.utils.dto.order;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateOrderDto {
    @NonNull
    Integer basketId;

    @NonNull
    Integer quantity;
}

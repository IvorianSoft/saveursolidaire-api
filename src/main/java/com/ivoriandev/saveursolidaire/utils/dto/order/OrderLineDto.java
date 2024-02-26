package com.ivoriandev.saveursolidaire.utils.dto.order;

import com.ivoriandev.saveursolidaire.models.OrderLine;
import com.ivoriandev.saveursolidaire.utils.dto.basket.BasketDto;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link OrderLine}
 */
@Getter
@Setter
@ToString
@Builder
public class OrderLineDto implements Serializable {
    Integer id;
    Date createdAt;
    Date updatedAt;
    Date deletedAt;
    OrderDto order;
    BasketDto basket;
    Integer quantity;
    Double price;
}
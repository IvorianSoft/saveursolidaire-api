package com.ivoriandev.saveursolidaire.utils.dto.order;

import com.ivoriandev.saveursolidaire.models.Order;
import com.ivoriandev.saveursolidaire.utils.dto.user.UserDto;
import com.ivoriandev.saveursolidaire.utils.enums.order.PaymentMethodEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link Order}
 */
@Getter
@Setter
@ToString
@Builder
public class OrderDto implements Serializable {
    Integer id;
    Date createdAt;
    Date updatedAt;
    Date deletedAt;
    String reference;
    Double totalPrice;
    Boolean isPaid;
    Boolean isRecovered;
    PaymentMethodEnum paymentMethod;
    UserDto user;
}
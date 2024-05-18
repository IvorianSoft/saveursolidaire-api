package com.ivoriandev.saveursolidaire.utils.dto.order;

import com.ivoriandev.saveursolidaire.utils.enums.order.PaymentMethodEnum;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateOrderDto {
    String reference;
    @NonNull
    Double totalPrice;
    @NonNull
    Boolean isPaid;
    @NonNull
    Boolean isRecovered;
    @NonNull
    PaymentMethodEnum paymentMethod;
    @NonNull
    Integer userId;
    @NonNull
    Integer sellerId;
    @NonNull
    Integer basketId;

}

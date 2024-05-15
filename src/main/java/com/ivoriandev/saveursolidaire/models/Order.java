package com.ivoriandev.saveursolidaire.models;

import com.ivoriandev.saveursolidaire.models.base.BaseEntity;
import com.ivoriandev.saveursolidaire.utils.enums.order.PaymentMethodEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@SQLDelete(sql = "UPDATE orders SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class Order extends BaseEntity {
    @Column(name = "reference", nullable = false)
    private String reference;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid;

    @Column(name = "is_recovered", nullable = false)
    private Boolean isRecovered;

    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false, referencedColumnName = "id")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "basket_id", nullable = false, referencedColumnName = "id")
    private Basket basket;
}

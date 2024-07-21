package com.retail.store.promo.engin.entity;

import com.retail.store.promo.engin.valueobject.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Bill {
    private Long id;
    private Long orderId;
    private Money totalAmount;
    private Money discount;
    private Money netPayableAmount;
}

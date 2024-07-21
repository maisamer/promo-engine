package com.retail.store.promo.engin.entity;

import com.retail.store.promo.engin.valueobject.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderItem {
    private Long id;
    private final Product product;
    private final int quantity;
    private final Money price;
    private final Money subTotal;

    public boolean isValidPrice(){
        return price.isGreaterThanZero() &&
                price.equals(product.getPrice()) &&
                price.multiply(quantity).equals(subTotal);
    }
}

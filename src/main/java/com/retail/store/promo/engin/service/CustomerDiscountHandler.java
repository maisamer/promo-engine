package com.retail.store.promo.engin.service;

import com.retail.store.promo.engin.entity.Order;
import com.retail.store.promo.engin.entity.OrderItem;
import com.retail.store.promo.engin.valueobject.Money;
import com.retail.store.promo.engin.valueobject.ProductCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CustomerDiscountHandler implements DiscountHandler {

    @Override
    public Money calculateDiscount(Order order) {
        if (ChronoUnit.YEARS.between(order.getUser().getJoinedOn(), LocalDate.now()) > 2) {
            Money totalNonGroceryAmount = order.getItems().stream()
                    .filter(item -> item.getProduct().getProductCategory() == ProductCategory.OTHER)
                    .map(OrderItem::getPrice)
                    .reduce(Money.ZERO,Money::add);

            return totalNonGroceryAmount.multiply(BigDecimal.valueOf(0.05));
        }
        return Money.ZERO;
    }
}

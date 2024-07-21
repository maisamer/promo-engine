package com.retail.store.promo.engin.service;

import com.retail.store.promo.engin.entity.Order;
import com.retail.store.promo.engin.entity.OrderItem;
import com.retail.store.promo.engin.valueobject.Money;
import com.retail.store.promo.engin.valueobject.ProductCategory;

import java.math.BigDecimal;

public class AffiliateDiscountHandler implements DiscountHandler {
    @Override
    public Money calculateDiscount(Order order) {
        Money totalNonGroceryAmount = order.getItems().stream()
                .filter(item -> item.getProduct().getProductCategory() == ProductCategory.OTHER)
                .map(OrderItem::getPrice)
                .reduce(Money.ZERO,Money::add);

        return totalNonGroceryAmount.multiply(BigDecimal.valueOf(0.10));
    }
}

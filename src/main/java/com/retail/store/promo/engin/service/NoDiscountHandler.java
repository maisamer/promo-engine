package com.retail.store.promo.engin.service;

import com.retail.store.promo.engin.entity.Order;
import com.retail.store.promo.engin.valueobject.Money;


public class NoDiscountHandler implements DiscountHandler {
    @Override
    public Money calculateDiscount(Order order) {
        return Money.ZERO;
    }
}

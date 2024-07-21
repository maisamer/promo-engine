package com.retail.store.promo.engin.service;

import com.retail.store.promo.engin.entity.Order;
import com.retail.store.promo.engin.valueobject.Money;

public interface DiscountHandler {
    Money calculateDiscount(Order order);
}

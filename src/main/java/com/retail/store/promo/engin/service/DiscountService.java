package com.retail.store.promo.engin.service;

import com.retail.store.promo.engin.entity.Order;
import com.retail.store.promo.engin.entity.OrderItem;
import com.retail.store.promo.engin.valueobject.Money;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DiscountService{
    public Money calculateDiscount(Order order) {
        DiscountHandler discountService = order.getUser().getUserType().getDiscountService();
        Money percentageDiscount = discountService.calculateDiscount(order);
        Money flatDiscount = calculateFlatDiscount(order);

        return percentageDiscount.add(flatDiscount);
    }

    private Money calculateFlatDiscount(Order order) {
        Money totalAmount = order.getItems().stream()
                .map(OrderItem::getPrice)
                .reduce(Money.ZERO,Money::add);

        return totalAmount.divide(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(5));
    }
}

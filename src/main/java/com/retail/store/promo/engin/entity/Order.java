package com.retail.store.promo.engin.entity;

import com.retail.store.promo.engin.exception.BillDomainException;
import com.retail.store.promo.engin.valueobject.Money;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Order {
    private final Long id;
    private final User user;
    private final Money totalAmount;
    private final List<OrderItem> items;
    public void validateOrder(){
        validateUser();
        validateTotalPrice();
        validateItemsPrice();
    }

    private void validateUser() {
        if(user == null)
            throw new BillDomainException("User must not be null!");
        user.validateUser();
    }

    private void validateItemsPrice() {
        Money orderItemsTotal = items.stream().map(orderItem -> {
            validateItemPrice(orderItem);
            return orderItem.getSubTotal();
        }).reduce(Money.ZERO,Money::add);
        if(!orderItemsTotal.equals(totalAmount)){
            throw new BillDomainException("Total price " + totalAmount.getAmount() +
                    "is not equals to order items total " + orderItemsTotal.getAmount() + "!");
        }
    }

    private void validateItemPrice(OrderItem orderItem) {
        if(!orderItem.isValidPrice()){
            throw new BillDomainException("Order item price " + orderItem.getPrice().getAmount() +
                    "is not valid for product " + orderItem.getProduct().getId() + "!");
        }
    }

    private void validateTotalPrice() {
        if(totalAmount == null || !totalAmount.isGreaterThanZero()){
            throw new BillDomainException("Total price must not be greater than zero!");
        }
    }

}

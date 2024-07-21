package com.retail.store.promo.engin.service;

import com.retail.store.promo.engin.entity.Bill;
import com.retail.store.promo.engin.entity.Order;
import com.retail.store.promo.engin.exception.BillDomainException;
import com.retail.store.promo.engin.valueobject.Money;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class BillService {
    private final DiscountService discountService;
    public Bill getBillDiscount(Order order){
        validateOrder(order);
        Money discountAmount = discountService.calculateDiscount(order);
        return Bill.builder()
                .discount(discountAmount)
                .totalAmount(order.getTotalAmount())
                .netPayableAmount(order.getTotalAmount().subtract(discountAmount))
                .build();
    }

    private void validateOrder(Order order){
        if(order == null)
            throw new BillDomainException("Order must not be null!");
        order.validateOrder();
    }
}

package com.retail.store.promo.engin.valueobject;

import com.retail.store.promo.engin.service.*;

public enum UserType {
    EMPLOYEE(new EmployeeDiscountHandler()),
    AFFILIATE(new AffiliateDiscountHandler()),
    CUSTOMER(new CustomerDiscountHandler()),
    OTHERS(new NoDiscountHandler());

    private final DiscountHandler discountService;

    UserType(DiscountHandler discountService) {
        this.discountService = discountService;
    }

    public DiscountHandler getDiscountService() {
        return discountService;
    }
}

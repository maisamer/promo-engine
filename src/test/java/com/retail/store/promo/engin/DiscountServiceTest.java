package com.retail.store.promo.engin;

import com.retail.store.promo.engin.entity.Order;
import com.retail.store.promo.engin.entity.OrderItem;
import com.retail.store.promo.engin.entity.Product;
import com.retail.store.promo.engin.entity.User;
import com.retail.store.promo.engin.service.DiscountService;
import com.retail.store.promo.engin.valueobject.Money;
import com.retail.store.promo.engin.valueobject.ProductCategory;
import com.retail.store.promo.engin.valueobject.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DiscountServiceTest {
    @InjectMocks
    private DiscountService discountService;
    private User employee;
    private User affiliate;
    private User customer;
    private User newCustomer;
    private User others;
    private Order.OrderBuilder orderBuilder;

    @BeforeEach
    public void setUp() {
        LocalDate date = LocalDate.now();
        employee = new User(1L, "Employee", UserType.EMPLOYEE, date);
        affiliate = new User(2L, "Affiliate", UserType.AFFILIATE, date);
        customer = new User(3L, "Customer", UserType.CUSTOMER, date.minusYears(3));
        newCustomer = new User(3L, "Customer", UserType.CUSTOMER, date);
        others = new User(4L, "Regular", UserType.OTHERS, date);
        Product groceryProduct = Product.builder().name("P1").id(1L)
                .productCategory(ProductCategory.GROCERY).price(new Money(BigDecimal.valueOf(200))).build();
        Product otherProduct = Product.builder().name("P2").id(2L)
                .productCategory(ProductCategory.OTHER).price(new Money(BigDecimal.valueOf(50))).build();
        OrderItem item1 = OrderItem.builder().id(1L).price(new Money(BigDecimal.valueOf(200))).product(groceryProduct).quantity(1).build();
        OrderItem item2 = OrderItem.builder().id(2L).price(new Money(BigDecimal.valueOf(100))).product(otherProduct).quantity(2).build();

        orderBuilder = Order.builder().id(1L)
                .items(Arrays.asList(item1, item2))
                .totalAmount(new Money(BigDecimal.valueOf(300)));
    }

    @Test
    public void testCalculateDiscountForEmployee() {
        Order order = orderBuilder.user(employee).build();
        Money discount = discountService.calculateDiscount(order);
        assertEquals(new Money(BigDecimal.valueOf(45.00)), discount);
    }

    @Test
    public void testCalculateDiscountForAffiliate() {
        Order order = orderBuilder.user(affiliate).build();
        Money discount = discountService.calculateDiscount(order);
        assertEquals(new Money(BigDecimal.valueOf(25.00)), discount);
    }

    @Test
    public void testCalculateDiscountForCustomer() {
        Order order = orderBuilder.user(customer).build();
        Money discount = discountService.calculateDiscount(order);
        assertEquals(new Money(BigDecimal.valueOf(20)), discount);
    }

    @Test
    public void testCalculateDiscountForCustomerJoinedAtLessThanTwoYears() {
        Order order = orderBuilder.user(newCustomer).build();
        Money discount = discountService.calculateDiscount(order);
        assertEquals(new Money(BigDecimal.valueOf(15)), discount);
    }

    @Test
    public void testCalculateDiscountForRegular() {
        Order order = orderBuilder.user(others).build();
        Money discount = discountService.calculateDiscount(order);
        assertEquals(new Money(BigDecimal.valueOf(15)), discount);
    }

}

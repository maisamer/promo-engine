package com.retail.store.promo.engin;

import com.retail.store.promo.engin.service.DiscountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import com.retail.store.promo.engin.entity.Bill;
import com.retail.store.promo.engin.entity.Order;
import com.retail.store.promo.engin.entity.OrderItem;
import com.retail.store.promo.engin.entity.Product;
import com.retail.store.promo.engin.entity.User;
import com.retail.store.promo.engin.exception.BillDomainException;
import com.retail.store.promo.engin.service.BillService;
import com.retail.store.promo.engin.valueobject.Money;
import com.retail.store.promo.engin.valueobject.ProductCategory;
import com.retail.store.promo.engin.valueobject.UserType;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BillServiceTest {

    @Mock
    private DiscountService discountService;

    @InjectMocks
    private BillService billService;

    private User employee;
    private User affiliate;
    private User customer;
    private User others;
    private Order.OrderBuilder orderBuilder;

    @BeforeEach
    public void setUp() {
        LocalDate date = LocalDate.now();
        employee = new User(1L, "Employee", UserType.EMPLOYEE, date);
        affiliate = new User(2L, "Affiliate", UserType.AFFILIATE, date);
        customer = new User(3L, "Customer", UserType.CUSTOMER, date.minusYears(3));
        others = new User(4L, "Regular", UserType.OTHERS, date);
        Product groceryProduct = Product.builder()
                .name("P1")
                .id(1L)
                .productCategory(ProductCategory.GROCERY)
                .price(new Money(BigDecimal.valueOf(200)))
                .build();
        Product otherProduct = Product.builder()
                .name("P2")
                .id(2L)
                .productCategory(ProductCategory.OTHER)
                .price(new Money(BigDecimal.valueOf(50)))
                .build();
        OrderItem item1 = OrderItem.builder()
                .id(1L)
                .price(new Money(BigDecimal.valueOf(200)))
                .subTotal(new Money(BigDecimal.valueOf(200)))
                .product(groceryProduct)
                .quantity(1)
                .build();
        OrderItem item2 = OrderItem.builder()
                .id(2L)
                .price(new Money(BigDecimal.valueOf(50)))
                .subTotal(new Money(BigDecimal.valueOf(100)))
                .product(otherProduct)
                .quantity(2)
                .build();

        orderBuilder = Order.builder()
                .id(1L)
                .items(Arrays.asList(item1, item2))
                .totalAmount(new Money(BigDecimal.valueOf(300)));
    }

    @Test
    public void testGetBillDiscountForEmployee() {
        Order order = orderBuilder.user(employee).build();

        when(discountService.calculateDiscount(any(Order.class))).thenReturn(new Money(BigDecimal.valueOf(45)));

        Bill bill = billService.getBillDiscount(order);

        assertEquals(new Money(BigDecimal.valueOf(45)), bill.getDiscount());
        assertEquals(new Money(BigDecimal.valueOf(300)), bill.getTotalAmount());
        assertEquals(new Money(BigDecimal.valueOf(255)), bill.getNetPayableAmount());
    }

    @Test
    public void testGetBillDiscountForAffiliate() {
        Order order = orderBuilder.user(affiliate).build();

        when(discountService.calculateDiscount(any(Order.class))).thenReturn(new Money(BigDecimal.valueOf(25)));

        Bill bill = billService.getBillDiscount(order);

        assertEquals(new Money(BigDecimal.valueOf(25)), bill.getDiscount());
        assertEquals(new Money(BigDecimal.valueOf(300)), bill.getTotalAmount());
        assertEquals(new Money(BigDecimal.valueOf(275)), bill.getNetPayableAmount());
    }

    @Test
    public void testGetBillDiscountForCustomer() {
        Order order = orderBuilder.user(customer).build();
        when(discountService.calculateDiscount(any(Order.class))).thenReturn(new Money(BigDecimal.valueOf(20)));

        Bill bill = billService.getBillDiscount(order);

        assertEquals(new Money(BigDecimal.valueOf(20)), bill.getDiscount());
        assertEquals(new Money(BigDecimal.valueOf(300)), bill.getTotalAmount());
        assertEquals(new Money(BigDecimal.valueOf(280)), bill.getNetPayableAmount());
    }

    @Test
    public void testGetBillDiscountForRegular() {
        Order order = orderBuilder.user(others).build();
        when(discountService.calculateDiscount(any(Order.class))).thenReturn(new Money(BigDecimal.valueOf(15)));

        Bill bill = billService.getBillDiscount(order);

        assertEquals(new Money(BigDecimal.valueOf(15)), bill.getDiscount());
        assertEquals(new Money(BigDecimal.valueOf(300)), bill.getTotalAmount());
        assertEquals(new Money(BigDecimal.valueOf(285)), bill.getNetPayableAmount());
    }

    @Test
    public void testValidationForInvalidOrder() {
        assertThrows(BillDomainException.class, () -> billService.getBillDiscount(null));
    }

    @Test
    public void testValidationForInvalidUser() {
        User invalidUser = new User(null, null, null, null);
        Order order = orderBuilder.user(invalidUser).build();

        assertThrows(BillDomainException.class, () -> billService.getBillDiscount(order));
    }
}
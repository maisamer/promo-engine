package com.retail.store.promo.engin.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {
    private final BigDecimal amount;

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean isGreaterThanZero(){
        return this.amount != null && this.amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThan(Money input){
        return this.amount != null && this.amount.compareTo(input.amount) > 0;
    }

    public Money add(Money input){
        return new Money(setScale(this.amount.add(input.amount)));
    }

    public Money subtract(Money input){
        return new Money(setScale(this.amount.subtract(input.amount)));
    }

    public Money multiply(BigDecimal multiplier) { return new Money(setScale(this.amount.multiply(multiplier)));}

    public Money multiply(int multiplier) { return new Money(setScale(this.amount.multiply(new BigDecimal(multiplier))));}


    private BigDecimal setScale(BigDecimal input){
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }

    public Money divide(BigDecimal input) {return new Money(setScale(this.amount.divide(input)));}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount.compareTo(money.amount) == 0;
    }


    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

}

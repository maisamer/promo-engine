package com.retail.store.promo.engin.entity;

import com.retail.store.promo.engin.valueobject.Money;
import com.retail.store.promo.engin.valueobject.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
public class Product {
    private Long id;
    private final String name;
    private final ProductCategory productCategory;
    private final Money price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

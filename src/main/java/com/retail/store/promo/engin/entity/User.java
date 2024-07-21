package com.retail.store.promo.engin.entity;

import com.retail.store.promo.engin.exception.BillDomainException;
import com.retail.store.promo.engin.valueobject.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private UserType userType;
    private LocalDate joinedOn;

    public void validateUser() {
        if(userType == null || joinedOn == null || id == null || username == null)
            throw new BillDomainException("Invalid user!");
    }
}

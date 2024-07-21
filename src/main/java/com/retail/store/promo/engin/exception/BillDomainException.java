package com.retail.store.promo.engin.exception;

public class BillDomainException  extends RuntimeException{
    public BillDomainException(String message) {
        super(message);
    }

    public BillDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.example.testingandci.exceptions;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException() {
        super("Payment not found");
    }

    public PaymentNotFoundException(String message) {
        super(message);
    }

}

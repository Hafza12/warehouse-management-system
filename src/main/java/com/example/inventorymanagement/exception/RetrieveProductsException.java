package com.example.inventorymanagement.exception;

public class RetrieveProductsException extends RuntimeException {
    public RetrieveProductsException(String message) {
        super(message);
    }

    public RetrieveProductsException(String message, Throwable cause) {
        super(message, cause);
    }
}


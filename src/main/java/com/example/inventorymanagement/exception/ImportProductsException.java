package com.example.inventorymanagement.exception;

public class ImportProductsException extends RuntimeException {
    public ImportProductsException(String message) {
        super(message);
    }

    public ImportProductsException(String message, Throwable cause) {
        super(message, cause);
    }
}

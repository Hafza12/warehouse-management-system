package com.example.inventorymanagement.exception;

public class ProcessOrderException extends RuntimeException {
    public ProcessOrderException(String message) {
        super(message);
    }

    public ProcessOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}

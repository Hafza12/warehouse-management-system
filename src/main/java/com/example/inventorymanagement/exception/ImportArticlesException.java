package com.example.inventorymanagement.exception;

public class ImportArticlesException extends RuntimeException {
    public ImportArticlesException(String message) {
        super(message);
    }

    public ImportArticlesException(String message, Throwable cause) {
        super(message, cause);
    }
}
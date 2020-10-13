package com.amaurote.bookstore.exception;

public class CategoryException extends RuntimeException {
    public CategoryException(String message) {
        super("Category error: " + message);
    }
}

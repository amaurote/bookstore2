package com.amaurote.bookstore.exception;

public class RatingException extends RuntimeException {
    public RatingException(String message) {
        super("Rating error:" + message);
    }
}

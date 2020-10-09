package com.amaurote.bookstore.exception;

public class ReviewException extends RuntimeException {
    public ReviewException(String message) {
        super("Review error:" + message);
    }
}

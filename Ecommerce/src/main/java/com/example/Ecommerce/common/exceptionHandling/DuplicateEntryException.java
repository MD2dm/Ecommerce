package com.example.Ecommerce.common.exceptionHandling;

public class DuplicateEntryException extends RuntimeException{

    public DuplicateEntryException(String message) {
        super(message);
    }

}

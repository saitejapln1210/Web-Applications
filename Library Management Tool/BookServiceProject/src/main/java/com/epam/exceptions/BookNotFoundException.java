package com.epam.exceptions;

public class BookNotFoundException extends Exception {
    public BookNotFoundException(String string) {
        super(string);
    }
}
